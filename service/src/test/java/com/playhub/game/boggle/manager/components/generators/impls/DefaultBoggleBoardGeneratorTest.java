package com.playhub.game.boggle.manager.components.generators.impls;

import com.playhub.game.boggle.manager.components.BoggleDice;
import com.playhub.game.boggle.manager.components.LettersDictionary;
import com.playhub.game.boggle.manager.components.LettersDictionaryProvider;
import com.playhub.game.boggle.manager.components.generators.BoggleDiceGenerator;
import com.playhub.game.boggle.manager.exceptions.LettersDictionaryNotFoundByLocale;
import com.playhub.game.boggle.manager.models.BoggleBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultBoggleBoardGeneratorTest {

    @Mock
    private LettersDictionaryProvider lettersDictionaryProvider;

    @Mock
    private BoggleDiceGenerator diceGenerator;

    @InjectMocks
    private DefaultBoggleBoardGenerator boardGenerator;

    @Test
    void shouldGenerateCorrectBoard() {
        // Given
        Locale locale = Locale.ENGLISH;
        LettersDictionary lettersDictionary = mock(LettersDictionary.class);
        when(lettersDictionaryProvider.getDictionary(locale)).thenReturn(Optional.of(lettersDictionary));

        int gridSize = 4; // 4x4 board
        List<BoggleDice> dices = generateMockDices(gridSize * gridSize);
        when(diceGenerator.generateDices(gridSize, lettersDictionary)).thenReturn(dices);

        // When
        BoggleBoard board = boardGenerator.generate(gridSize, locale);

        // Then
        assertThat(board).isNotNull();
        assertThat(board).hasSize(gridSize); // Ensure board has correct number of rows

        // Ensure each row has the correct number of columns and each symbol is 'A'
        for (List<Character> row : board) {
            assertThat(row).hasSize(gridSize);
            assertThat(row).allMatch(symbol -> symbol == 'A'); // Check that all symbols in row are 'A'
        }
    }

    @Test
    void shouldThrowExceptionWhenDictionaryNotFound() {
        // Given
        Locale locale = Locale.FRENCH;
        when(lettersDictionaryProvider.getDictionary(locale)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> boardGenerator.generate(4, locale))
                .isInstanceOf(LettersDictionaryNotFoundByLocale.class)
                .hasMessageContaining(locale.toLanguageTag());
    }

    // Helper method to generate mocked dices
    private List<BoggleDice> generateMockDices(int count) {
        List<BoggleDice> dices = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            BoggleDice dice = mock(BoggleDice.class);
            when(dice.getRandomSymbol()).thenReturn('A'); // Mock each dice to return 'A'
            dices.add(dice);
        }
        return dices;
    }

}