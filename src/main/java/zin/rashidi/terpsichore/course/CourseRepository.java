package zin.rashidi.terpsichore.course;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Rashidi Zin
 */
interface CourseRepository extends CrudRepository<Course, Long> {

    boolean existsByIdAndStatus(Long id, Course.Status status);

}
