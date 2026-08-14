package zin.rashidi.terpsichore.subscription;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Rashidi Zin
 */
@Table
class Subscription {

    @Id
    private Long id;
    private Long studentId;
    private Long courseId;
    private Status status;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate modifiedDate;

    public Long getId() {
        return id;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long courseId() {
        return courseId;
    }

    public Long studentId() {
        return studentId;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Subscription that && Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    enum Status {
        ACTIVE, COMPLETED, CANCELLED
    }

}
