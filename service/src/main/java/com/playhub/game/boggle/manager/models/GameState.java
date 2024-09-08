package com.playhub.game.boggle.manager.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum GameState {

    WAITING(true), PLAYING(true, true), CANCELED(false), FINISHED(false);

    public static Set<GameState> getActiveStates() {
        return Arrays.stream(GameState.values())
                .filter(GameState::isActive)
                .collect(Collectors.toSet());
    }

    private final boolean active;

    private final boolean playable;

    GameState(boolean active) {
        this(active, false);
    }

    GameState(boolean active, boolean playable) {
        this.active = active;
        this.playable = playable;
    }

}
