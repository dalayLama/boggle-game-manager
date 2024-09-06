package com.playhub.game.boggle.manager.models;

import lombok.Builder;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
public record Round(
        Long id,
        UUID gameId,
        int number,
        BoggleBoard board,
        Set<UUID> players,
        RoundState state,
        Duration duration,
        Instant createdAt,
        Instant startedAt,
        Instant finishedAt
) {}
