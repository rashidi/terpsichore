package zin.rashidi.terpsichore.student;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static zin.rashidi.terpsichore.student.Student.Status.ACTIVE;

/**
 * @author Rashidi Zin
 */
@Service
@Transactional(readOnly = true)
public class StudentManagement {

    private final StudentRepository students;

    StudentManagement(StudentRepository students) {
        this.students = students;
    }

    public boolean isActive(Long id) {
        return students.existsByIdAndStatus(id, ACTIVE);
    }

}
