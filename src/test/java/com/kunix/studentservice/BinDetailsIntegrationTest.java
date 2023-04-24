package com.kunix.studentservice;

import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import java.time.Duration;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@ContextConfiguration(initializers = {BinDetailsIntegrationTest.Initializer.class})
public class BinDetailsIntegrationTest {
    private static final String regex = ".*(\\\"message\\\":\\\\s?\\\"started\\\".*|] started\\n$)";
    private static PostgreSQLContainer postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("postgres");

        postgreSQLContainer.setWaitStrategy((new LogMessageWaitStrategy())
                .withRegEx(regex)
                .withStartupTimeout(Duration.ofSeconds(20L)));
    }


    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void test1() {
        postgreSQLContainer.withInitScript("scripts/data.sql");
        postgreSQLContainer.start();

        then(2).isEqualTo(2);

        postgreSQLContainer.stop();
    }
}
