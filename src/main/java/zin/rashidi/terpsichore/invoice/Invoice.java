package zin.rashidi.terpsichore.invoice;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

import static zin.rashidi.terpsichore.invoice.Invoice.Status.UNPAID;

/**
 * @author Rashidi Zin
 */
@Table
class Invoice {

    @Id
    private Long id;

    @CreatedDate
    private LocalDate issueDate;

    @LastModifiedDate
    private LocalDate modifiedDate;

    @InsertOnlyProperty
    private final Long subscriptionId;
    private BigDecimal amount = BigDecimal.valueOf(150);
    private Status status = UNPAID;

    Invoice(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    enum Status {
        UNPAID, PAID, OVERDUE
    }

}
