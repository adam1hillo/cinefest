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
    private int vrijePlaatsen;
    private final BigDecimal aankoopprijs;

    void reserveer(int aantalPlaatsen) {
        if (aantalPlaatsen > vrijePlaatsen) {
            throw new OnvoldoendeVrijePlaatsenException();
        }
        vrijePlaatsen -= aantalPlaatsen;
    }
}
