package co.buzmo.reflex.repo;

import co.buzmo.reflex.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    /** Calls the stored function fn_player_percentile(mode, score). */
    @Query(value = "SELECT fn_player_percentile(:mode, :score)", nativeQuery = true)
    BigDecimal percentile(@Param("mode") String mode, @Param("score") int score);

    /** A single player's rank, read from the v_leaderboard view. */
    @Query(value = "SELECT rank FROM v_leaderboard WHERE mode = :mode AND player_id = :playerId",
            nativeQuery = true)
    Long rankFor(@Param("mode") String mode, @Param("playerId") Long playerId);

    /** Top N of the v_leaderboard view for a mode. */
    @Query(value = "SELECT mode, player_id AS playerId, handle, best_score AS bestScore, rank " +
            "FROM v_leaderboard WHERE mode = :mode ORDER BY rank ASC LIMIT :limit",
            nativeQuery = true)
    List<LeaderboardRow> topLeaderboard(@Param("mode") String mode, @Param("limit") int limit);

    /** Aggregate stats maintained by the trigger, from mode_stats. */
    @Query(value = "SELECT mode, total_plays AS totalPlays, best_score AS bestScore, " +
            "avg_score AS avgScore FROM mode_stats WHERE mode = :mode",
            nativeQuery = true)
    StatsRow stats(@Param("mode") String mode);
}
