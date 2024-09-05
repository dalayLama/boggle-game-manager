package com.playhub.game.boggle.manager.mappers;

import com.playhub.game.boggle.manager.dto.GameDto;
import com.playhub.game.boggle.manager.dto.NewGameRequestDto;
import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GameDtoMapper {

    NewGameRequest toNewGameRequest(NewGameRequestDto dto, UUID ownerId);

    GameDto toGameDto(Game game);

}
