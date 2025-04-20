package zin.rashidi.terpsichore.student;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;

/**
 * @author Rashidi Zin
 */
@Configuration
class StudentEventManagement {

    @Bean
    public ApplicationListener<AfterSaveEvent<Student>> studentActivated(ApplicationEventPublisher publisher) {
        return event -> {
            var student = event.getEntity();

            if (Student.Status.INACTIVE == student.getStatus()) {
                publisher.publishEvent(new StudentInactivated(student.getId()));
            }
        };
    };

}
