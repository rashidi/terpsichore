package zin.rashidi.terpsichore.subscription;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import zin.rashidi.terpsichore.course.CourseCancelled;
import zin.rashidi.terpsichore.student.StudentInactivated;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * @author Rashidi Zin
 */
@Component
class SubscriptionEventManagement {

    private final SubscriptionRepository subscriptions;

    SubscriptionEventManagement(SubscriptionRepository subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void cancelByCourse(CourseCancelled course) {
        subscriptions.cancelByCourse(course.id());
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void cancelByStudent(StudentInactivated student) {
        subscriptions.cancelByStudent(student.id());
    }

}
