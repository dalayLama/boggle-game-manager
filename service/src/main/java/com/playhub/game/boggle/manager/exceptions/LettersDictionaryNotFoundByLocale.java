package com.playhub.game.boggle.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Locale;

public class LettersDictionaryNotFoundByLocale extends BoggleGameManagerException {

    private static final String MESSAGE_TEMPLATE = "Letters dictionary not found for locale %s";

    private final Locale locale;

    public LettersDictionaryNotFoundByLocale(Locale locale) {
        super(MESSAGE_TEMPLATE.formatted(locale.toLanguageTag()), Error.LETTERS_DICTIONARY_NOT_FOUND_BY_LOCALE);
        this.locale = locale;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Object[] getDetailMessageArguments() {
        return new Object[] {locale.toLanguageTag()};
    }

}
