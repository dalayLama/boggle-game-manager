package com.playhub.game.boggle.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record RoundDto(
        UUID gameId,
        int number,
        List<List<Character>> board,
        Set<UUID> players,
        RoundStateDto state,
        @Schema(type = "string", description = "Duration in ISO-8601 format", example = "PT1M") Duration duration,
        Instant createdAt,
        Instant startedAt,
        Instant finishedAt
) {
}
