package com.monitoringPal.global.DBConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Objects;

import static com.monitoringPal.global.DBConfig.DataSourceConfig.*;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
public class TransactionManagerConfig {
    public static final String DOMAIN_ENTITY_MANAGER_FACTORY = "domainEntityManagerFactory";
    public static final String META_ENTITY_MANAGER_FACTORY = "metaEntityManagerFactory";
    public static final String MONITOR_ENTITY_MANAGER_FACTORY = "monitorEntityManagerFactory";
    public static final String META_TRANSACTION_MANAGER = "metaTransactionManager";
    public static final String DOMAIN_TRANSACTION_MANAGER = "domainTransactionManager";
    public static final String MONITOR_TRANSACTION_MANAGER = "monitorTransactionManager";

    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;
    private final ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadataProviders;
    private final EntityManagerFactoryBuilder entityManagerFactoryBuilder;

    @Bean(name = DOMAIN_TRANSACTION_MANAGER)
    public PlatformTransactionManager domainTransactionManager(
            @Qualifier(DOMAIN_ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }

    @Bean(name = META_TRANSACTION_MANAGER)
    public PlatformTransactionManager metaTransactionManager(
            @Qualifier(META_ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }

    @Primary
    @Bean(name = MONITOR_TRANSACTION_MANAGER)
    public PlatformTransactionManager monitorTransactionManager(
            @Qualifier(MONITOR_ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }



    @Bean(name = DOMAIN_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean domainEntityManagerFactory(
            @Qualifier(DOMAIN_DATASOURCE) DataSource dataSource) {
        return EntityManagerFactoryCreator.builder()
                .properties(jpaProperties)
                .hibernateProperties(hibernateProperties)
                .metadataProviders(metadataProviders)
                .entityManagerFactoryBuilder(entityManagerFactoryBuilder)
                .dataSource(dataSource)
                .packages("com.monitoringPal.studyPal")
                .persistenceUnit("domainUnit")
                .build()
                .create();
    }

    @Bean(name = META_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean metaEntityManagerFactory(
            @Qualifier(META_DATASOURCE) DataSource dataSource) {
        return EntityManagerFactoryCreator.builder()
                .properties(jpaProperties)
                .hibernateProperties(hibernateProperties)
                .metadataProviders(metadataProviders)
                .entityManagerFactoryBuilder(entityManagerFactoryBuilder)
                .dataSource(dataSource)
                .packages("com.monitoringPal.batchPal")
                .persistenceUnit("batchUnit")
                .build()
                .create();
    }

    @Primary
    @Bean(name = MONITOR_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean monitorEntityManagerFactory(
            @Qualifier(MONITOR_DATASOURCE) DataSource dataSource) {
        return EntityManagerFactoryCreator.builder()
                .properties(jpaProperties)
                .hibernateProperties(hibernateProperties)
                .metadataProviders(metadataProviders)
                .entityManagerFactoryBuilder(entityManagerFactoryBuilder)
                .dataSource(dataSource)
                .packages("com.monitoringPal.monitorPal")
                .persistenceUnit("monitorUnit")
                .build()
                .create();
    }



    @Configuration
    @EnableJpaRepositories(
            basePackages = "com.monitoringPal.studyPal",
            entityManagerFactoryRef = DOMAIN_ENTITY_MANAGER_FACTORY,
            transactionManagerRef = DOMAIN_TRANSACTION_MANAGER)
    public static class DomainJpaRepositoriesConfig {}

    @Configuration
    @EnableJpaRepositories(
            basePackages = "com.monitoringPal.batchPal",
            entityManagerFactoryRef = META_ENTITY_MANAGER_FACTORY,
            transactionManagerRef = META_TRANSACTION_MANAGER)
    public static class MetaJpaRepositoriesConfig {}

    @Configuration
    @EnableJpaRepositories(
            basePackages = "com.monitoringPal.monitorPal",
            entityManagerFactoryRef = MONITOR_ENTITY_MANAGER_FACTORY,
            transactionManagerRef = MONITOR_TRANSACTION_MANAGER)
    public static class MonitorJpaRepositoriesConfig {}

}