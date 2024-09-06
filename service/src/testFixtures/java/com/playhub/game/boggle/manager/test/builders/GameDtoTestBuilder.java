package com.playhub.game.boggle.manager.test.builders;

import com.jimbeam.test.utils.common.TestObjectBuilder;
import com.playhub.game.boggle.manager.consts.LocaleConsts;
import com.playhub.game.boggle.manager.dto.GameDto;
import com.playhub.game.boggle.manager.dto.GameStateDto;
import com.playhub.game.boggle.manager.models.Game;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aDto")
@With
public class GameDtoTestBuilder implements TestObjectBuilder<GameDto> {

    private UUID id = UUID.randomUUID();

    private UUID ownerId = UUID.randomUUID();

    private UUID roomId = UUID.randomUUID();

    private Locale locale = LocaleConsts.EN;

    private GameStateDto state = GameStateDto.WAITING;

    private Instant createdAt = Instant.now();

    private Instant startedAt = Instant.now();

    private Instant finishedAt = null;

    public static GameDtoTestBuilder from(Game game) {
        return aDto()
                .withId(game.id())
                .withOwnerId(game.ownerId())
                .withRoomId(game.roomId())
                .withLocale(game.locale())
                .withState(GameStateDto.valueOf(game.state().name()))
                .withCreatedAt(game.createdAt())
                .withStartedAt(game.startedAt())
                .withFinishedAt(game.finishedAt());
    }

    @Override
    public GameDto build() {
        return new GameDto(
                id, ownerId, roomId, locale, state, createdAt, startedAt, finishedAt
        );
    }

}
