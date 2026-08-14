package zin.rashidi.terpsichore.subscription;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;

import static java.util.Objects.isNull;

/**
 * @author Rashidi Zin
 */
@Configuration
class SubscriptionEventManagement {

    @Bean
    public ApplicationListener<AfterSaveEvent<Subscription>> subscriptionCreated(ApplicationEventPublisher publisher) {
        return event -> {
            var subscription = event.getEntity();

            if (isNull(subscription.getModifiedDate())) {
                publisher.publishEvent(new SubscriptionRegistered(subscription.getId()));
            }

        };
    }

}
