package com.playhub.game.boggle.manager.dao.repositories;

import com.playhub.game.boggle.manager.dao.entities.RoundEntity;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RoundRepository extends JpaRepository<RoundEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RoundEntity r WHERE r.game.id = :gameId AND r.state = 'WAITING' ORDER BY r.number ASC")
    Optional<RoundEntity> findAndBlockFirstWaitingRound(@NotNull @Param("gameId") UUID gameId);

    @Query("SELECT count(r.id) > 0 FROM RoundEntity r WHERE r.game.id = :gameId AND r.state = 'PLAYING'")
    boolean gameHasActiveRound(@NotNull @Param("gameId") UUID gameId);

}
