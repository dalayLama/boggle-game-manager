package com.playhub.game.boggle.manager.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.playhub.game.boggle.manager.dto.converters.LocaleToStringConverter;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

public record GameDto(
        UUID id,
        UUID ownerId,
        UUID roomId,
        @JsonSerialize(converter = LocaleToStringConverter.class) Locale locale,
        GameStateDto state,
        Instant createdAt,
        Instant startedAt,
        Instant finishedAt
) {
}
