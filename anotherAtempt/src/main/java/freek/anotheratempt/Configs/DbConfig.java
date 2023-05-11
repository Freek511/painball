package freek.anotheratempt.Configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "freek.anotheratempt.Repo"
        }
)
@EnableAspectJAutoProxy
@ComponentScan
public class DbConfig {}

