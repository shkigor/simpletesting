package ck.solo.simpletesting.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// @Configuration indicates that this class contains one or more bean methods
// annotated with @Bean producing beans manageable by spring container.
// In our case, this class represent hibernate configuration.
@Configuration

// @EnableTransactionManagement is equivalent to Spring’s tx:* XML namespace,
// enabling Spring’s annotation-driven transaction management capability.
@EnableTransactionManagement

// @ComponentScan is equivalent to context:component-scan base-package="..." in
// xml,
// providing with where to look for spring managed beans/classes.
@ComponentScan({ "ck.solo.simpletesting.configuration" })

// @PropertySource is used to declare a set of properties
// (defined in a properties file in application classpath) in Spring run-time
// Environment,
// providing flexibility to have different values in different application
// environments.
// /src/main/webapp/resources/application.properties
@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfiguration {

	@Autowired
	private Environment environment;

	/**
	 * Method sessionFactory() is creating a LocalSessionFactoryBean, which exactly mirrors the XML based configuration : We need a
	 * dataSource and hibernate properties (same as hibernate.properties). Thanks to @PropertySource, we can externalize the real values in
	 * a .properties file, and use Spring’s Environment to fetch the value corresponding to an item.
	 * 
	 * @return LocalSessionFactoryBean
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "ck.solo.simpletesting.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		return properties;
	}

	/**
	 * Once the SessionFactory is created, it will be injected into Bean method transactionManager which may eventually provide transaction
	 * support for the sessions created by this sessionFactory.
	 * 
	 * @param s
	 *            SessionFactory
	 * @return HibernateTransactionManager
	 */
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
}