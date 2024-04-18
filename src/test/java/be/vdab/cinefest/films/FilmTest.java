package be.vdab.cinefest.films;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FilmTest {

    @Test
    void reserveerBijVoldoendeVrijePlaatsenWijzigtVrijePlaatsen() {
        Film film = new Film(1, "test1", 2000, 5, BigDecimal.ONE);
        film.reserveer(5);
        assertThat(film.getVrijePlaatsen()).isZero();
    }
    @Test
    void reserveerBijOnvoldoendeVrijePlaatsenMislukt() {
        Film film = new Film(1, "test1", 2000, 5, BigDecimal.ONE);
        assertThatExceptionOfType(OnvoldoendeVrijePlaatsenException.class).isThrownBy(() -> film.reserveer(6));
    }
}
