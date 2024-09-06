package com.playhub.game.boggle.manager.models;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

public record Game(
        UUID id,
        UUID ownerId,
        UUID roomId,
        Locale locale,
        GameState state,
        Instant createdAt,
        Instant startedAt,
        Instant finishedAt
) {}
