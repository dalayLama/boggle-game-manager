package com.playhub.game.boggle.manager.services;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public interface PlayerService {

    Set<UUID> filterActivePlayers(Set<UUID> playerIds);

    void addAnswer(@NotNull Long roundId, @NotNull UUID playerId, @NotEmpty String answer);
}
