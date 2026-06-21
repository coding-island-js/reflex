# Reflex — reaction-time & aim-trainer game

A small, fast web game: test your reaction time and aiming, then see how you rank against everyone else.

Built as a full-stack reference app:

| Layer | Tech |
|---|---|
| Backend | **Java 21 · Spring Boot 3 · Spring Data JPA / Hibernate** |
| Database | **PostgreSQL 16** — stored functions, a stored procedure, views, and a trigger |
| Frontend | **React + Redux** (Vite) — *added in the next step* |
| Packaging | **Docker** + Docker Compose (one command to run the whole stack) |

## Run it locally

You only need Docker installed (no Java/Maven/Node required — Docker builds everything):

```bash
docker compose up --build
```

- API: http://localhost:8080/api/health
- Postgres: localhost:5432 (db `reflex`, user `reflex`, password `reflex`)

## API

| Method | Path | Purpose |
|---|---|---|
| POST | `/api/scores` | Submit a score; returns your rank + percentile |
| GET | `/api/leaderboard?mode=reaction&limit=20` | Top players for a mode |
| GET | `/api/stats?mode=reaction` | Aggregate stats for a mode |
| GET | `/api/health` | Health check |

`mode` is `reaction` or `aim`.

## Database design (the interesting part)

All in `backend/src/main/resources/db/migration/V1__schema.sql`, managed by Flyway:

- **Trigger** `score_after_insert` — on every score insert, rolls up the `mode_stats` aggregate table and writes an append-only `score_audit` row. No application code maintains these.
- **Views** — `v_leaderboard` and `v_daily_leaderboard` rank each player's best score with a `RANK()` window function; `v_percentile_dist` buckets the score distribution.
- **Function** `fn_player_percentile(mode, score)` — returns where a score lands (0–100) versus everyone else in that mode.
- **Procedure** `sp_purge_audit(days)` — housekeeping to prune old audit rows (`CALL sp_purge_audit(30);`).
