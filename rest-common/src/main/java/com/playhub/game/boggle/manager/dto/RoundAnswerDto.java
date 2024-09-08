package com.playhub.game.boggle.manager.dto;

import jakarta.validation.constraints.NotBlank;

public record RoundAnswerDto(
        @NotBlank String answer
) {}
