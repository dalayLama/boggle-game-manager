package com.playhub.game.boggle.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public class RoundNotHaveEnoughPlayers extends BoggleGameManagerException {

    private static final String MESSAGE_TEMPLATE = "Round %d of the game %s doesn't have enough players.";

    private final UUID gameId;

    private final Long roundId;

    public RoundNotHaveEnoughPlayers(UUID gameId, Long roundId) {
        super(MESSAGE_TEMPLATE.formatted(roundId, gameId), Error.ROUND_NOT_HAVE_ENOUGH_PLAYERS);
        this.gameId = gameId;
        this.roundId = roundId;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return new Object[] {gameId, roundId};
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }

}
