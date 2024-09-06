package com.playhub.game.boggle.manager.components;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BoggleDice {

    private final List<Character> symbols;

    private final Random random;

    public BoggleDice(Character side1,
                      Character side2,
                      Character side3,
                      Character side4,
                      Character side5,
                      Character side6) {
        this(List.of(side1, side2, side3, side4, side5, side6));
    }

    public BoggleDice(Collection<? extends Character> symbols) {
        this(symbols, new Random());
    }

    public BoggleDice(Collection<? extends Character> symbols, Random random) {
        if (symbols == null || symbols.size() != 6) {
            throw new IllegalArgumentException("symbols must contain exactly 6 symbols");
        }
        this.symbols = List.copyOf(symbols);
        this.random = Objects.requireNonNull(random, "Random must not be null");
    }

    public Character getRandomSymbol() {
        int index = random.nextInt(symbols.size());
        return symbols.get(index);
    }

    public List<Character> getAllSymbols() {
        return List.copyOf(symbols);
    }

}
