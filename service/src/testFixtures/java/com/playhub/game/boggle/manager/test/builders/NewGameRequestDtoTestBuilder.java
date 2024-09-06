package com.playhub.game.boggle.manager.test.builders;

import com.jimbeam.test.utils.common.TestObjectBuilder;
import com.playhub.game.boggle.manager.consts.LocaleConsts;
import com.playhub.game.boggle.manager.dto.NewGameRequestDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aDto")
@With
public class NewGameRequestDtoTestBuilder implements TestObjectBuilder<NewGameRequestDto> {

    private UUID roomId = UUID.randomUUID();

    private Locale locale = LocaleConsts.EN;

    private int gridSize = 4;

    private int rounds = 3;

    private Set<UUID> players = Set.of(UUID.randomUUID(), UUID.randomUUID());

    private Duration roundsDuration = Duration.ofMinutes(1L);

    @Override
    public NewGameRequestDto build() {
        return new NewGameRequestDto(
                roomId, rounds, gridSize, players, locale, roundsDuration
        );
    }

}
