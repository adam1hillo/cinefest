package be.vdab.cinefest.films;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;
@RequestMapping("films")
@RestController
class FilmController {

    private final FilmService filmService;

    FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("totaalvrijeplaatsen")
    long findTotaalVrijePlaatesen() {
        return filmService.findTotaalVrijePlaatsen();
    }

    @GetMapping("{id}")
    IdTitelJaarVrijePlaatsen findById(@PathVariable long id) {
        return filmService.findById(id)
                .map(IdTitelJaarVrijePlaatsen::new)
                .orElseThrow(()-> new FilmNietGevondenException(id));
    }
    @GetMapping
    Stream<IdTitelJaarVrijePlaatsen> findAll() {
        return filmService.findAll()
                .stream()
                .map(IdTitelJaarVrijePlaatsen::new);
    }

    @GetMapping(params = "jaar")
    Stream<IdTitelJaarVrijePlaatsen> findByJaar(int jaar) {
        return filmService.findByJaar(jaar)
                .stream()
                .map(IdTitelJaarVrijePlaatsen::new);
    }
    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        filmService.delete(id);
    }

    @PostMapping
    long create(@RequestBody @Valid NieuweFilm nieuweFilm) {
        return filmService.create(nieuweFilm);
    }

    @PatchMapping("{id}/titel")
    void updateTitel(@PathVariable long id, @RequestBody @NotBlank String nieuweTitel) {
        filmService.updateTitel(id, nieuweTitel);
    }

    @PostMapping("{id}/reservaties")
    long reserveer(@PathVariable long id, @RequestBody @Valid NieuweReservatie nieuweReservatie) {
        return filmService.reserveer(id, nieuweReservatie);
    }



    private record IdTitelJaarVrijePlaatsen(long id, String titel, int jaar, int vrijePlaatsen) {
        IdTitelJaarVrijePlaatsen(Film film) {
            this(film.getId(), film.getTitel(), film.getJaar(), film.getVrijePlaatsen());
        }
    }

}
