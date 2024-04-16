package be.vdab.cinefest.medewerkers;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class MedewerkerRepository {

    private final JdbcClient jdbcClient;

    MedewerkerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Medewerker> findByStukVoornaamEnStukFamilienaam(String stukVoornaam, String stukFamilienaam) {
        String sql = """
                select id, voornaam, familienaam
                from medewerkers
                where voornaam like ? and familienaam like ?
                order by voornaam, familienaam;
                """;
        return jdbcClient.sql(sql)
                .params("%" + stukVoornaam + "%", "%" + stukFamilienaam + "%")
                .query(Medewerker.class)
                .list();
    }
}
