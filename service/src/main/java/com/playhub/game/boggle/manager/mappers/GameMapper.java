package com.playhub.game.boggle.manager.mappers;

import com.playhub.game.boggle.manager.dao.entities.GameEntity;
import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GameMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rounds", ignore = true)
    @Mapping(target = "state", constant = "WAITING")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "startedAt", ignore = true)
    @Mapping(target = "finishedAt", ignore = true)
    GameEntity toGameEntity(NewGameRequest request);

    Game toGame(GameEntity entity);

}
