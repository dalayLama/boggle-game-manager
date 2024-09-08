package com.playhub.game.boggle.manager.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum RoundState {

    WAITING(true), PLAYING(true, true), CANCELED(false), FINISHED(false);

    public static Set<RoundState> getActiveStates() {
        return Arrays.stream(RoundState.values())
                .filter(RoundState::isActive)
                .collect(Collectors.toSet());
    }

    RoundState(boolean active) {
        this(active, false);
    }

    RoundState(boolean active, boolean playable) {
        this.active = active;
        this.playable = playable;
    }

    private final boolean active;

    private final boolean playable;

}
