package com.playhub.game.boggle.manager.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playhub.game.boggle.manager.components.DefaultLettersDictionary;
import com.playhub.game.boggle.manager.components.LettersDictionary;
import com.playhub.game.boggle.manager.components.LettersDictionaryProvider;
import com.playhub.game.boggle.manager.components.LocaleMapLettersDictionaryProvider;
import com.playhub.game.boggle.manager.components.generators.BoggleDiceGenerator;
import com.playhub.game.boggle.manager.components.generators.impls.DefaultBoggleDiceGenerator;
import com.playhub.game.boggle.manager.consts.LocaleConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

@Configuration
public class BoggleGeneratorsConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public LettersDictionaryProvider lettersDictionaryProvider() throws IOException {
        Map<Locale, LettersDictionary> lettersDictionaryMap = Map.of(
                LocaleConsts.RU, ruLettersProvider(),
                LocaleConsts.EN, enLettersProvider()
        );
        return new LocaleMapLettersDictionaryProvider(lettersDictionaryMap);
    }

    @Bean
    public BoggleDiceGenerator boggleDiceGenerator() {
        return new DefaultBoggleDiceGenerator(new Random());
    }

    private LettersDictionary ruLettersProvider() throws IOException {
        return readFromFile("dictionaries/ru_letters.json");
    }

    private LettersDictionary enLettersProvider() throws IOException {
        return readFromFile("dictionaries/en_letters.json");
    }

    private LettersDictionary readFromFile(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        Map<String, List<Character>> map = objectMapper.readValue(resource.getFile(), new TypeReference<>() {});
        return new DefaultLettersDictionary(map.get("commonLetters"), map.get("rareLetters"));
    }

}
