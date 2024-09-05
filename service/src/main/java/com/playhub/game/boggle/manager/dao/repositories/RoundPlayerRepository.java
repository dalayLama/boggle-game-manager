package com.playhub.game.boggle.manager.dao.repositories;

import com.playhub.game.boggle.manager.dao.entities.RoundPlayerEntity;
import com.playhub.game.boggle.manager.dao.entities.RoundPlayerId;
import com.playhub.game.boggle.manager.models.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface RoundPlayerRepository extends JpaRepository<RoundPlayerEntity, RoundPlayerId> {

    @Query("SELECT p.id.playerId FROM RoundPlayerEntity p " +
            "WHERE p.id.playerId IN (:playersIds) AND p.id.round.game.state IN (:activeStates)")
    Set<UUID> filterActivePlayers(@Param("playersIds") Set<UUID> playerIds,
                                  @Param("activeStates") Set<GameState> activeStates);

}
