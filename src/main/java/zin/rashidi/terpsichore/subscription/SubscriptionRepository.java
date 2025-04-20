package zin.rashidi.terpsichore.subscription;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Rashidi Zin
 */
@RestResource(exported = false)
interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    @Modifying
    @RestResource(exported = false)
    @Query("UPDATE subscription SET status = 'CANCELLED' WHERE course_id = :courseId AND status = 'ACTIVE'")
    void cancelByCourse(Long courseId);

    @Modifying
    @RestResource(exported = false)
    @Query("UPDATE subscription SET status = 'CANCELLED' WHERE student_id = :studentId AND status = 'ACTIVE'")
    void cancelByStudent(Long studentId);

}
