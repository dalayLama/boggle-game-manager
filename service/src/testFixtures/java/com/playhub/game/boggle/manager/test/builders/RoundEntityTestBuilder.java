package com.playhub.game.boggle.manager.test.builders;

import com.jimbeam.test.utils.common.TestObjectBuilder;
import com.playhub.game.boggle.manager.dao.entities.GameEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundPlayerEntity;
import com.playhub.game.boggle.manager.models.BoggleBoard;
import com.playhub.game.boggle.manager.models.RoundState;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aWaiting")
@With
public class RoundEntityTestBuilder implements TestObjectBuilder<RoundEntity> {

    private Long id = 1L;

    private GameEntity game = null;

    private int number = 1;

    private BoggleBoard board = new BoggleBoard(List.of(
            List.of('A', 'B'),
            List.of('C', 'V')
    ));

    private List<RoundPlayerEntity> players = new ArrayList<>();

    private RoundState state = RoundState.WAITING;

    private Instant createdAt = Instant.now();

    private Instant startedAt = null;

    private Instant finishedAt = null;

    public static RoundEntityTestBuilder aWaiting(GameEntity game) {
        return aWaiting()
                .withGame(game);
    }

    public static RoundEntityTestBuilder aNewRound(GameEntity game) {
        return aWaiting()
                .withId(null)
                .withGame(game);
    }

    @Override
    public RoundEntity build() {
        RoundEntity round = new RoundEntity(id, game, number, board, players, state, createdAt, startedAt, finishedAt);
        if (game != null) {
            game.addRound(round);
        }
        return round;
    }

}
