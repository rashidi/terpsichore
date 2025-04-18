package zin.rashidi.terpsichore.course;

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
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Rashidi Zin
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class CreateCourseTests {

    @Autowired
    private MockMvcTester mvc;

    @Test
    @DisplayName("Given a course with the title Salsa Fundamentals is active When I create a new course with the same name Then http bad requests will be returned")
    void withExistingTitle() {
        var content = """
                {
                  "title": "Salsa Fundamentals",
                  "status": "ACTIVE"
                }
                """;

        mvc.post()
                .uri("/courses")
                .contentType(APPLICATION_JSON)
                .content(content)
            .assertThat()
                .hasStatus(BAD_REQUEST)
                .matches(jsonPath("$.errors.[0].message", is("Course already exists")));
    }

    @Test
    @DisplayName("Given a course with the title Samba Performance is cancelled When I create a new course with the same name Then http CREATED will be returned")
    void withExistingTitleAndStatusCancelled() {
        var content = """
                {
                  "title": "Samba Performance",
                  "status": "ACTIVE"
                }
                """;

        mvc.post()
                .uri("/courses")
                .contentType(APPLICATION_JSON)
                .content(content)
            .assertThat()
                .hasStatus(CREATED);
    }

    @Test
    @DisplayName("Given a course with the title Salsa Cuban Advanced is unavailable When I create a new course with a new name Then http CREATED will be returned")
    void withNewTitle() {
        var content = """
                {
                  "title": "Salsa Cuban Advanced",
                  "status": "ACTIVE"
                }
                """;

        mvc.post()
                .uri("/courses")
                .contentType(APPLICATION_JSON)
                .content(content)
            .assertThat()
                .hasStatus(CREATED);
    }

}
