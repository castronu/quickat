package org.quickat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
//@EnableJpaRepositories
//@Import(RepositoryRestMvcConfiguration.class)
public class QuickatApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickatApplication.class, args);
    }
}
