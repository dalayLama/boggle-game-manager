package com.playhub.game.boggle.manager.services;

import com.playhub.game.boggle.manager.dao.repositories.RoundPlayerRepository;
import com.playhub.game.boggle.manager.models.GameState;
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

}