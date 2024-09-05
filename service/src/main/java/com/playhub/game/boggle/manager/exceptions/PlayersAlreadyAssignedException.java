package com.playhub.game.boggle.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Set;
import java.util.UUID;

public class PlayersAlreadyAssignedException extends BoggleGameManagerException {

    private static final String MESSAGE_TEMPLATE = "Players %s are already assigned to different active games";

    private final Set<UUID> players;

    public PlayersAlreadyAssignedException(Set<UUID> players) {
        super(MESSAGE_TEMPLATE.formatted(players), Error.PLAYERS_ALREADY_ASSIGNED);
        this.players = players;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return players.toArray(UUID[]::new);
    }

}
