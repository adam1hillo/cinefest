package be.vdab.cinefest.films;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
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
}
