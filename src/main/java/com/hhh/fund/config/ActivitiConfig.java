package com.hhh.fund.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.dmn.engine.DmnEngineConfiguration;
import org.activiti.dmn.engine.configurator.DmnEngineConfigurator;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.engine.runtime.Clock;
import org.activiti.form.api.FormRepositoryService;
import org.activiti.form.engine.FormEngineConfiguration;
import org.activiti.form.engine.configurator.FormEngineConfigurator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Activiti 工作流配置
 *
 */

@Configuration
public class ActivitiConfig {
	
	@Resource(name = "dataSource")
	private DruidDataSource dataSource;

	@Resource
	public Environment env;

	@Resource(name = "transactionManager")
	private PlatformTransactionManager transactionManager;
	
	@Resource(name = "processEngineConfiguration")
	private ProcessEngineConfigurationImpl processEngineCfg;

	@Bean(name = { "processEngineConfiguration" })
	public ProcessEngineConfigurationImpl processEngineConfiguration() {
		SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
		processEngineConfiguration.setDataSource(this.dataSource);
//		processEngineConfiguration.setDatabaseSchema("ACTIVITI");
		processEngineConfiguration.setDatabaseSchemaUpdate("true");
		processEngineConfiguration.setTransactionManager(this.transactionManager);
		processEngineConfiguration.setAsyncExecutorActivate(true);
		processEngineConfiguration.setAsyncExecutor(asyncExecutor());
		processEngineConfiguration.setActivityFontName("宋体");
		processEngineConfiguration.setLabelFontName("宋体");
		
		String emailHost = this.env.getProperty("email.host");
		if (StringUtils.hasLength(emailHost)) {
			processEngineConfiguration.setMailServerHost(emailHost);
			processEngineConfiguration.setMailServerPort(
					((Integer) this.env.getRequiredProperty("email.port", Integer.class)).intValue());

			Boolean useCredentials = (Boolean) this.env.getProperty("email.useCredentials", Boolean.class);
			if (Boolean.TRUE.equals(useCredentials)) {
				processEngineConfiguration.setMailServerUsername(this.env.getProperty("email.username"));
				processEngineConfiguration.setMailServerPassword(this.env.getProperty("email.password"));
			}
			Boolean emailSSL = (Boolean) this.env.getProperty("email.ssl", Boolean.class);
			if (emailSSL != null) {
				processEngineConfiguration.setMailServerUseSSL(emailSSL.booleanValue());
			}
			Boolean emailTLS = (Boolean) this.env.getProperty("email.tls", Boolean.class);
			if (emailTLS != null) {
				processEngineConfiguration.setMailServerUseTLS(emailTLS.booleanValue());
			}
		}
		processEngineConfiguration.setProcessDefinitionCacheLimit(
				((Integer) this.env.getProperty("activiti.process-definitions.cache.max", Integer.class,
						Integer.valueOf(128))).intValue());

		processEngineConfiguration.setEnableSafeBpmnXml(true);

		List<BpmnParseHandler> preParseHandlers = new ArrayList<>();
		processEngineConfiguration.setPreBpmnParseHandlers(preParseHandlers);

		FormEngineConfiguration formEngineConfiguration = new FormEngineConfiguration();
		formEngineConfiguration.setDataSource(this.dataSource);

		FormEngineConfigurator formEngineConfigurator = new FormEngineConfigurator();
		formEngineConfigurator.setFormEngineConfiguration(formEngineConfiguration);
		processEngineConfiguration.addConfigurator(formEngineConfigurator);

		DmnEngineConfiguration dmnEngineConfiguration = new DmnEngineConfiguration();
		dmnEngineConfiguration.setDataSource(this.dataSource);

		DmnEngineConfigurator dmnEngineConfigurator = new DmnEngineConfigurator();
		dmnEngineConfigurator.setDmnEngineConfiguration(dmnEngineConfiguration);
		processEngineConfiguration.addConfigurator(dmnEngineConfigurator);

		return processEngineConfiguration;
	}

	@Bean(name = { "processEngineFactoryBean" })
	public ProcessEngineFactoryBean processEngineFactoryBean() {
		ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
		factoryBean.setProcessEngineConfiguration(processEngineCfg);
		return factoryBean;
	}

	@Bean(name = { "processEngine" })
	public ProcessEngine processEngine() {
		try {
			return processEngineFactoryBean().getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Bean
	public AsyncExecutor asyncExecutor() {
		DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
		asyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(5000);
		asyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(5000);
		return asyncExecutor;
	}

	@Bean(name = { "clock" })
	@DependsOn({ "processEngine" })
	public Clock getClock() {
		return processEngineCfg.getClock();
	}

	@Bean
	public RepositoryService repositoryService() {
		return processEngine().getRepositoryService();
	}

	@Bean
	public RuntimeService runtimeService() {
		return processEngine().getRuntimeService();
	}

	@Bean(name = "task")
	public TaskService taskService() {
		return processEngine().getTaskService();
	}

	@Bean
	public HistoryService historyService() {
		return processEngine().getHistoryService();
	}

	@Bean
	public org.activiti.engine.FormService formService() {
		return processEngine().getFormService();
	}

	@Bean
	public IdentityService identityService() {
		return processEngine().getIdentityService();
	}

	@Bean
	public ManagementService managementService() {
		return processEngine().getManagementService();
	}

	@Bean
	public FormRepositoryService formEngineRepositoryService() {
		return processEngine().getFormEngineRepositoryService();
	}

	@Bean
	public org.activiti.form.api.FormService formEngineFormService() {
		return processEngine().getFormEngineFormService();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
