package com.playhub.game.boggle.manager.dao.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class CharacterMatrixConverter implements AttributeConverter<List<List<Character>>, String[][]> {

    @Override
    public String[][] convertToDatabaseColumn(List<List<Character>> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return new String[0][0];
        }

        int size = attribute.size();
        String[][] matrix = new String[size][size];

        for (int i = 0; i < size; i++) {
            String[] rowAsArray = attribute.get(i).stream()
                    .map(String::valueOf)
                    .toArray(String[]::new);
            matrix[i] = rowAsArray;
        }

        return matrix;
    }

    @Override
    public List<List<Character>> convertToEntityAttribute(String[][] dbData) {
        if (dbData == null || dbData.length == 0) {
            return new ArrayList<>();
        }

        List<List<Character>> matrix = new ArrayList<>();
        for (String[] row : dbData) {
            List<Character> charactersRow = Arrays.stream(row)
                    .map(item -> item.charAt(0))
                    .collect(Collectors.toCollection(ArrayList::new));
            matrix.add(charactersRow);
        }

        return matrix;
    }
}
