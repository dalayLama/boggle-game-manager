package com.playhub.game.boggle.manager.mappers;


import com.playhub.game.boggle.manager.dto.GameDto;
import com.playhub.game.boggle.manager.dto.NewGameRequestDto;
import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import com.playhub.game.boggle.manager.test.builders.GameDtoTestBuilder;
import com.playhub.game.boggle.manager.test.builders.GameTestBuilder;
import com.playhub.game.boggle.manager.test.builders.NewGameRequestDtoTestBuilder;
import com.playhub.game.boggle.manager.test.builders.NewGameRequestTestBuilder;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GameDtoMapperTest {

    private final GameDtoMapper mapper = Mappers.getMapper(GameDtoMapper.class);

    @Test
    void shouldConvertNewGameRequestDtoToNewGameRequest() {
        NewGameRequestDto dto = NewGameRequestDtoTestBuilder.aDto().build();
        UUID ownerId = UUID.fromString("bcd5aec4-9ed4-4647-b90c-1e76b0363996");

        NewGameRequest expectedResult = NewGameRequestTestBuilder.from(dto, ownerId).build();

        NewGameRequest result = mapper.toNewGameRequest(dto, ownerId);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void shouldConvertGameToGameDto() {
        Game game = GameTestBuilder.aGame().build();
        GameDto expectedResult = GameDtoTestBuilder.from(game).build();

        GameDto result = mapper.toGameDto(game);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

}