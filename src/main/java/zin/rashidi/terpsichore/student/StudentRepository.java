package zin.rashidi.terpsichore.student;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Rashidi Zin
 */
interface StudentRepository extends CrudRepository<Student, Long> {

    boolean existsByIdAndStatus(Long id, Student.Status status);

}
