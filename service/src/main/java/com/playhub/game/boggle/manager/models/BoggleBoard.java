package com.playhub.game.boggle.manager.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BoggleBoard extends ArrayList<List<Character>> {

    public BoggleBoard(int initialCapacity) {
        super(initialCapacity);
    }

    public BoggleBoard() {
    }

    public BoggleBoard(Collection<? extends List<Character>> c) {
        super(c);
    }
}
