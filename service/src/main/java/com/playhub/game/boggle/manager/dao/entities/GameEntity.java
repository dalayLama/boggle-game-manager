package com.playhub.game.boggle.manager.dao.entities;

import com.playhub.game.boggle.manager.dao.converters.DurationToLongConverter;
import com.playhub.game.boggle.manager.dao.converters.LocaleToStringConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.playhub.game.boggle.manager.models.GameState;
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
import org.hibernate.annotations.UuidGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "games")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString(exclude = {"rounds"})
public class GameEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "owner_id", nullable = false, updatable = false)
    @NotNull
    private UUID ownerId;

    @Column(name = "room_id", nullable = false, updatable = false)
    @NotNull
    private UUID roomId;

    @Column(name = "locale", nullable = false, updatable = false)
    @NotNull
    @Convert(converter = LocaleToStringConverter.class)
    private Locale locale;

    @Column(name = "grid_size", nullable = false, updatable = false)
    @Min(4)
    private int gridSize;

    @Column(name = "rounds_duration", nullable = false, updatable = false)
    @NotNull
    @Convert(converter = DurationToLongConverter.class)
    private Duration roundsDuration;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Fetch(FetchMode.SUBSELECT)
    private List<RoundEntity> rounds = new ArrayList<>();

    @Column(name = "state", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private GameState state;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.VM)
    private Instant createdAt;

    @Column(name = "started_at", nullable = false)
    @UpdateTimestamp(source = SourceType.VM)
    private Instant startedAt;

    @Column(name = "finished_at", nullable = false, updatable = false)
    private Instant finishedAt;

    public void addRound(RoundEntity round) {
        round.setGame(this);
        rounds.add(round);
    }

}
