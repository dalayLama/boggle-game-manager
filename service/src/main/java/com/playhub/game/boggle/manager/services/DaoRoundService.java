package com.playhub.game.boggle.manager.services;

import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import com.playhub.game.boggle.manager.dao.repositories.RoundRepository;
import com.playhub.game.boggle.manager.exceptions.AvailableRoundNotFoundException;
import com.playhub.game.boggle.manager.exceptions.GameHasActiveRoundException;
import com.playhub.game.boggle.manager.exceptions.RoundNotHaveEnoughPlayers;
import com.playhub.game.boggle.manager.mappers.GameMapper;
import com.playhub.game.boggle.manager.models.Round;
import com.playhub.game.boggle.manager.models.RoundState;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DaoRoundService implements RoundService {

    private final RoundRepository roundRepository;

    private final GameMapper gameMapper;

    @Transactional
    public Round startNextRound(@NotNull UUID gameId) {
        if (roundRepository.gameHasActiveRound(gameId)) {
            throw new GameHasActiveRoundException(gameId);
        }
        RoundEntity round = roundRepository.findAndBlockFirstWaitingRound(gameId)
                .orElseThrow(() -> new AvailableRoundNotFoundException(gameId));

        checkRoundHasEnoughParticipants(round);
        changeStateToPlaying(round);
        RoundEntity savedRound = roundRepository.saveAndFlush(round);
        return gameMapper.toRound(savedRound);
    }

    private void checkRoundHasEnoughParticipants(RoundEntity round) {
        if (round.getPlayers().size() < 2) {
            throw new RoundNotHaveEnoughPlayers(round.getGame().getId(), round.getId());
        }
    }

    private void changeStateToPlaying(RoundEntity round) {
        round.setState(RoundState.PLAYING);
        round.setStartedAt(Instant.now());
    }

}
