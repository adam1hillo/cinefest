package be.vdab.cinefest.medewerkers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class MedewerkerController {
    private final MedewerkerService medewerkerService;

    MedewerkerController(MedewerkerService medewerkerService) {
        this.medewerkerService = medewerkerService;
    }
    @GetMapping(value = "medewerkers", params = {"stukVoornaam", "stukFamilienaam"})
    List<Medewerker> findByStukVoornaamEnStukFamilienaam(String stukVoornaam, String stukFamilienaam) {
        return medewerkerService.findByStukVoornaamEnStukFamilienaam(stukVoornaam, stukFamilienaam);

    }



}
