package com.playhub.game.boggle.manager.dto;

import com.playhub.common.support.validation.annotations.Even;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;

import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public record NewGameRequestDto(
        @NotNull UUID roomId,
        @Min(1) @Max(10) int rounds,
        @Even @Min(4) @Max(8) int gridSize,
        @Size(min = 1) Set<UUID> players,
        @NotNull Locale locale,
        @NotNull @DurationMin(minutes = 1L) @DurationMax(minutes = 3L) Duration roundsDuration
) {}
