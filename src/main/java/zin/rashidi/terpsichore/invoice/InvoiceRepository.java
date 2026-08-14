package zin.rashidi.terpsichore.invoice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @author Rashidi Zin
 */
@RestResource(exported = false)
interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
