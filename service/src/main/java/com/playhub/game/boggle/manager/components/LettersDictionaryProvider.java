package com.playhub.game.boggle.manager.components;

import jakarta.validation.constraints.NotNull;

import java.util.Locale;
import java.util.Optional;

public interface LettersDictionaryProvider {

    Optional<LettersDictionary> getDictionary(@NotNull Locale locale);

}
