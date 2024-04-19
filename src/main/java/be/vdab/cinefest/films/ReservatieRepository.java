package be.vdab.cinefest.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class ReservatieRepository {
    private final JdbcClient jdbcClient;

    ReservatieRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    long create(Reservatie reservatie) {
        String sql = """
                insert into reservaties(filmId, emailAdres, plaatsen, besteld)
                values (?, ?, ?, ?);
                """;
        var keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sql)
                .params(reservatie.getFilmId(), reservatie.getEmailAdres(), reservatie.getPlaatsen(), reservatie.getBesteld())
                .update(keyHolder);
        return keyHolder.getKey().longValue();
    }
    List<ReservatieMetFilm> findByEmailAdres(String emailAdres) {
        String sql = """
                select reservaties.id, films.titel, plaatsen, besteld
                from reservaties inner join films
                on reservaties.filmId = films.id
                where emailAdres = ?
                order by id desc
                """;
        return jdbcClient.sql(sql)
                .param(emailAdres)
                .query(ReservatieMetFilm.class)
                .list();
    }
}
