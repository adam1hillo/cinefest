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
@Sql({"/films.sql", "/reservaties.sql"})
@AutoConfigureMockMvc
class ReservatieControllerTest {
    private final static String RESERVATIES_TABLE = "reservaties";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    ReservatieControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }
    private long idVanTest1Film() {
        return jdbcClient.sql("select if from films where titel = 'test1'")
                .query(Long.class)
                .single();
    }
    @Test
    void findByEmailAdresVindtDeJuisteReservaties() throws Exception {
        var aantalReservaties = JdbcTestUtils.countRowsInTableWhere(jdbcClient,RESERVATIES_TABLE,
                "emailAdres = 'test@example.org'");
        mockMvc.perform(get("/reservaties?emailAdres={emailAdres}", "test@example.org"))
                .andExpectAll(status().isOk(),
                        jsonPath("length()").value(aantalReservaties));
    }
}
