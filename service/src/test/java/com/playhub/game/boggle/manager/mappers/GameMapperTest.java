package com.playhub.game.boggle.manager.mappers;

import com.playhub.game.boggle.manager.dao.entities.GameEntity;
import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import com.playhub.game.boggle.manager.test.builders.GameEntityTestBuilder;
import com.playhub.game.boggle.manager.test.builders.GameTestBuilder;
import com.playhub.game.boggle.manager.test.builders.NewGameRequestTestBuilder;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class GameMapperTest {

    private final GameMapper mapper = Mappers.getMapper(GameMapper.class);

    @Test
    void shouldConvertNewGameRequestToEntity() {
        NewGameRequest request = NewGameRequestTestBuilder.aRequest().build();
        GameEntity expectedResult = GameEntityTestBuilder.from(request).build();

        GameEntity result = mapper.toGameEntity(request);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void shouldConvertGameEntityToGame() {
        GameEntity entity = GameEntityTestBuilder.aWaiting().build();
        Game expectedResult = GameTestBuilder.from(entity).build();

        Game result = mapper.toGame(entity);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);
    }

}