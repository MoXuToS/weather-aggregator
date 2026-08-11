package ru.moxutos.meteo.weather.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Конфигурация для JPA репозиториев.
 */
@Configuration
@EnableTransactionManagement
@EntityScan("ru.moxutos.meteo.weather.model.domain.jpa")
@EnableJpaRepositories(basePackages = "ru.moxutos.meteo.weather.model.domain.jpa",
        entityManagerFactoryRef = "jpaEntityManager", transactionManagerRef = "jpaTransactionManager")
public class JpaDatasourceConfiguration {

    /**
     * Свойства источника данных.
     *
     * @return datasource properties
     */
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties jpaDataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * Генерируем дефолтный датасорс из конфига.
     *
     * @return datasource
     */
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource jpaDataSource() {
        return jpaDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }


    /**
     * Генерируем второй энтити менеджер из второго датасорса.
     *
     * @return entity manager bean
     */
    @Bean(name = "jpaEntityManager")
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(jpaDataSource())
                .packages("ru.moxutos.meteo.weather.model.domain.jpa")
                .build();
    }


    /**
     * Генерируем первый менеджер транзакций из первого энтити менеджера.
     *
     * @return transaction manager bean
     */
    @Bean
    public PlatformTransactionManager jpaTransactionManager(
            final @Qualifier("jpaEntityManager") EntityManagerFactory jpaEntityManager) {
        return new JpaTransactionManager(jpaEntityManager);
    }

}
