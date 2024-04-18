package be.vdab.cinefest.films;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql("/films.sql")
class ReservatieRepositoryTest {

    private final JdbcClient jdbcClient;
    private final ReservatieRepository reservatieRepository;
    private static final String RESERVATIES_TABLE = "reservaties";
    private static final String FILMS_TABLE = "films";

    ReservatieRepositoryTest(JdbcClient jdbcClient, ReservatieRepository reservatieRepository) {
        this.jdbcClient = jdbcClient;
        this.reservatieRepository = reservatieRepository;
    }
    private long idVanTest1Film() {
        return jdbcClient.sql("select id from films where titel = 'test1'")
                .query(Long.class)
                .single();
    }

    @Test
    void createVoegtEenReservatieToe() {
        Reservatie reservatie = new Reservatie(0, idVanTest1Film(), "test", 1, LocalDateTime.now());
        long toegevoegdeId = reservatieRepository.create(reservatie);
        System.out.println(toegevoegdeId);
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, RESERVATIES_TABLE, "emailAdres = 'test' and id = " + toegevoegdeId)).isOne();
    }
}
