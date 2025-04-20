package zin.rashidi.terpsichore.course;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;

import static zin.rashidi.terpsichore.course.Course.Status.CANCELLED;

/**
 * @author Rashidi Zin
 */
@Configuration
class CourseEventManagement {

    @Bean
    public ApplicationListener<AfterSaveEvent<Course>> courseCancelled(ApplicationEventPublisher publisher) {
        return event -> {
            var course = event.getEntity();

            if (course.getStatus() == CANCELLED) {
                publisher.publishEvent(new CourseCancelled(course.getId()));
            }

        };
    }

}
