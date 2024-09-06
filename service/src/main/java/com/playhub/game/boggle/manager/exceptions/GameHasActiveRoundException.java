package com.playhub.game.boggle.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public class GameHasActiveRoundException extends BoggleGameManagerException {

    private final static String MESSAGE_TEMPLATE = "Game %s already has an active round";

    private final UUID gameId;

    public GameHasActiveRoundException(UUID gameId) {
        super(MESSAGE_TEMPLATE.formatted(gameId), Error.GAME_HAS_ACTIVE_ROUND);
        this.gameId = gameId;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return new Object[] {gameId};
    }

}
