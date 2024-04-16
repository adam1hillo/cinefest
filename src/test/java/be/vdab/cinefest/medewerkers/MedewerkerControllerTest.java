package be.vdab.cinefest.medewerkers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Sql("/medewerkers.sql")
@AutoConfigureMockMvc
class MedewerkerControllerTest {
    private final JdbcClient jdbcClient;

    private final MockMvc mockMvc;
    private static final String MEDEWERKERS_TABLE = "medewerkers";

    MedewerkerControllerTest(JdbcClient jdbcClient, MockMvc mockMvc) {
        this.jdbcClient = jdbcClient;
        this.mockMvc = mockMvc;
    }
    @Test
    void findByStukVoornaamEnStukFamilienaamVindDeJuisteMedewerkers() throws Exception {
        mockMvc.perform(get("/medewerkers")
                .param("stukVoornaam", "testvoornaam")
                .param("stukFamilienaam", "testfamilienaam"))
                .andExpectAll(status().isOk(),
                        jsonPath("length()").value(JdbcTestUtils.countRowsInTableWhere(
                                jdbcClient, MEDEWERKERS_TABLE, "voornaam like '%testvoornaam%' and familienaam like '%testfamilienaam%'")));

    }
}
