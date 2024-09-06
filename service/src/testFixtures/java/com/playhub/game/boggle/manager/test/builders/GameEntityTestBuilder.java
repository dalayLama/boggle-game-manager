package com.playhub.game.boggle.manager.test.builders;

import com.jimbeam.test.utils.common.TestObjectBuilder;
import com.playhub.game.boggle.manager.consts.LocaleConsts;
import com.playhub.game.boggle.manager.dao.entities.GameEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import com.playhub.game.boggle.manager.models.GameState;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aWaiting")
@With
public class GameEntityTestBuilder implements TestObjectBuilder<GameEntity> {

    private UUID id = UUID.randomUUID();

    private UUID ownerId = UUID.randomUUID();

    private UUID roomId = UUID.randomUUID();

    private Locale locale = LocaleConsts.EN;

    private int gridSize = 4;

    private Duration roundsDuration = Duration.ofMinutes(1L);

    private List<RoundEntity> rounds = new ArrayList<>();

    private GameState state = GameState.WAITING;

    private Instant createdAt = Instant.now();

    private Instant startedAt = Instant.now();

    private Instant finishedAt = null;

    public static GameEntityTestBuilder from(NewGameRequest request) {
        return aWaiting()
                .withId(null)
                .withOwnerId(request.ownerId())
                .withRoomId(request.roomId())
                .withLocale(request.locale())
                .withGridSize(request.gridSize())
                .withRoundsDuration(request.roundsDuration())
                .withState(GameState.WAITING)
                .withCreatedAt(null)
                .withStartedAt(null)
                .withFinishedAt(null);
    }

    @Override
    public GameEntity build() {
        return new GameEntity(
                id, ownerId, roomId, locale, gridSize, roundsDuration, rounds, state, createdAt, startedAt, finishedAt
        );
    }
}
