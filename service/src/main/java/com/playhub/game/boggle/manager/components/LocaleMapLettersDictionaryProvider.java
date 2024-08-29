package com.playhub.game.boggle.manager.components;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class LocaleMapLettersDictionaryProvider implements LettersDictionaryProvider {

    private final Map<Locale, LettersDictionary> lettersDictionaryMap;

    @Override
    public Optional<LettersDictionary> getDictionary(@NotNull Locale locale) {
        return Optional.ofNullable(lettersDictionaryMap.get(locale));
    }

}
