package mg.tife.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "mg.tife.infrastructure.jpa")
@EntityScan(basePackages = "mg.tife.infrastructure.entity")
public class JpaConfig {
}