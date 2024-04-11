package be.vdab.cinefest.films;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
class Film {
    private final long id;
    private final String titel;
    private final int jaar;
    private final int vrijePlaatsen;
    private final BigDecimal aankoopprijs;
}
