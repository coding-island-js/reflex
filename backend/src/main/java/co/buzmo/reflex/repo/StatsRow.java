package co.buzmo.reflex.repo;

import java.math.BigDecimal;

/** Projection for the mode_stats aggregate table (native query result). */
public interface StatsRow {
    String getMode();
    Long getTotalPlays();
    Integer getBestScore();
    BigDecimal getAvgScore();
}
