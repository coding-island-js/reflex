package co.buzmo.reflex.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id", nullable = false)
    private Long playerId;

    @Column(name = "mode", nullable = false)
    private String mode;

    @Column(name = "reaction_ms")
    private Integer reactionMs;

    @Column(name = "accuracy")
    private BigDecimal accuracy;

    @Column(name = "targets_hit")
    private Integer targetsHit;

    @Column(name = "raw_score", nullable = false)
    private Integer rawScore;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    public Long getId() { return id; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public Integer getReactionMs() { return reactionMs; }
    public void setReactionMs(Integer reactionMs) { this.reactionMs = reactionMs; }

    public BigDecimal getAccuracy() { return accuracy; }
    public void setAccuracy(BigDecimal accuracy) { this.accuracy = accuracy; }

    public Integer getTargetsHit() { return targetsHit; }
    public void setTargetsHit(Integer targetsHit) { this.targetsHit = targetsHit; }

    public Integer getRawScore() { return rawScore; }
    public void setRawScore(Integer rawScore) { this.rawScore = rawScore; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
}
