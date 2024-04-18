package be.vdab.cinefest.films;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

record NieuweReservatie(@NotNull @Email String emailAdres, @Positive int plaatsen) {
}
