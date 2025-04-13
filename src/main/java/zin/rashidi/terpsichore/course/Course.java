package zin.rashidi.terpsichore.course;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Rashidi Zin
 */
@Table
class Course {

    @Id
    private Long id;
    private String title;
    private Status status;

    enum Status {
        ACTIVE, CANCELLED
    }

}
