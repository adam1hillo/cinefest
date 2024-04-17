package be.vdab.cinefest.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class FilmRepository {
    private final JdbcClient jdbcClient;

    FilmRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    long findTotaalVrijePlaatsen() {
        String sql = """
                select sum(vrijePlaatsen) as totaalVrijePlaatsen
                from films
                """;
        return jdbcClient.sql(sql)
                .query(Long.class)
                .single();
    }
    Optional<Film> findById(long id) {
        String sql = """
                select id, titel, jaar, vrijePlaatsen, aankoopprijs
                from films
                where id = ?
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Film.class)
                .optional();
    }
    List<Film> findAll() {
        String sql = """
                select id, titel, jaar, vrijePlaatsen, aankoopprijs
                from films
                order by titel
                """;
        return jdbcClient.sql(sql)
                .query(Film.class)
                .list();
    }
    List<Film> findByJaar(int jaar) {
        String sql = """
                select id, titel, jaar, vrijePlaatsen, aankoopprijs
                from films
                where jaar = ?
                order by titel;
                """;
        return jdbcClient.sql(sql)
                .param(jaar)
                .query(Film.class)
                .list();
    }
    void delete(long id) {
        String sql = """
                delete from films
                where id = ?
                """;
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }
    long create(Film film) {
        String sql = """
                insert into films (titel, jaar, vrijePlaatsen, aankoopprijs)
                values (?, ?, ?, ?)
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(film.getTitel(), film.getJaar(), film.getVrijePlaatsen(), film.getAankoopprijs())
                .update(keyHolder);
        return keyHolder.getKey().longValue();
    }
    void updateTitel(long id, String nieuweTitel) {
        String sql = """
                update films
                set titel = ?
                where id = ?
                """;
        if (jdbcClient.sql(sql).params(nieuweTitel, id).update() == 0) {
            throw new FilmNietGevondenException(id);
        }
    }

}
