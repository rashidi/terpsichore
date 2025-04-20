package zin.rashidi.terpsichore.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Rashidi Zin
 */
@Table
class Course {

    @Id
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private Status status;

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    enum Status {
        ACTIVE, CANCELLED
    }

}
