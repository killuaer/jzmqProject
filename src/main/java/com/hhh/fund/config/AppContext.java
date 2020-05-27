package com.hhh.fund.config;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.hhh.core.model.WebServiceConstants;
import com.hhh.system.service.LogService;
import com.hhh.util.JcjgExceptionHandler;

/**
 * Spring Application Context Configuration Class
 * 
 * @author 3hzl 注意：<br>
 *         <p>
 *         1、如果不采用Jpa的方式访问数据库，则不需要配置@EnableJpaRepositories。
 *         </p>
 *         <p>
 *         2、注意新增业务Jpa entity需要在entityManagerFactory增加对entity所在package的扫描。
 *         </p>
 */
@Configuration
@EnableScheduling
@PropertySource(value = { "classpath:db.properties", "classpath:security.properties", "classpath:app.properties" })
@ComponentScan(basePackages = { "com.hhh" }, excludeFilters = @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION))
@EnableJpaRepositories(basePackages = { "com.hhh.core.dao","com.hhh.system.dao","com.hhh.activiti.core.dao", "com.hhh.auth.dao","com.hhh.business.dao",
		"com.hhh.xm.dao" }, entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy
@Import(value = { CachingConfig.class, SecurityConfig.class, ActivitiConfig.class })
public class AppContext {
	private final static Logger logger = LoggerFactory.getLogger(AppContext.class);

	@Resource
	public Environment env;

	@Resource(name = "dataSource")
	private DruidDataSource dataSource;

	@Resource(name = "jpaVendorAdapter")
	private JpaVendorAdapter jpaVendorAdapter;

	@Resource(name = "entityManagerFactory")
	private EntityManagerFactory entityManagerFactory;

	/**
	 * 数据源绑定
	 * 
	 * @return
	 * @throws PropertyVetoException
	 * @throws SQLException
	 */
	@Bean(name = "dataSource")
	public DruidDataSource dataSource() throws PropertyVetoException, SQLException {
		DruidDataSource dataSource = new DruidDataSource();

		logger.info("my jdbc.driver = " + env.getProperty("jdbc.driver"));
		// 数据库连接
		dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pwd"));
		// 数据池配置
		dataSource.setMaxActive(Integer.parseInt(env.getProperty("datapool.maxActive")));
		dataSource.setInitialSize(Integer.parseInt(env.getProperty("datapool.initialSize")));
		dataSource.setMaxWait(Long.parseLong(env.getProperty("datapool.maxWait")));
		dataSource.setMinIdle(Integer.parseInt(env.getProperty("datapool.minIdle")));
		dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty("datapool.timeBetweenEvictionRunsMillis")));
		dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("datapool.minEvictableIdleTimeMillis")));
		dataSource.setValidationQuery(env.getProperty("datapool.validationQuery"));
		dataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("datapool.testWhileIdle")));
		dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("datapool.testOnBorrow")));
		dataSource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("datapool.testOnReturn")));

		// SQL输出
		dataSource.setFilters("stat, slf4j");

		logger.info("Data source properties: " + dataSource.getConnection());
		return dataSource;
	}

	/**
	 * Druid的日志过滤器设置
	 * 
	 * @return
	 */
	@Bean(name = "logFilter")
	public Slf4jLogFilter logFilter() {
		Slf4jLogFilter logFilter = new Slf4jLogFilter();
		logFilter.setStatementExecutableSqlLogEnable(true);
		return logFilter;
	}

	/**
	 * 慢速SQL过滤
	 * 
	 * @return
	 */
	@Bean(name = "statFilter")
	public StatFilter statFilter() {
		StatFilter statFilter = new StatFilter();

		statFilter.setSlowSqlMillis(Long.parseLong(env.getProperty("datapool.slowSQLMills")));
		statFilter.setLogSlowSql(Boolean.parseBoolean(env.getProperty("datapool.logSlowSql")));

		return statFilter;
	}

	/**
	 * JPA Vendor
	 * 
	 * @return
	 */
	@Bean(name = "jpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(false);
		jpaVendorAdapter.setDatabasePlatform(env.getProperty("jpa.dbPlatform"));

		return jpaVendorAdapter;
	}

	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lcemf = new LocalContainerEntityManagerFactoryBean();

		lcemf.setDataSource(dataSource);
		lcemf.setPackagesToScan("com.hhh.core.entity","com.hhh.system.entity","com.hhh.activiti.entity", "com.hhh.activiti.core.entity", "com.hhh.auth.entity","com.hhh.business.entity",
				"com.hhh.xm.entity");
		lcemf.setJpaVendorAdapter(jpaVendorAdapter);
		
		Properties jpaProperties = new Properties();
		// level-2-cache
		jpaProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		jpaProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
		jpaProperties.setProperty("hibernate.cache.use_query_cache", "true");
		jpaProperties.setProperty("hibernate.generate_statistics", "hibernate.generate_statistics");
		jpaProperties.setProperty("hibernate.show_sql", "true");
		lcemf.setJpaProperties(jpaProperties);
		
		logger.info("Before LocalContainerEntityManagerFactoryBean.afterPropertiesSet():::" + lcemf.getJpaPropertyMap());

		lcemf.setPersistenceUnitName("mysqlUnit");

		// 返回factory需要先设置afterProperties
		lcemf.afterPropertiesSet();
		logger.info("After LocalContainerEntityManagerFactoryBean.afterPropertiesSet():::" + lcemf.getJpaPropertyMap());
		return lcemf.getObject();
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();

		transactionManager.setDataSource(dataSource);
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate() throws PropertyVetoException, SQLException {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public EntityManager entityManager() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		logger.info("entityManager: " + entityManager.isOpen());
		return entityManager;
	}

	@Bean
	public JcjgExceptionHandler exceptionHander(LogService logservice) {
		return new JcjgExceptionHandler(logservice);
	}
	@Bean
	public String initWeixin() {
		WebServiceConstants.CUSTOMERID=env.getProperty("customerId");
		return "";
	}
}
