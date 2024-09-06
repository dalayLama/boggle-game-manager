package com.playhub.game.boggle.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public class GameNotFoundByIdException extends BoggleGameManagerException {

    private static final String MESSAGE_TEMPLATE = "Game not found by id %s";

    private final UUID gameId;

    public GameNotFoundByIdException(UUID gameId) {
        super(MESSAGE_TEMPLATE.formatted(gameId), Error.GAME_NOT_FOUND_BY_ID);
        this.gameId = gameId;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return new Object[]{gameId};
    }

}
