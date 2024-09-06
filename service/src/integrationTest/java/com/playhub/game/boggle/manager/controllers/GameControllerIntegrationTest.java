package com.playhub.game.boggle.manager.controllers;

import com.jimbeam.test.utils.spring.liquibase.LiquibaseConfig;
import com.jimbeam.test.utils.testcontainer.postgres.PostgresContainer;
import com.playhub.common.errors.managment.PlayHubErrorCodes;
import com.playhub.game.boggle.manager.App;
import com.playhub.game.boggle.manager.consts.LocaleConsts;
import com.playhub.game.boggle.manager.dto.GameStateDto;
import com.playhub.game.boggle.manager.rest.ApiPaths;
import com.playhub.game.boggle.manager.test.user.UserInfo;
import com.playhub.game.boggle.manager.test.user.UserInfoUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.Locale;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = {App.class, PostgresContainer.class, LiquibaseConfig.class}
)
@ActiveProfiles({"integration-test"})
@AutoConfigureMockMvc
public class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateGame() throws Exception {
        UserInfo userInfo = UserInfoUtils.getUserInfo();
        UUID roomId = UUID.fromString("1fa0786b-25ec-4b26-b94c-b4fc5ec5a010");
        Locale locale = LocaleConsts.EN;

        mockMvc.perform(post(ApiPaths.V1_GAMES)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userInfo.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "roomId": "%s",
                                    "rounds": 1,
                                    "gridSize": 4,
                                    "players": ["a921932b-cc1c-4b13-93da-3d613239e75a"],
                                    "locale": "%s",
                                    "roundsDuration": "PT1M"
                                }
                                """.formatted(roomId, locale.toLanguageTag())
                        ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.ownerId").value(userInfo.id().toString()))
                .andExpect(jsonPath("$.roomId").value(roomId.toString()))
                .andExpect(jsonPath("$.locale").value(locale.toLanguageTag()))
                .andExpect(jsonPath("$.state").value(GameStateDto.WAITING.name()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.startedAt").isNotEmpty())
                .andExpect(jsonPath("$.finishedAt").isEmpty())
                .andDo(print());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "en-EN, 0, 3, PT30S",
            "en-EN, 11, 9, PT4M",
            "ru-RU, 0, 3, PT30S",
            "ru-RU, 11, 9, PT4M"
    })
    void shouldReturn400IfRequestIsNotValid_whenCreatingGame(Locale locale,
                                                             int rounds,
                                                             int gridSize,
                                                             Duration roundsDuration) throws Exception {
        UserInfo userInfo = UserInfoUtils.getUserInfo();

        mockMvc.perform(post(ApiPaths.V1_GAMES)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userInfo.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "roomId": null,
                                    "rounds": %d,
                                    "gridSize": %d,
                                    "players": [],
                                    "locale": "%s",
                                    "roundsDuration": "%s"
                                }
                                """.formatted(rounds, gridSize, locale.toLanguageTag(), roundsDuration.toString())
                        ))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationDetails.gridSize", hasSize(2)))
                .andExpect(jsonPath("$.validationDetails.players", hasSize(1)))
                .andExpect(jsonPath("$.validationDetails.roundsDuration", hasSize(1)))
                .andExpect(jsonPath("$.validationDetails.rounds", hasSize(1)))
                .andExpect(jsonPath("$.validationDetails.roomId", hasSize(1)))
                .andExpect(jsonPath("$.errorCode").value(PlayHubErrorCodes.VALIDATION_ERROR_CODE))
                .andDo(print());
    }

    @Test
    void shouldReturn401IfBearerTokenIsNotDeclared_whenCreatingGame() throws Exception {
        mockMvc.perform(post(ApiPaths.V1_GAMES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "roomId": "1fa0786b-25ec-4b26-b94c-b4fc5ec5a010",
                                    "rounds": 1,
                                    "gridSize": 4,
                                    "players": ["a921932b-cc1c-4b13-93da-3d613239e75a"],
                                    "locale": "en-EN",
                                    "roundsDuration": "PT1M"
                                }
                                """
                        ))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

}
