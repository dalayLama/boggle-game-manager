package com.playhub.game.boggle.manager.components.generators.impls;

import com.playhub.game.boggle.manager.components.BoggleDice;
import com.playhub.game.boggle.manager.components.LettersDictionary;
import com.playhub.game.boggle.manager.components.LettersDictionaryProvider;
import com.playhub.game.boggle.manager.components.generators.BoggleBoardGenerator;
import com.playhub.game.boggle.manager.components.generators.BoggleDiceGenerator;
import com.playhub.game.boggle.manager.exceptions.LettersDictionaryNotFoundByLocale;
import com.playhub.game.boggle.manager.models.BoggleBoard;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DefaultBoggleBoardGenerator implements BoggleBoardGenerator {

    private final LettersDictionaryProvider lettersDictionaryProvider;

    private final BoggleDiceGenerator diceGenerator;

    @Override
    public BoggleBoard generate(@Min(4) int gridSize, @NotNull Locale locale) {
        LettersDictionary lettersDictionary = getLettersDictionary(locale);
        List<BoggleDice> dices = diceGenerator.generateDices(gridSize, lettersDictionary);
        return generateBoard(gridSize, dices);
    }

    private BoggleBoard generateBoard(int gridSize, List<BoggleDice> dices) {
        List<List<Character>> grid = new ArrayList<>(gridSize);
        int diceIndex = 0;
        for (int i = 0; i < gridSize; i++) {
            List<Character> row = new ArrayList<>(gridSize);
            for (int j = 0; j < gridSize; j++) {
                Character diceSymbol = dices.get(diceIndex++).getRandomSymbol();
                row.add(diceSymbol);
            }
            grid.add(row);
        }
        return new BoggleBoard(grid);
    }

    private LettersDictionary getLettersDictionary(Locale locale) {
        return lettersDictionaryProvider.getDictionary(locale)
                .orElseThrow(() -> new LettersDictionaryNotFoundByLocale(locale));
    }

}
