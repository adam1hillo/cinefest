package be.vdab.cinefest.films;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
class Reservatie {
    private final long id;
    private final long filmId;
    private final String emailAdres;
    private final int plaatsen;
    private final LocalDateTime besteld;
}
