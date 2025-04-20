package zin.rashidi.terpsichore.course;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import zin.rashidi.terpsichore.course.Course.Status;

/**
 * @author Rashidi Zin
 */
@RestResource(exported = false)
interface CourseRepository extends CrudRepository<Course, Long> {

    boolean existsByIdAndStatus(Long id, Status status);

    boolean existsByTitleAndStatus(String title, Status status);

}
