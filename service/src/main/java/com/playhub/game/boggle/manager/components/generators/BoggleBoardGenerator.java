package com.playhub.game.boggle.manager.components.generators;

import com.playhub.game.boggle.manager.models.BoggleBoard;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Locale;

public interface BoggleBoardGenerator {

    BoggleBoard generate(@Min(4) int boardSize, @NotNull Locale locale);

}
