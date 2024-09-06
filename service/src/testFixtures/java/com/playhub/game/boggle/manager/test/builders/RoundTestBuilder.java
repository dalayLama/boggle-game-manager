package com.playhub.game.boggle.manager.test.builders;

import com.jimbeam.test.utils.common.TestObjectBuilder;
import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundPlayerEntity;
import com.playhub.game.boggle.manager.models.BoggleBoard;
import com.playhub.game.boggle.manager.models.Round;
import com.playhub.game.boggle.manager.models.RoundState;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aWaiting")
@With
public class RoundTestBuilder implements TestObjectBuilder<Round> {

    private Long id = 1L;

    private UUID gameId = UUID.fromString("75cef4cd-0a2a-4457-ada0-8c9d2c784f2a");

    private int number = 1;

    private BoggleBoard board = new BoggleBoard(List.of(
            List.of('A', 'B'),
            List.of('C', 'V')
    ));

    private Set<UUID> players = Set.of(
            UUID.fromString("b15cc8e1-1316-4aac-bfb1-50dd6faba769"),
            UUID.fromString("270ef572-aa4b-45f1-8c79-5c8da52478e2")
    );

    private RoundState state = RoundState.WAITING;

    private Duration duration = Duration.ofMinutes(1L);

    private Instant createdAt = Instant.now();

    private Instant startedAt = null;

    private Instant finishedAt = null;

    public static RoundTestBuilder from(RoundEntity round) {
        return aWaiting()
                .withId(round.getId())
                .withGameId(round.getGame().getId())
                .withNumber(round.getNumber())
                .withBoard(round.getBoard())
                .withPlayers(round.getPlayers().stream().map(RoundPlayerEntity::getPlayerId).collect(Collectors.toSet()))
                .withState(round.getState())
                .withDuration(round.getGame().getRoundsDuration())
                .withCreatedAt(round.getCreatedAt())
                .withStartedAt(round.getStartedAt())
                .withFinishedAt(round.getFinishedAt());
    }

    @Override
    public Round build() {
        return new Round(id, gameId, number, board, players, state, duration, createdAt, startedAt, finishedAt);
    }

}
