package com.playhub.game.boggle.manager.dao.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

@Converter
public class LocaleToStringConverter implements AttributeConverter<Locale, String> {

    @Override
    public String convertToDatabaseColumn(Locale attribute) {
        return attribute != null ? attribute.toLanguageTag() : null;
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        return dbData != null ? Locale.forLanguageTag(dbData) : null;
    }

}
