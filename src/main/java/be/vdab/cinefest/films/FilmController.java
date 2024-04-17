package be.vdab.cinefest.films;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
class FilmController {

    private final FilmService filmService;

    FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("films/totaalvrijeplaatsen")
    long findTotaalVrijePlaatesen() {
        return filmService.findTotaalVrijePlaatsen();
    }
    @GetMapping("films/{id}")
    IdTitelJaarVrijePlaatsen findById(@PathVariable long id) {
        return filmService.findById(id)
                .map(IdTitelJaarVrijePlaatsen::new)
                .orElseThrow(()-> new FilmNietGevondenException(id));
    }
    @GetMapping("films")
    Stream<IdTitelJaarVrijePlaatsen> findAll() {
        return filmService.findAll()
                .stream()
                .map(IdTitelJaarVrijePlaatsen::new);
    }

    @GetMapping(value = "films", params = "jaar")
    Stream<IdTitelJaarVrijePlaatsen> findByJaar(int jaar) {
        return filmService.findByJaar(jaar)
                .stream()
                .map(IdTitelJaarVrijePlaatsen::new);
    }
    @DeleteMapping("films/{id}")
    void delete(@PathVariable long id) {
        filmService.delete(id);
    }

    @PostMapping("films")
    long create(@RequestBody @Valid NieuweFilm nieuweFilm) {
        return filmService.create(nieuweFilm);
    }

    @PatchMapping("films/{id}/titel")
    void updateTitel(@PathVariable long id, @RequestBody @NotBlank String nieuweTitel) {
        filmService.updateTitel(id, nieuweTitel);
    }




    private record IdTitelJaarVrijePlaatsen(long id, String titel, int jaar, int vrijePlaatsen) {
        IdTitelJaarVrijePlaatsen(Film film) {
            this(film.getId(), film.getTitel(), film.getJaar(), film.getVrijePlaatsen());
        }
    }

}
