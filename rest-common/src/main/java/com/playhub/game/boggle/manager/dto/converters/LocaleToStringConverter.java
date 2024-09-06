package com.playhub.game.boggle.manager.dto.converters;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Locale;

public class LocaleToStringConverter extends StdConverter<Locale, String> {

    @Override
    public String convert(Locale locale) {
        return locale != null ? locale.toLanguageTag() : null;
    }

}
