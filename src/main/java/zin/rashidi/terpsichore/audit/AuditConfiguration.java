package zin.rashidi.terpsichore.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

/**
 * @author Rashidi Zin
 */
@Configuration
@EnableJdbcAuditing(modifyOnCreate = false)
class AuditConfiguration {
}
