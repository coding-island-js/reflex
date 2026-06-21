package co.buzmo.reflex.service;

import co.buzmo.reflex.model.Player;
import co.buzmo.reflex.model.Score;
import co.buzmo.reflex.repo.LeaderboardRow;
import co.buzmo.reflex.repo.PlayerRepository;
import co.buzmo.reflex.repo.ScoreRepository;
import co.buzmo.reflex.repo.StatsRow;
import co.buzmo.reflex.web.LeaderboardEntry;
import co.buzmo.reflex.web.ModeStatsDto;
import co.buzmo.reflex.web.ScoreResult;
import co.buzmo.reflex.web.SubmitScoreRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ScoreService {

    private final PlayerRepository players;
    private final ScoreRepository scores;

    public ScoreService(PlayerRepository players, ScoreRepository scores) {
        this.players = players;
        this.scores = scores;
    }

    @Transactional
    public ScoreResult submit(SubmitScoreRequest req) {
        Player player = players.findByDeviceId(req.deviceId())
                .orElseGet(() -> {
                    Player p = new Player();
                    p.setDeviceId(req.deviceId());
                    return p;
                });
        if (req.handle() != null && !req.handle().isBlank()) {
            player.setHandle(req.handle().trim());
        }
        if (req.country() != null && !req.country().isBlank()) {
            player.setCountry(req.country().trim().toUpperCase());
        }
        player = players.save(player);

        int rawScore = computeRawScore(req);

        Score score = new Score();
        score.setPlayerId(player.getId());
        score.setMode(req.mode());
        score.setReactionMs(req.reactionMs());
        score.setAccuracy(req.accuracy());
        score.setTargetsHit(req.targetsHit());
        score.setRawScore(rawScore);
        // Flush so the AFTER INSERT trigger runs before we read the views below.
        scores.saveAndFlush(score);

        BigDecimal percentile = scores.percentile(req.mode(), rawScore);
        Long rank = scores.rankFor(req.mode(), player.getId());
        StatsRow stats = scores.stats(req.mode());

        return new ScoreResult(
                rawScore,
                rank != null ? rank : 0L,
                percentile != null ? percentile : BigDecimal.ZERO,
                stats != null ? stats.getTotalPlays() : 0L);
    }

    /**
     * Score is computed server-side (never trusted from the client) so it is
     * always "higher = better", which keeps ranking and percentiles consistent.
     */
    private int computeRawScore(SubmitScoreRequest req) {
        return switch (req.mode()) {
            case "reaction" -> {
                int ms = req.reactionMs() != null ? req.reactionMs() : 1000;
                yield Math.max(0, 1000 - ms);
            }
            case "aim" -> {
                int hits = req.targetsHit() != null ? req.targetsHit() : 0;
                double acc = req.accuracy() != null ? req.accuracy().doubleValue() : 0d;
                yield (int) Math.round(hits * acc);
            }
            default -> throw new IllegalArgumentException("Unknown mode: " + req.mode());
        };
    }

    @Transactional(readOnly = true)
    public List<LeaderboardEntry> leaderboard(String mode, int limit) {
        return scores.topLeaderboard(mode, limit).stream()
                .map(r -> new LeaderboardEntry(
                        r.getRank(),
                        r.getPlayerId(),
                        r.getHandle(),
                        r.getBestScore() != null ? r.getBestScore() : 0))
                .toList();
    }

    @Transactional(readOnly = true)
    public ModeStatsDto stats(String mode) {
        StatsRow row = scores.stats(mode);
        if (row == null) {
            return new ModeStatsDto(mode, 0L, null, null);
        }
        return new ModeStatsDto(row.getMode(), row.getTotalPlays(), row.getBestScore(), row.getAvgScore());
    }
}
