package com.playhub.game.boggle.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public class PlayerInRoundNotFound extends BoggleGameManagerException {

    private static final String MESSAGE_TEMPLATE = "Player %s hasn't been found in round %d";

    private final Long roundId;

    private final UUID playerId;

    public PlayerInRoundNotFound(Long roundId, UUID playerId) {
        super(MESSAGE_TEMPLATE.formatted(playerId, roundId), Error.PLAYER_NOT_FOUND);
        this.roundId = roundId;
        this.playerId = playerId;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return new Object[] {roundId, playerId};
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

}
