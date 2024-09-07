package com.playhub.game.boggle.manager.services;

import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import com.playhub.game.boggle.manager.models.Round;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface GameService {

    Game createGame(@NotNull @Valid NewGameRequest request);

    Round startNextRound(@NotNull UUID gameId);

}