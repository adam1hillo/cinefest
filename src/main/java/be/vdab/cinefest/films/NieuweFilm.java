package be.vdab.cinefest.films;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record NieuweFilm(@NotBlank String titel, @Positive int jaar) {
}
