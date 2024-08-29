package com.playhub.game.boggle.manager.converters;

import com.playhub.game.boggle.manager.dao.converters.DurationToLongConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DurationToLongConverterTest {

    @InjectMocks
    private DurationToLongConverter converter;

    @Test
    void shouldConvertToDatabaseColumn() {
        Duration duration = Duration.parse("PT1M");
        long expectedResult = duration.toNanos();

        long actualResult = converter.convertToDatabaseColumn(duration);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void shouldConvertToEntityAttribute() {
        long nanos = 60000000000L;
        Duration expectedResult = Duration.ofNanos(nanos);

        Duration actualResult = converter.convertToEntityAttribute(nanos);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

}