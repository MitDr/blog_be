package org.project.blog.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "org.project.blog.Repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class DataSourceConfig {

    //DataSource
    @Value("${datasource.url}")
    private String jdbcUrl;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;
    @Value("${datasource.driver-class-name}")
    private String driverClassName;
    @Value("${datasource.maximum-pool-size}")
    private int maximumPoolSize;

    //Hibernate
    @Value("${jpa.hibernate.ddl-auto}")
    private String hibernateDdlAuto;
    @Value("${jpa.show-sql}")
    private boolean hibernateShowSql;
    @Value("${jpa.properties.hibernate.format_sql}")
    private boolean hibernateFormatSql;
    @Value("${jpa.database-platform}")
    private String hibernateDatabasePlatform;
    @Value("${jpa.database}")
    private String hibernateDatabase;
    @Value("${jpa.properties.hibernate.generate_statistics}")
    private boolean hibernateGenerateStatistics;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);

        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(dataSource())
                .packages("org.project.blog.Entity")
                .properties(hibernateProperties())
                .build();
    }

    private Map<String, Object> hibernateProperties() {
        Map<String, Object> map = new HashMap<>();
        map.put("hibernate.hbm2ddl.auto", hibernateDdlAuto);
        map.put("hibernate.show_sql", hibernateShowSql);
        map.put("hibernate.format_sql", hibernateFormatSql);
        map.put("hibernate.database.platform", hibernateDatabasePlatform);
        map.put("hibernate.database", hibernateDatabase);
        map.put("hibernate.generate_statistics", hibernateGenerateStatistics);
        return map;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
