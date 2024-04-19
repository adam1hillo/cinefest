package be.vdab.cinefest.films;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;
@RequestMapping("reservaties")
@RestController
class ReservatieController {
    private final ReservatieService reservatieService;

    ReservatieController(ReservatieService reservatieService) {
        this.reservatieService = reservatieService;
    }
    @GetMapping(params = "emailAdres")
    List<ReservatieMetFilm> findByEmailAdres(String emailAdres) {
        return reservatieService.findByEmailAdres(emailAdres);
    }
}
