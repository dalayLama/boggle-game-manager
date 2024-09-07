package com.playhub.game.boggle.manager.dao.repositories;

import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RoundRepository extends JpaRepository<RoundEntity, Long> {

    @Query(value = "SELECT r.id, r.game_id, r.number, r.board, r.state, r.created_at, r.started_at, r.finished_at"
            + " FROM rounds r"
            + " WHERE r.game_id = :gameId AND r.state = 'WAITING'"
            + " ORDER BY r.number ASC"
            + " LIMIT 1"
            + " FOR NO KEY UPDATE", nativeQuery = true)
    Optional<RoundEntity> findAndBlockFirstWaitingRound(@NotNull @Param("gameId") UUID gameId);

    @Query("SELECT count(r.id) > 0 FROM RoundEntity r WHERE r.game.id = :gameId AND r.state = 'PLAYING'")
    boolean gameHasActiveRound(@NotNull @Param("gameId") UUID gameId);

}
