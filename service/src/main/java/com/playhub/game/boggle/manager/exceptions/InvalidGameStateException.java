package com.playhub.game.boggle.manager.exceptions;

import com.playhub.game.boggle.manager.models.GameState;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public class InvalidGameStateException extends BoggleGameManagerException {

    private final UUID gameId;

    private final GameState state;

    public InvalidGameStateException(String message, UUID gameId, GameState state) {
        super(message, Error.INVALID_GAME_STATE);
        this.gameId = gameId;
        this.state = state;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return new Object[] {gameId, state};
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }

}
