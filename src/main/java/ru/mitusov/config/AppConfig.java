package ru.mitusov.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@ComponentScan(basePackages = "ru.mitusov")
@EnableWebMvc
@PropertySource("classpath:persistence-mysql.properties")
@EnableTransactionManagement
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    private Logger logger =
            Logger.getLogger(getClass().getName());

    @Bean
    public DataSource getDataSource() {

        ComboPooledDataSource pooledDataSource =
                new ComboPooledDataSource();
        try {
            pooledDataSource.setDriverClass(environment.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        logger.info("==>> jdbc.driver = " + environment.getProperty("jdbc.driver"));
        logger.info("==>> dbc.user = " + environment.getProperty("jdbc.user"));

        pooledDataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        pooledDataSource.setUser(environment.getProperty("jdbc.user"));
        pooledDataSource.setPassword(environment.getProperty("jdbc.password"));

        pooledDataSource.setInitialPoolSize(
                getIntProperty("connection.pool.initialPoolSize"));
        pooledDataSource.setMinPoolSize(
                getIntProperty("connection.pool.minPoolSize"));
        pooledDataSource.setMaxPoolSize(
                getIntProperty("connection.pool.maxPoolSize"));
        pooledDataSource.setMaxIdleTime(
                getIntProperty("connection.pool.maxIdleTime"));


        return pooledDataSource;
    }

    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));

        return props;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        // create session factorys
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // set the properties
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    private int getIntProperty(String propVal) {
        Objects.requireNonNull(propVal, "Some of the property for configuration of PoolSize is null");
        return Integer.parseInt(environment.getProperty(propVal));
    }
}
