package com.playhub.game.boggle.manager.dao.entities;

import com.playhub.game.boggle.manager.dao.converters.CharacterMatrixConverter;
import com.playhub.game.boggle.manager.models.BoggleBoard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.playhub.game.boggle.manager.models.RoundState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "rounds")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString(exclude = {"game", "players"})
public class RoundEntity {

    public static RoundEntity newRound(int number, BoggleBoard board, Set<UUID> players) {
        RoundEntity round = RoundEntity.builder()
                .id(null)
                .game(null)
                .number(number)
                .board(board)
                .players(new ArrayList<>())
                .state(RoundState.WAITING)
                .createdAt(null)
                .startedAt(null)
                .finishedAt(null)
                .build();

        players.forEach(id -> {
            RoundPlayerEntity player = RoundPlayerEntity.newRoundPlayer(id);
            round.addPlayer(player);
        });
        return round;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false, updatable = false)
    @NotNull
    private GameEntity game;

    @Column(name = "number", nullable = false, updatable = false)
    @Min(1L)
    private int number;

    @Column(name = "board", nullable = false, updatable = false)
    @NotNull
    @Convert(converter = CharacterMatrixConverter.class)
    private BoggleBoard board;

    @OneToMany(mappedBy = "id.round", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Fetch(FetchMode.SUBSELECT)
    private List<RoundPlayerEntity> players;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoundState state;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.VM)
    private Instant createdAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    public void addPlayer(RoundPlayerEntity player) {
        player.setRound(this);
        players.add(player);
    }
}
