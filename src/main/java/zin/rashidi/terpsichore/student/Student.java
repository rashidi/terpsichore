package zin.rashidi.terpsichore.student;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

/**
 * @author Rashidi Zin
 */
@Table
class Student {

    @Id
    private Long id;

    @Embedded(onEmpty = USE_EMPTY)
    private Name name;
    private Status status;

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    record Name(String first, String last) {}

    enum Status {
        ACTIVE, INACTIVE
    }

}
