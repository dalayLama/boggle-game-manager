package com.playhub.game.boggle.manager.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;

@Entity
@Table(name = "participant_answers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString(exclude = {"participant"})
public class RoundAnswerEntity {

    public static RoundAnswerEntity newAnswer(String answer) {
        return RoundAnswerEntity.builder()
                .id(null)
                .participant(null)
                .answer(answer)
                .createdAt(null)
                .build();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "round_id", referencedColumnName = "round_id"),
            @JoinColumn(name = "participant_id", referencedColumnName = "participant_id")
    })
    private RoundParticipantEntity participant;

    @Column(name = "answer", nullable = false, updatable = false)
    @NotNull
    @NotBlank
    private String answer;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.VM)
    private Instant createdAt;

}
