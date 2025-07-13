package zin.rashidi.terpsichore.invoice;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import zin.rashidi.terpsichore.subscription.SubscriptionRegistered;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * @author Rashidi Zin
 */
@Service
class InvoiceManagement {

    private final InvoiceRepository invoices;

    InvoiceManagement(InvoiceRepository invoices) {
        this.invoices = invoices;
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void createOnSubscriptionRegistered(SubscriptionRegistered subscription) {
        var subscriptionId = subscription.id();

        invoices.save(new Invoice(subscriptionId));
    }

}
