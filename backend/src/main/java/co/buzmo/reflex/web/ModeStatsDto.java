package co.buzmo.reflex.web;

import java.math.BigDecimal;

public record ModeStatsDto(
        String mode,
        long totalPlays,
        Integer bestScore,
        BigDecimal avgScore
) {}
