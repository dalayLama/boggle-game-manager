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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "round_players")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString(exclude = {"id", "answers"})
public class RoundPlayerEntity {

    public static RoundPlayerEntity newRoundPlayer(UUID participantId) {
        RoundPlayerId id = new RoundPlayerId(null, participantId);
        return new RoundPlayerEntity(id, new ArrayList<>());
    }

    @EmbeddedId
    private RoundPlayerId id;

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Fetch(FetchMode.SUBSELECT)
    private List<PlayerAnswerEntity> answers;

    public void setRound(RoundEntity round) {
        this.id.setRound(round);
    }

    public RoundEntity getRound() {
        return this.id.getRound();
    }

    public void setPlayerId(UUID participantId) {
        this.id.setPlayerId(participantId);
    }

    public UUID getPlayerId() {
        return this.id.getPlayerId();
    }

    public void addAnswer(PlayerAnswerEntity newAnswer) {
        newAnswer.setPlayer(this);
        answers.add(newAnswer);
    }

}
