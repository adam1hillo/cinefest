package be.vdab.cinefest.films;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Sql("/films.sql")
@AutoConfigureMockMvc
public class FilmControllerTest {
    private static final String FILMS_TABLE = "films";

    private final JdbcClient jdbcClient;
    private final MockMvc mockMvc;

    public FilmControllerTest(JdbcClient jdbcClient, MockMvc mockMvc) {
        this.jdbcClient = jdbcClient;
        this.mockMvc = mockMvc;
    }

    @Test
    void findTotaalVrijePlaatsenVindtHetTotaalAantalVrijePlaatsen() throws Exception {
        var vrijePlaatsen = jdbcClient.sql(
                "select sum(vrijePlaatsen) from films ")
                        .query(Long.class)
                        .single();

        mockMvc.perform(get("/films/totaalvrijeplaatsen"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").value(vrijePlaatsen));
    }
    private long idVanTest1Film() {
        return jdbcClient.sql("select id from films where titel = 'test1'")
                .query(Long.class)
                .single();
    }
    @Test
    void findByIdMetEenBestaandeIdVidntDeFilm() throws Exception {
        long id = idVanTest1Film();
        mockMvc.perform(get("/films/{id}", id))
                .andExpectAll(
                    status().isOk(),
                    jsonPath("id").value(id),
                    jsonPath("titel").value("test1"));
    }
    @Test
    void findByIdMetOnbestaandeIdGeeftStatusNotFound() throws Exception {
        mockMvc.perform(get("/films/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
    @Test
    void findAllVindtAlleFilms() throws Exception {
        mockMvc.perform(get("/films"))
                .andExpectAll(status().isOk(),
                        jsonPath("length()")
                                .value(JdbcTestUtils.countRowsInTable(jdbcClient, FILMS_TABLE)));
    }
    @Test
    void findByJaarVindtJuisteFilms() throws Exception {
        mockMvc.perform(get("/films").param("jaar", "2001"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()")
                                .value(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FILMS_TABLE,
                                        "jaar = 2001")));
    }
}
