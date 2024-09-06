package com.playhub.game.boggle.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public class AvailableRoundNotFoundException extends BoggleGameManagerException {

    private static final String MESSAGE_TEMPLATE = "Available round hasn't been found for the game %s";

    private final UUID gameId;

    public AvailableRoundNotFoundException(UUID gameId) {
        super(MESSAGE_TEMPLATE.formatted(gameId), Error.AVAILABLE_ROUND_NOT_FOUND);
        this.gameId = gameId;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return new Object[] {gameId};
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

}
