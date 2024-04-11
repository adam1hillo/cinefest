package be.vdab.cinefest.films;

import org.springframework.web.bind.annotation.GetMapping;
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
}
