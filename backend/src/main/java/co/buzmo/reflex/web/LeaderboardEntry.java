package co.buzmo.reflex.web;

public record LeaderboardEntry(
        long rank,
        Long playerId,
        String handle,
        int bestScore
) {}
