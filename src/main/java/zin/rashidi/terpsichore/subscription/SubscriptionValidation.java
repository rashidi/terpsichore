package zin.rashidi.terpsichore.subscription;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import zin.rashidi.terpsichore.course.CourseManagement;
import zin.rashidi.terpsichore.student.StudentManagement;

/**
 * @author Rashidi Zin
 */
@Configuration
class SubscriptionValidation implements RepositoryRestConfigurer {

    private final CourseManagement courses;
    private final StudentManagement students;

    SubscriptionValidation(CourseManagement courses, StudentManagement students) {
        this.courses = courses;
        this.students = students;
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", new Validator() {
            @Override
            public boolean supports(Class<?> clazz) {
                return Subscription.class.isAssignableFrom(clazz);
            }

            @Override
            public void validate(Object target, Errors errors) {
                var subscription = (Subscription) target;

                if (!courses.isActive(subscription.courseId())) {
                    errors.rejectValue("courseId", "course.inactive", "Course is inactive");
                }

                if (!students.isActive(subscription.studentId())) {
                    errors.rejectValue("studentId", "student.inactive", "Student is inactive");
                }

            }
        });
    }

}
