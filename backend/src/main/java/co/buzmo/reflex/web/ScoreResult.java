package co.buzmo.reflex.web;

import java.math.BigDecimal;

/** Returned after submitting a score. */
public record ScoreResult(
        int rawScore,
        long rank,
        BigDecimal percentile,
        long totalPlays
) {}
