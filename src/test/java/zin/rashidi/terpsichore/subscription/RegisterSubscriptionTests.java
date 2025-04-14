package zin.rashidi.terpsichore.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import zin.rashidi.terpsichore.TestcontainersConfiguration;

import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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

}
