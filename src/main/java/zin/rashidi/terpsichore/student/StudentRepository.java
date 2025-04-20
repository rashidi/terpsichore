package zin.rashidi.terpsichore.student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Rashidi Zin
 */
@RestResource(exported = false)
interface StudentRepository extends CrudRepository<Student, Long> {

    boolean existsByIdAndStatus(Long id, Student.Status status);

}
