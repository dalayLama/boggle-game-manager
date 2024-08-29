package com.playhub.game.boggle.manager.dao.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name = "round_participants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString(exclude = {"id", "answers"})
public class RoundParticipantEntity {

    @EmbeddedId
    private RoundParticipantId id;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Fetch(FetchMode.SUBSELECT)
    private List<RoundAnswerEntity> answers;

    public void addAnswer(RoundAnswerEntity newAnswer) {
        if (newAnswer == null) {
            return;
        }
        newAnswer.setParticipant(this);
        answers.add(newAnswer);
    }
}
