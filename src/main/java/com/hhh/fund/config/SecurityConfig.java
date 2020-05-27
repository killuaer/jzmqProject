package com.hhh.fund.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.hhh.fund.security.ShiroDBRealm;
import com.hhh.fund.security.XssFilter;

/**
 * Shiro配置
 * 
 * @author 3hzl
 *
 */
@Configuration
public class SecurityConfig implements EnvironmentAware {

	private Environment env;

	@Override
	public void setEnvironment(final Environment environment) {
		this.env = environment;
	}

	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realms());
		securityManager.setSessionManager(sessionManager());
	    //注入记住我管理器  
		securityManager.setRememberMeManager(rememberMeManager());

		return securityManager;
	}

	@Bean(name="rememberMeManager")
	public RememberMeManager rememberMeManager() {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		// ookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位) 
		rememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
		rememberMeManager.setCookie(rememberMeCookie());
		return rememberMeManager;
	}

	@Bean(name="rememberMeCookie")
	public Cookie rememberMeCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setMaxAge(1209600); // 两周之内记住我
		return cookie;
	}

	protected SessionManager sessionManager() {
		return new ServletContainerSessionManager();
	}

	@Bean
	public AuthorizingRealm shiroDBRealm() {
		return new ShiroDBRealm();
	}

	@Bean
	public Collection<Realm> realms() {
		List<Realm> realms = new LinkedList<Realm>();
		realms.add(shiroDBRealm());
		return realms;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean(name = "xssFilter")
	public Filter xssFilter() {
		return new XssFilter();
	}

	@Bean(name = "shiroFilter")
	public Filter shiroFilter() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());

		shiroFilterFactoryBean.setLoginUrl(env.getProperty("loginURL"));
		shiroFilterFactoryBean.setSuccessUrl(env.getProperty("successURL"));
		shiroFilterFactoryBean.setUnauthorizedUrl(env.getProperty("unauthorizedURL"));

		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("anon", new AnonymousFilter());
		FormAuthenticationFilter formFilter = new FormAuthenticationFilter();
		formFilter.setRememberMeParam("rememberMe");
		filters.put("anthc", formFilter);
//		LogoutFilter logoutFilter = new LogoutFilter();
//		logoutFilter.setRedirectUrl("/admin/logout");
//		filters.put("logout", logoutFilter);
		shiroFilterFactoryBean.setFilters(filters);

		Map<String, String> filterChainDefMap = new LinkedHashMap<String, String>();
		filterChainDefMap.put("/admin/login", "anon");
		filterChainDefMap.put("/admin/keyToLogin", "anon");
//		filterChainDefMap.put("/admin/logout", "logout");
		filterChainDefMap.put("/admin/**", "user");
		filterChainDefMap.put("/fund/**", "user");
		filterChainDefMap.put("/dept/**", "user");
		filterChainDefMap.put("/param/**", "user");
		filterChainDefMap.put("/user/**", "user");
		filterChainDefMap.put("/role/**", "user");
		filterChainDefMap.put("/testItem/**", "user");
		filterChainDefMap.put("/video/**", "user");
		filterChainDefMap.put("/todo/**", "user");
		filterChainDefMap.put("/notice/**", "user");
		filterChainDefMap.put("/equipment/**", "user");
		filterChainDefMap.put("/organization/**", "user");
		filterChainDefMap.put("/staff/**", "user");
		filterChainDefMap.put("/testAbility/**", "user");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefMap);
		try {
			return (Filter) shiroFilterFactoryBean.getObject();
		} catch (Exception ex) {
			throw new BeanCreationException("shiroFilter", "FactoryBean throw exception on object creation", ex);
		}
	}
}
