package co.buzmo.reflex.repo;

/** Projection for the v_leaderboard view (native query result). */
public interface LeaderboardRow {
    String getMode();
    Long getPlayerId();
    String getHandle();
    Integer getBestScore();
    Long getRank();
}
