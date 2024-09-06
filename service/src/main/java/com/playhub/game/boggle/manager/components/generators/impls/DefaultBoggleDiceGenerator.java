package com.playhub.game.boggle.manager.components.generators.impls;

import com.playhub.game.boggle.manager.components.BoggleDice;
import com.playhub.game.boggle.manager.components.LettersDictionary;
import com.playhub.game.boggle.manager.components.generators.BoggleDiceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class DefaultBoggleDiceGenerator implements BoggleDiceGenerator {

    private final Random random;

    @Override
    public List<BoggleDice> generateDices(@Positive int gridSize, @NotNull LettersDictionary lettersDictionary) {
        int dicesCount = gridSize * gridSize;
        List<BoggleDice> dices = new ArrayList<>(dicesCount);

        for (int i = 0; i < dicesCount; i++) {
            List<Character> diceSymbols = new ArrayList<>();

            diceSymbols.add(getRandomSymbol(lettersDictionary.getRareLetters()));

            for (int j = 0; j < 5; j++) {
                diceSymbols.add(getRandomSymbol(lettersDictionary.getCommonLetters()));
            }

            Collections.shuffle(diceSymbols);

            dices.add(new BoggleDice(diceSymbols));
        }

        return dices;
    }

    private Character getRandomSymbol(List<Character> symbols) {
        int rareIndex = random.nextInt(symbols.size());
        return symbols.get(rareIndex);
    }

}
