package zin.rashidi.terpsichore.course;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static zin.rashidi.terpsichore.course.Course.Status.ACTIVE;

/**
 * @author Rashidi Zin
 */
@Service
@Transactional(readOnly = true)
public class CourseManagement {

    private final CourseRepository courses;

    CourseManagement(CourseRepository courses) {
        this.courses = courses;
    }

    public boolean isActive(Long id) {
        return courses.existsByIdAndStatus(id, ACTIVE);
    }

}
