package zin.rashidi.terpsichore.course;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static zin.rashidi.terpsichore.course.Course.Status.ACTIVE;

/**
 * @author Rashidi Zin
 */
@Configuration
class CourseValidation implements RepositoryRestConfigurer {

    private final CourseRepository courses;

    CourseValidation(CourseRepository courses) {
        this.courses = courses;
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", new Validator() {

            @Override
            public boolean supports(Class<?> clazz) {
                return Course.class.isAssignableFrom(clazz);
            }

            @Override
            public void validate(Object target, Errors errors) {
                var course = (Course) target;
                var existsByTitle = courses.existsByTitleAndStatus(course.getTitle(), ACTIVE);

                if (existsByTitle) {
                    errors.rejectValue("title", "course.exists", "Course already exists");
                }

            }
        });
    }

}
