package com.playhub.game.boggle.manager.components;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DefaultLettersDictionary implements LettersDictionary {

    private final List<Character> commonLetters;

    private final List<Character> rareLetters;

    @Override
    public List<Character> getCommonLetters() {
        return commonLetters;
    }

    @Override
    public List<Character> getRareLetters() {
        return rareLetters;
    }

}
