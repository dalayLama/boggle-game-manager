package com.playhub.game.boggle.manager.dao.entities;

import com.playhub.game.boggle.manager.dao.converters.CharacterMatrixConverter;
import com.playhub.game.boggle.manager.dao.converters.DurationToLongConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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
import javafx.css.converter.DurationConverter;
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

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "rounds")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString(exclude = {"game", "participants"})
public class RoundEntity {

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

    @Column(name = "chars_matrix", nullable = false, updatable = false)
    @NotNull
    @Convert(converter = CharacterMatrixConverter.class)
    private List<List<Character>> charsMatrix;

    @OneToMany(mappedBy = "id.round", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Fetch(FetchMode.SUBSELECT)
    private List<RoundParticipantEntity> participants;

    @Column(name = "state", nullable = false)
    @NotNull
    private RoundState state;

    @Column(name = "duration", nullable = false)
    @NotNull
    @Convert(converter = DurationToLongConverter.class)
    private Duration duration;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.VM)
    private Instant createdAt;

    @Column(name = "started_at", nullable = false)
    @UpdateTimestamp(source = SourceType.VM)
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

}
