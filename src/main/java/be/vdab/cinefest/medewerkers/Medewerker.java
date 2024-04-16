package be.vdab.cinefest.medewerkers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
class Medewerker {
    private long id;
    private String voornaam;
    private String familienaam;
}
