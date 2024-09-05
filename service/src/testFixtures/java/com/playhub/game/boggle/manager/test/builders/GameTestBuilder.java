package com.playhub.game.boggle.manager.test.builders;

import com.jimbeam.test.utils.common.TestObjectBuilder;
import com.playhub.game.boggle.manager.consts.LocaleConsts;
import com.playhub.game.boggle.manager.dao.entities.GameEntity;
import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.GameState;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aGame")
@With
public class GameTestBuilder implements TestObjectBuilder<Game> {

    private UUID id = UUID.randomUUID();

    private UUID ownerId = UUID.randomUUID();

    private UUID roomId = UUID.randomUUID();

    private Locale locale = LocaleConsts.EN;

    private GameState state = GameState.WAITING;

    private Instant createdAt = Instant.now();

    private Instant startedAt = Instant.now();

    private Instant finishedAt = null;

    public static GameTestBuilder from(GameEntity entity) {
        return aGame()
                .withId(entity.getId())
                .withOwnerId(entity.getOwnerId())
                .withRoomId(entity.getRoomId())
                .withLocale(entity.getLocale())
                .withState(entity.getState())
                .withCreatedAt(entity.getCreatedAt())
                .withStartedAt(entity.getStartedAt())
                .withFinishedAt(entity.getFinishedAt());
    }

    @Override
    public Game build() {
        return new Game(
                id, ownerId, roomId, locale, state, createdAt, startedAt, finishedAt
        );
    }

}
