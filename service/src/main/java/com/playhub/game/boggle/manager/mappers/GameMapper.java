package com.playhub.game.boggle.manager.mappers;

import com.playhub.game.boggle.manager.dao.entities.GameEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundPlayerEntity;
import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.Round;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "duration", source = "game.roundsDuration")
    Round toRound(RoundEntity round);

    default Set<UUID> roundPlayersToPlayerIds(Collection<? extends RoundPlayerEntity> players) {
        return players.stream().map(RoundPlayerEntity::getPlayerId).collect(Collectors.toSet());
    }

}
