package com.playhub.game.boggle.manager.components.generators.impls;

import com.playhub.game.boggle.manager.components.BoggleDice;
import com.playhub.game.boggle.manager.components.LettersDictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultBoggleDiceGeneratorTest {

    private LettersDictionary lettersDictionary;

    private DefaultBoggleDiceGenerator generator;

    @BeforeEach
    void setUp() {
        Random random = new Random(12345);
        lettersDictionary = mock(LettersDictionary.class);
        generator = new DefaultBoggleDiceGenerator(random);
    }

    @Test
    void shouldGenerateCorrectNumberOfDices() {
        // Given
        int gridSize = 4; // 4x4 board means 16 dices
        when(lettersDictionary.getRareLetters()).thenReturn(List.of('X', 'Z', 'Q'));
        when(lettersDictionary.getCommonLetters()).thenReturn(List.of('A', 'E', 'I', 'O', 'N'));

        // When
        List<BoggleDice> dices = generator.generateDices(gridSize, lettersDictionary);

        // Then
        assertThat(dices).hasSize(16); // Ensure 16 dices are generated for a 4x4 board
    }

    @Test
    void shouldEachDiceContainOneRareAndFiveCommonLetters() {
        // Given
        int gridSize = 3; // 3x3 board means 9 dices
        when(lettersDictionary.getRareLetters()).thenReturn(List.of('X', 'Z', 'Q'));
        when(lettersDictionary.getCommonLetters()).thenReturn(List.of('A', 'E', 'I', 'O', 'N'));

        // When
        List<BoggleDice> dices = generator.generateDices(gridSize, lettersDictionary);

        // Then
        for (BoggleDice dice : dices) {
            List<Character> symbols = dice.getAllSymbols();
            assertThat(symbols).hasSize(6); // Each dice has 6 symbols
            assertThat(symbols).containsAnyOf('X', 'Z', 'Q'); // One rare letter
            assertThat(symbols).containsAnyOf('A', 'E', 'I', 'O', 'N'); // Common letters
        }

    }

}