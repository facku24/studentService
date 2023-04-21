package com.kunix.studentservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "postgresEntityManagerFactory", transactionManagerRef = "postgresTransactionManager",
        basePackages = {"com.kunix.studentservice.repository" })
public class postgresConfig {

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maxSize;

    @Value("${spring.datasource.reporting.url}")
    private String url;

    @Value("${spring.datasource.reporting.username}")
    private String username;

    @Value("${spring.datasource.reporting.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private Integer connectTimeout;

    /*@Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }*/

    @Bean(name = "postgresDataSource")
    @Primary
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(username);
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        return dataSource;
    }

    @Bean(name = "postgresEntityManagerFactory")
    @Primary
    public EntityManagerFactory postgresEntityManagerFactory(@Qualifier("postgresDataSource") DataSource postgresDataSource) {
        LocalContainerEntityManagerFactoryBean postgresFactory = new LocalContainerEntityManagerFactoryBean();
        postgresFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        postgresFactory.setPackagesToScan("com.kunix.studentservice.model");
        postgresFactory.setDataSource(postgresDataSource);
        postgresFactory.afterPropertiesSet();

        return postgresFactory.getObject();
    }

    @Bean(name = "postgresTransactionManager")
    @Primary
    public TransactionManager postgresTransactionManager(@Qualifier("postgresDataSource") DataSource postgresDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(postgresDataSource);
        return transactionManager;
    }
}
