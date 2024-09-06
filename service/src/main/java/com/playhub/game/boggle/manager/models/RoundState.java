package com.playhub.game.boggle.manager.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum RoundState {

    WAITING(true), PLAYING(true), CANCELED(false), FINISHED(false);

    public static Set<GameState> getActiveStates() {
        return Arrays.stream(GameState.values())
                .filter(GameState::isActive)
                .collect(Collectors.toSet());
    }

    private final boolean active;

}
