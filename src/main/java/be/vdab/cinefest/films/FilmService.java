package be.vdab.cinefest.films;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
class FilmService {
    private final FilmRepository filmRepository;

    FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
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
}
