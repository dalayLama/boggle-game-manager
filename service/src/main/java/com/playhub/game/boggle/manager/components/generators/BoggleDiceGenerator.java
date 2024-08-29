package com.playhub.game.boggle.manager.components.generators;

import com.playhub.game.boggle.manager.components.BoggleDice;
import com.playhub.game.boggle.manager.components.LettersDictionary;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface BoggleDiceGenerator {

    List<BoggleDice> generateDices(@Positive int gridSize, @NotNull LettersDictionary lettersDictionary);

}
