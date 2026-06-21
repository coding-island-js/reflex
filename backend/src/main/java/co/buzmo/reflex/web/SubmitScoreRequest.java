package co.buzmo.reflex.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record SubmitScoreRequest(
        @NotBlank String deviceId,
        String handle,
        String country,
        @NotBlank @Pattern(regexp = "reaction|aim") String mode,
        Integer reactionMs,
        BigDecimal accuracy,
        Integer targetsHit
) {}
