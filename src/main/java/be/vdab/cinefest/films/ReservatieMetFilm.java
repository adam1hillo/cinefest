package be.vdab.cinefest.films;

import java.time.LocalDateTime;

record ReservatieMetFilm(long id, String titel, int plaatsen, LocalDateTime besteld) {
}
