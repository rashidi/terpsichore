package zin.rashidi.terpsichore.subscription;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Rashidi Zin
 */
@RepositoryRestResource
interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
