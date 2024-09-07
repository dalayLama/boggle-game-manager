package com.playhub.game.boggle.manager.services;

import com.playhub.game.boggle.manager.components.generators.BoggleBoardGenerator;
import com.playhub.game.boggle.manager.dao.entities.GameEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import com.playhub.game.boggle.manager.dao.repositories.GameRepository;
import com.playhub.game.boggle.manager.exceptions.GameNotFoundByIdException;
import com.playhub.game.boggle.manager.exceptions.InvalidGameStateException;
import com.playhub.game.boggle.manager.exceptions.PlayersAlreadyAssignedException;
import com.playhub.game.boggle.manager.mappers.GameMapper;
import com.playhub.game.boggle.manager.models.BoggleBoard;
import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.Round;
import com.playhub.game.boggle.manager.models.GameState;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class DaoGameService implements GameService {

    private final GameRepository gameRepository;

    private final PlayerService playerService;

    private final RoundService roundService;

    private final BoggleBoardGenerator boardGenerator;

    private final GameMapper gameMapper;

    @Transactional
    @Override
    public Game createGame(@NotNull @Valid NewGameRequest request) {
        GameEntity newGame = gameMapper.toGameEntity(request);
        Set<UUID> players = extractPlayers(request);
        checkPlayersNotInGame(players);
        createRounds(request, players).forEach(newGame::addRound);
        GameEntity savedGame = gameRepository.saveAndFlush(newGame);
        return gameMapper.toGame(savedGame);
    }

    @Transactional
    @Override
    public Round startNextRound(@NotNull UUID gameId) {
        GameEntity game = getGameById(gameRepository::pessimisticWrite, gameId);
        if (game.getState() == GameState.WAITING) {
            changeStateToPlaying(game);
        } else if (!game.getState().isActive()) {
            String message = "Can't start next round for the game(%s) with state %s".formatted(gameId, game.getState());
            throw new InvalidGameStateException(message, gameId, game.getState());
        }

        return roundService.startNextRound(gameId);
    }

    private void changeStateToPlaying(GameEntity game) {
        game.setState(GameState.PLAYING);
        game.setStartedAt(Instant.now());
    }

    private List<RoundEntity> createRounds(NewGameRequest request, Set<UUID> players) {
        return IntStream.range(0, request.rounds())
                .mapToObj(index -> createRound(index + 1, request, players))
                .toList();
    }

    private RoundEntity createRound(int roundNumber, NewGameRequest request, Set<UUID> players) {
        BoggleBoard board = boardGenerator.generate(request.gridSize(), request.locale());
        return RoundEntity.newRound(roundNumber, board, players);
    }

    private Set<UUID> extractPlayers(NewGameRequest request) {
        Set<UUID> players = new HashSet<>(request.players());
        players.add(request.ownerId());
        return players;
    }

    private void checkPlayersNotInGame(Set<UUID> players) {
        Set<UUID> activePlayers = playerService.filterActivePlayers(players);
        if (!activePlayers.isEmpty()) {
            throw new PlayersAlreadyAssignedException(activePlayers);
        }
    }

    private GameEntity getGameById(Function<UUID, Optional<GameEntity>> getFunction, UUID gameId) {
        return getFunction.apply(gameId).orElseThrow(() -> new GameNotFoundByIdException(gameId));
    }

}