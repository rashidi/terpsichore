package zin.rashidi.terpsichore.student;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import zin.rashidi.terpsichore.TestcontainersConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author Rashidi Zin
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class InactiveStudentTests {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    @DisplayName("When the student name Carlos Rodriguez turn INACTIVE Then his Active courses will be CANCELLED")
    void activeSubscriptionsAreCancelled() {
        var content = """
                {
                  "status": "INACTIVE"
                }
                """;

        mvc.patch().uri("/students/{id}", 1L).content(content).contentType(APPLICATION_JSON).assertThat().hasStatus(NO_CONTENT);

        assertThat(cancelledSubscriptionsByStudent(1L)).isEqualTo(1);
    }

    private int cancelledSubscriptionsByStudent(Long studentId) {
        return JdbcTestUtils.countRowsInTableWhere(jdbc, "subscription", "student_id = " + studentId + " AND status = 'CANCELLED'");
    }

}
