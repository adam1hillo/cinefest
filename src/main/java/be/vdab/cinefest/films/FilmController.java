package be.vdab.cinefest.films;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    IdTiTelJaarVrijePlaatsen findById(@PathVariable long id) {
        return filmService.findById(id)
                .map(IdTiTelJaarVrijePlaatsen::new)
                .orElseThrow(()-> new FilmNietGevondenException(id));
    }
    private record IdTiTelJaarVrijePlaatsen(long id, String titel, int jaar, int vrijePlaatsen) {
        IdTiTelJaarVrijePlaatsen(Film film) {
            this(film.getId(), film.getTitel(), film.getJaar(), film.getVrijePlaatsen());
        }
    }
}
