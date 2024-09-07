package com.playhub.game.boggle.manager.controllers;

import com.jimbeam.test.utils.spring.liquibase.LiquibaseConfig;
import com.jimbeam.test.utils.testcontainer.postgres.PostgresContainer;
import com.playhub.common.errors.managment.PlayHubErrorCodes;
import com.playhub.game.boggle.manager.App;
import com.playhub.game.boggle.manager.consts.LocaleConsts;
import com.playhub.game.boggle.manager.dto.GameStateDto;
import com.playhub.game.boggle.manager.dto.RoundStateDto;
import com.playhub.game.boggle.manager.exceptions.Error;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.Locale;
import java.util.UUID;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
    @Sql(scripts = "/sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
                .andExpect(jsonPath("$.startedAt").isEmpty())
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

    @Test
    @Sql(scripts = "/sql/created-game.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldStartNextRound() throws Exception {
        UserInfo userInfo = UserInfoUtils.getUserInfo();
        UUID gameId = UUID.fromString("8e5a5e37-6c7e-4cc3-a010-7458db3e80bf");

        String[] expectedPlayers = {userInfo.id().toString(), "0d57d4cd-8218-4ac8-950a-1d876d0f0293"};

        mockMvc.perform(post(ApiPaths.V1_NEXT_ROUND, gameId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userInfo.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(gameId.toString()))
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.board", contains(
                        contains("A", "B"),
                        contains("C", "V"),
                        contains("A", "B"),
                        contains("C", "V")
                )))
                .andExpect(jsonPath("$.players", contains(expectedPlayers)))
                .andExpect(jsonPath("$.state").value(RoundStateDto.PLAYING.name()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.startedAt").isNotEmpty())
                .andExpect(jsonPath("$.finishedAt").isEmpty())
                .andDo(print());
    }

    @Test
    @Sql(scripts = "/sql/finished-game.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn403WhenStartingNextRound_ifGameIsFinished() throws Exception {
        UserInfo userInfo = UserInfoUtils.getUserInfo();
        UUID gameId = UUID.fromString("8e5a5e37-6c7e-4cc3-a010-7458db3e80bf");

        mockMvc.perform(post(ApiPaths.V1_NEXT_ROUND, gameId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userInfo.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode").value(Error.INVALID_GAME_STATE.name()))
                .andDo(print());
    }

    @Test
    @Sql(scripts = "/sql/game-with-active-round.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn409WhenStartingNextRound_ifGameHasAnActiveRound() throws Exception {
        UserInfo userInfo = UserInfoUtils.getUserInfo();
        UUID gameId = UUID.fromString("8e5a5e37-6c7e-4cc3-a010-7458db3e80bf");

        mockMvc.perform(post(ApiPaths.V1_NEXT_ROUND, gameId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userInfo.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value(Error.GAME_HAS_ACTIVE_ROUND.name()))
                .andDo(print());
    }

    @Test
    @Sql(scripts = "/sql/game-with-not-enough-players-in-round.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturn403WhenStartingNextRound_ifGameRoundDoesNotHaveEnoughPlayers() throws Exception {
        UserInfo userInfo = UserInfoUtils.getUserInfo();
        UUID gameId = UUID.fromString("8e5a5e37-6c7e-4cc3-a010-7458db3e80bf");

        mockMvc.perform(post(ApiPaths.V1_NEXT_ROUND, gameId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + userInfo.jwtToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode").value(Error.ROUND_NOT_HAVE_ENOUGH_PLAYERS.name()))
                .andDo(print());
    }

}
