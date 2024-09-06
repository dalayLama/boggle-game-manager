package com.playhub.game.boggle.manager.test.builders;

import com.jimbeam.test.utils.common.TestObjectBuilder;
import com.playhub.game.boggle.manager.consts.LocaleConsts;
import com.playhub.game.boggle.manager.dto.NewGameRequestDto;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aRequest")
@With
public class NewGameRequestTestBuilder implements TestObjectBuilder<NewGameRequest> {

    private UUID ownerId = UUID.randomUUID();

    private UUID roomId = UUID.randomUUID();

    private Locale locale = LocaleConsts.EN;

    private int gridSize = 4;

    private int rounds = 3;

    private Set<UUID> players = Set.of(UUID.randomUUID(), UUID.randomUUID());

    private Duration roundsDuration = Duration.ofMinutes(1L);

    public static NewGameRequestTestBuilder from(NewGameRequestDto dto, UUID ownerId) {
        return aRequest()
                .withOwnerId(ownerId)
                .withRoomId(dto.roomId())
                .withLocale(dto.locale())
                .withGridSize(dto.gridSize())
                .withRounds(dto.rounds())
                .withPlayers(dto.players())
                .withRoundsDuration(dto.roundsDuration());
    }

    @Override
    public NewGameRequest build() {
        return new NewGameRequest(
                ownerId, roomId, locale, gridSize, rounds, players, roundsDuration
        );
    }

}
