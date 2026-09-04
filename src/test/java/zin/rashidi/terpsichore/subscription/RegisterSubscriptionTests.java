package zin.rashidi.terpsichore.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import zin.rashidi.terpsichore.TestcontainersConfiguration;

import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Long.parseLong;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Rashidi Zin
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class RegisterSubscriptionTests {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    @DisplayName("Given the course with id 5 is CANCELLED When I register a new subscription with it Then error Course is inactive will be returned")
    void registerWithInactiveCourse() {
        var content = """
                {
                  "courseId": 5,
                  "studentId": 1,
                  "status": "ACTIVE"
                }
                """;

        mvc.post()
                .uri("/subscriptions")
                .contentType(APPLICATION_JSON)
                .content(content)
            .assertThat()
                .hasStatus(BAD_REQUEST)
                .matches(jsonPath("$.errors.[0].message", is("Course is inactive")));
    }

    @Test
    @DisplayName("Given the student with id 4 is INACTIVE When I register a new subscription with it Then error Student is inactive will be returned")
    void registerWithInactiveStudent() {
        var content = """
                {
                  "courseId": 1,
                  "studentId": 4,
                  "status": "ACTIVE"
                }
                """;

        mvc.post()
                .uri("/subscriptions")
                .contentType(APPLICATION_JSON)
                .content(content)
                .assertThat()
                .hasStatus(BAD_REQUEST)
                .matches(jsonPath("$.errors.[0].message", is("Student is inactive")));
    }

    @Test
    @DisplayName("When a subscription is registered Then an invoice for it is created")
    void register() {
        var subscriptionId = new AtomicLong(0L);
        var content = """
                {
                  "courseId": 1,
                  "studentId": 5,
                  "status": "ACTIVE"
                }
                """;

        mvc.post()
                .uri("/subscriptions")
                .contentType(APPLICATION_JSON)
                .content(content)
            .assertThat()
                .hasStatus(CREATED)
                .matches(header().exists(LOCATION))
                .apply(result -> subscriptionId.set(parseLong(
                        substringAfterLast(result.getResponse().getHeader(LOCATION), "/")
                )));

        var totalInvoicesBySubscription = countRowsInTableWhere(jdbc, "invoice", "subscription_id = %d AND status = 'UNPAID'".formatted(subscriptionId.get()));

        assertThat(totalInvoicesBySubscription).isEqualTo(1);
    }

}
