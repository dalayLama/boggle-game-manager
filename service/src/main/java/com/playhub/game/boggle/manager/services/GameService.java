package com.playhub.game.boggle.manager.services;

import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GameService {

    Game createGame(@NotNull @Valid NewGameRequest request);

}