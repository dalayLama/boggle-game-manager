package com.playhub.game.boggle.manager.dao.repositories;

import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import com.playhub.game.boggle.manager.models.RoundState;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoundRepository extends JpaRepository<RoundEntity, Long> {

    @Query(value = "SELECT r.id, r.game_id, r.number, r.board, r.state, r.created_at, r.started_at, r.finished_at"
            + " FROM rounds r"
            + " WHERE r.game_id = :gameId AND r.state = 'WAITING'"
            + " ORDER BY r.number ASC"
            + " LIMIT 1"
            + " FOR NO KEY UPDATE", nativeQuery = true)
    Optional<RoundEntity> findAndBlockFirstWaitingRound(@NotNull @Param("gameId") UUID gameId);

    @Query("SELECT r FROM RoundEntity r WHERE r.game.id = :gameId AND r.number = :number")
    Optional<RoundEntity> findRoundByNumber(@NotNull @Param("gameId") UUID gameId,
                                            @NotNull @Param("number") int number);

    @Query("SELECT count(r.id) > 0 FROM RoundEntity r WHERE r.game.id = :gameId AND r.state = 'PLAYING'")
    boolean gameHasActiveRound(@NotNull @Param("gameId") UUID gameId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RoundEntity r WHERE r.game.id = :gameId AND r.state in (:states)")
    List<RoundEntity> findRoundsAndBlock(@NotNull UUID gameId, @NotEmpty @Param("states") Set<RoundState> states);

}
