package com.playhub.game.boggle.manager.converters;

import com.playhub.game.boggle.manager.dao.converters.LocaleToStringConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LocaleToStringConverterTest {

    @InjectMocks
    private LocaleToStringConverter converter;

    @Test
    void shouldConvertToDatabaseColumn() {
        Locale locale = Locale.ENGLISH;
        String expectedResult = locale.toLanguageTag();

        String actualResult = converter.convertToDatabaseColumn(locale);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldConvertToEntityAttribute() {
        String languageTag = "en-EN";
        Locale expectedResult = Locale.forLanguageTag(languageTag);

        Locale actualResult = converter.convertToEntityAttribute(languageTag);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

}