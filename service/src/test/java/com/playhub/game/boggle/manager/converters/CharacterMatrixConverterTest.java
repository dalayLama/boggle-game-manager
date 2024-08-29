package com.playhub.game.boggle.manager.converters;


import com.playhub.game.boggle.manager.dao.converters.CharacterMatrixConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CharacterMatrixConverterTest {

    @InjectMocks
    private CharacterMatrixConverter converter;

    @Test
    void shouldConvertToDatabaseColumn() {
        List<List<Character>> rows = List.of(
                List.of('a', 'b', 'c'),
                List.of('d', 'e', 'f')
        );

        String[][] expectedResult = new String[][]{
            {"a", "b", "c"},
            {"d", "e", "f"},
        };

        String[][] result = converter.convertToDatabaseColumn(rows);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldConvertToEntityAttribute() {
        String[][] arrays = new String[][]{
                {"a", "b", "c"},
                {"d", "e", "f"},
        };

        List<List<Character>> expectedResult = List.of(
                List.of('a', 'b', 'c'),
                List.of('d', 'e', 'f')
        );

        List<List<Character>> result = converter.convertToEntityAttribute(arrays);

        assertThat(result).isEqualTo(expectedResult);
    }

}