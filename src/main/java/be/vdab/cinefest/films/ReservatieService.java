package be.vdab.cinefest.films;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
class ReservatieService {
    private final ReservatieRepository reservatieRepository;

    ReservatieService(ReservatieRepository reservatieRepository) {
        this.reservatieRepository = reservatieRepository;
    }
    List<ReservatieMetFilm> findByEmailAdres(String emailAdres) {
        return reservatieRepository.findByEmailAdres(emailAdres);
    }
}
