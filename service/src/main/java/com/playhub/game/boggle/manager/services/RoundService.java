package com.playhub.game.boggle.manager.services;

import com.playhub.game.boggle.manager.models.Round;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface RoundService {

    Round startNextRound(@NotNull UUID gameId);

}
