package be.vdab.cinefest.medewerkers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
class MedewerkerService {
    private final MedewerkerRepository medewerkerRepository;

    MedewerkerService(MedewerkerRepository medewerkerRepository) {
        this.medewerkerRepository = medewerkerRepository;
    }
    List<Medewerker> findByStukVoornaamEnStukFamilienaam(String stukVoornaam, String stukFamilienaam) {
        return medewerkerRepository.findByStukVoornaamEnStukFamilienaam(stukVoornaam, stukFamilienaam);
    }
}
