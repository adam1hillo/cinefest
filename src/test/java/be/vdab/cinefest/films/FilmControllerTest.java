package be.vdab.cinefest.films;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Sql("/films.sql")
@AutoConfigureMockMvc
public class FilmControllerTest {
    private static final String FILMS_TABLE = "films";
    private static final Path TEST_RESOURCES = Path.of("src/test/resources");

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
    @Test
    void deleteVerwijdertEenFilm() throws Exception {
        long id = idVanTest1Film();
        mockMvc.perform(delete("/films/{id}", id))
                .andExpect(status().isOk());
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FILMS_TABLE, "id = " + id)).isZero();
    }
    @Test
    void createVoegtEenFilmToe() throws Exception {
        String jsonData = Files.readString(TEST_RESOURCES.resolve("correcteFilm.json"));
        var resposeBody = mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FILMS_TABLE, "titel = 'test3' and id = " + resposeBody)).isOne();
    }
}
