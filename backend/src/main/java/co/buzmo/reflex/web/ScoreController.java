package co.buzmo.reflex.web;

import co.buzmo.reflex.service.ScoreService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScoreController {

    private final ScoreService service;

    public ScoreController(ScoreService service) {
        this.service = service;
    }

    @PostMapping("/scores")
    public ScoreResult submit(@Valid @RequestBody SubmitScoreRequest req) {
        return service.submit(req);
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardEntry> leaderboard(@RequestParam String mode,
                                              @RequestParam(defaultValue = "20") int limit) {
        return service.leaderboard(mode, Math.min(Math.max(limit, 1), 100));
    }

    @GetMapping("/stats")
    public ModeStatsDto stats(@RequestParam String mode) {
        return service.stats(mode);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
