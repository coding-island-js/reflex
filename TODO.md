# Reflex — Roadmap / TODO

Next-session pickups, roughly in priority order.

## High value (resume signal)
- [ ] **Tests** — JUnit 5 + Testcontainers (real Postgres). Cover `ScoreService.submit` (trigger updates `mode_stats`, correct percentile + rank) and the view-backed repository queries. Wire into `mvn test`.
- [ ] **CI** — GitHub Actions: build + test on every push (hits the CI/CD job bullet). Add a status badge to the README.

## Polish
- [ ] Persist personal best per device; show it on the stage.
- [ ] Shareable result text ("faster than X% of players").
- [ ] Small "juice": sound + flash on hit / on green.
- [ ] Nicer empty-state when a leaderboard has 0 plays.

## Content / SEO
- [ ] 2-3 more guides in `content/Articles.java` (e.g. "average click speed", "reaction time by sport", "best monitor refresh rate for reaction"). Each widens AEO surface.
- [ ] Add an OG image (`og.png`) for link previews.

## Later / optional
- [ ] Go live: Lightsail (~$5/mo) OR a fresh AWS account ($200 credits / 6 months). Set `BASE_URL` + `CORS_ORIGINS` to the real domain.
- [ ] Basic anti-cheat: rate-limit `POST /api/scores` (server already recomputes score).
- [ ] If deploying to AWS: create a scoped IAM user (stop using the root key on account 633044122287).

---

**Already built:** Spring Boot + JPA/Hibernate REST API · Postgres (trigger, views, function, procedure) · React + Redux UI (reaction + aim) · Thymeleaf server-rendered guides + sitemap.xml + llms.txt · fully Dockerized · public GitHub repo.
