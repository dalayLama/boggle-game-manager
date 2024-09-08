package com.playhub.game.boggle.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public class RoundByNumberNotFoundException extends BoggleGameManagerException {

    private static final String MESSAGE_TEMPLATE = "Round with number %d hasn't been found in the game %s";

    private final UUID gameId;

    private final int roundNumber;

    public RoundByNumberNotFoundException(UUID gameId, int roundNumber) {
        super(MESSAGE_TEMPLATE.formatted(roundNumber, gameId), Error.ROUND_NOT_FOUND_BY_NUMBER);
        this.gameId = gameId;
        this.roundNumber = roundNumber;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return new Object[] {gameId, roundNumber};
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

}
