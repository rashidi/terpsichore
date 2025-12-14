package zin.rashidi.terpsichore.course;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import zin.rashidi.terpsichore.TestcontainersConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

/**
 * @author Rashidi Zin
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class CancelCourseTests {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    @DisplayName("When I cancel Advanced Rumba Then active subscriptions for the course will be CANCELLED")
    void subscriptionsAreCancelled() {
        var content = """
                {
                  "status": "CANCELLED"
                }
                """;

        mvc.patch().uri("/courses/{id}", 3L)
                .content(content)
                .contentType(APPLICATION_JSON)
            .assertThat()
                .hasStatus(NO_CONTENT);

        assertThat(cancelledSubscriptionsByCourse(3L)).isEqualTo(2);
    }

    private int cancelledSubscriptionsByCourse(Long courseId) {
        return countRowsInTableWhere(jdbc, "subscription", "course_id = " + courseId + " AND status = 'CANCELLED'");
    }

}
