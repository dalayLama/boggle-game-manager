package com.playhub.game.boggle.manager.exceptions;

import com.playhub.game.boggle.manager.models.RoundState;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public class InvalidRoundStateException extends BoggleGameManagerException {

    private final UUID gameId;

    private final Long roundId;

    private final RoundState roundState;

    public InvalidRoundStateException(String message, UUID gameId, Long id, RoundState state) {
        super(message, Error.INVALID_GAME_STATE);
        this.gameId = gameId;
        this.roundId = id;
        this.roundState = state;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return new Object[] {gameId, roundId, roundState};
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }

}
