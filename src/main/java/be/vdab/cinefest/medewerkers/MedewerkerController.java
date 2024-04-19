package be.vdab.cinefest.medewerkers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("medewerkers")
@RestController
class MedewerkerController {
    private final MedewerkerService medewerkerService;

    MedewerkerController(MedewerkerService medewerkerService) {
        this.medewerkerService = medewerkerService;
    }
    @GetMapping(params = {"stukVoornaam", "stukFamilienaam"})
    List<Medewerker> findByStukVoornaamEnStukFamilienaam(String stukVoornaam, String stukFamilienaam) {
        return medewerkerService.findByStukVoornaamEnStukFamilienaam(stukVoornaam, stukFamilienaam);

    }



}
