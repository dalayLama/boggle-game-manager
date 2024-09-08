package com.playhub.game.boggle.manager.services;

import com.playhub.game.boggle.manager.dao.entities.PlayerAnswerEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundPlayerEntity;
import com.playhub.game.boggle.manager.dao.repositories.RoundPlayerRepository;
import com.playhub.game.boggle.manager.exceptions.PlayerInRoundNotFound;
import com.playhub.game.boggle.manager.models.GameState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DaoPlayersService implements PlayerService {

    private final RoundPlayerRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Set<UUID> filterActivePlayers(Set<UUID> playerIds) {
        return repository.filterActivePlayers(playerIds, GameState.getActiveStates());
    }

    @Transactional
    public void addAnswer(@NotNull Long roundId, @NotNull UUID playerId, @NotEmpty String answer) {
        RoundPlayerEntity player = repository.findPlayerInRound(roundId, playerId)
                .orElseThrow(() -> new PlayerInRoundNotFound(roundId, playerId));
        PlayerAnswerEntity newAnswer = PlayerAnswerEntity.newAnswer(answer);
        player.addAnswer(newAnswer);
        repository.saveAndFlush(player);
    }

}