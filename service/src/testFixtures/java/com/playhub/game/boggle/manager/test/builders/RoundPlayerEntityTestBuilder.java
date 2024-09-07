package com.playhub.game.boggle.manager.test.builders;

import com.jimbeam.test.utils.common.TestObjectBuilder;
import com.playhub.game.boggle.manager.dao.entities.PlayerAnswerEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundPlayerEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundPlayerId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(staticName = "anEntity")
@With
public class RoundPlayerEntityTestBuilder implements TestObjectBuilder<RoundPlayerEntity> {

    private RoundEntity round = null;

    private UUID playerId = UUID.randomUUID();

    private List<PlayerAnswerEntity> answers = new ArrayList<>();

    public static RoundPlayerEntityTestBuilder anEntity(RoundEntity round) {
        return new RoundPlayerEntityTestBuilder()
                .withRound(round);
    }

    @Override
    public RoundPlayerEntity build() {
        RoundPlayerId roundPlayerId = new RoundPlayerId(round, playerId);
        RoundPlayerEntity player = new RoundPlayerEntity(roundPlayerId, answers);
        if (round != null) {
            round.addPlayer(player);
        }
        return player;
    }

}
