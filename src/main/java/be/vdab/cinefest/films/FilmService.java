package be.vdab.cinefest.films;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
class FilmService {
    private final FilmRepository filmRepository;
    private final ReservatieRepository reservatieRepository;

    FilmService(FilmRepository filmRepository, ReservatieRepository reservatieRepository) {
        this.filmRepository = filmRepository;
        this.reservatieRepository = reservatieRepository;
    }

    long findTotaalVrijePlaatsen() {
        return filmRepository.findTotaalVrijePlaatsen();
    }

    Optional<Film> findById(long id) {
        return filmRepository.findById(id);
    }

    List<Film> findAll() {
        return filmRepository.findAll();
    }

    List<Film> findByJaar(int jaar) {
        return filmRepository.findByJaar(jaar);
    }

    @Transactional
    void delete(long id) {
        filmRepository.delete(id);
    }

    @Transactional
    long create(NieuweFilm nieuweFilm) {
        Film film = new Film(0, nieuweFilm.titel(), nieuweFilm.jaar(), 0, BigDecimal.ZERO);
        return filmRepository.create(film);
    }

    @Transactional
    void updateTitel(long id, String nieuweTitel) {
        filmRepository.updateTitel(id, nieuweTitel);
    }

    @Transactional
    long reserveer(long filmId, NieuweReservatie nieuweReservatie) {
        Film film = filmRepository.findAndLockById(filmId)
                .orElseThrow(() -> new FilmNietGevondenException(filmId));
        Reservatie reservatie = new Reservatie(0, filmId, nieuweReservatie.emailAdres(), nieuweReservatie.plaatsen(), LocalDateTime.now());
        film.reserveer(reservatie.getPlaatsen());
        filmRepository.updateVrijePlaatsen(filmId, film.getVrijePlaatsen());
        return reservatieRepository.create(reservatie);
    }
}