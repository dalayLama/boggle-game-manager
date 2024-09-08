package com.playhub.game.boggle.manager.services;

import com.playhub.game.boggle.manager.models.Round;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public interface RoundService {

    Round startNextRound(@NotNull UUID gameId);

    void addAnswer(@NotNull UUID gameId,
                   @Positive int roundNumber,
                   @NotNull UUID playerId,
                   @NotEmpty String answer);
}
