package com.ucsmy.jindu.manage.config;

import com.ucsmy.jindu.manage.config.log4j2.LogFilter;
import com.ucsmy.jindu.manage.config.shiro.MyFormAuthenticationFilter;
import com.ucsmy.jindu.manage.config.shiro.MyRolesAuthorizationFilter;
import com.ucsmy.jindu.manage.config.shiro.ShiroRealmImpl;
import com.ucsmy.jindu.manage.config.shiro.csrf.CsrfAuthenticationStrategy;
import com.ucsmy.jindu.manage.config.shiro.csrf.CsrfFilter;
import com.ucsmy.jindu.manage.config.shiro.csrf.CsrfTokenRepository;
import com.ucsmy.jindu.manage.config.shiro.csrf.HttpSessionCsrfTokenRepository;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置 Created by
 * 
 * @author ucs_zhongtingyuan on 2017/3/28.
 */
@Configuration
public class ShiroConfig {
	private static Map<String, String> FILTERCHAINDEFINITIONMAP = new LinkedHashMap<>();

	@Bean
	public AuthorizingRealm getShiroRealm() {
		return new ShiroRealmImpl();
	}

	@Bean(name = "cacheManager")
	public CacheManager getCacheManager() {
		return new MemoryConstrainedCacheManager();
	}

	@Bean(name = "csrfTokenRepository")
	public CsrfTokenRepository getHttpCsrfTokenRepository() {
		return new HttpSessionCsrfTokenRepository();
	}

	@Bean(name = "csrfAuthenticationStrategy")
	public CsrfAuthenticationStrategy getCsrfAuthenticationStrategy() {
		CsrfAuthenticationStrategy cas = new CsrfAuthenticationStrategy();
		cas.setCsrfTokenRepository(getHttpCsrfTokenRepository());
		return cas;
	}

	/*
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}
	*/

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager() {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(getShiroRealm());
		dwsm.setCacheManager(getCacheManager());
		return dwsm;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(getDefaultWebSecurityManager());
		return new AuthorizationAttributeSourceAdvisor();
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
		// 增加Filter
		shiroFilterFactoryBean.getFilters().put("authc",
				new MyFormAuthenticationFilter(getHttpCsrfTokenRepository()));
		shiroFilterFactoryBean.getFilters().put("roles", new MyRolesAuthorizationFilter());
		shiroFilterFactoryBean.getFilters().put("csrf", new CsrfFilter(getHttpCsrfTokenRepository()));
		// 为日志输出添加ip和用户信息的上下文
		shiroFilterFactoryBean.getFilters().put("log", new LogFilter());
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/main/index");
		FILTERCHAINDEFINITIONMAP.put("/css/**", "anon");
		FILTERCHAINDEFINITIONMAP.put("/images/**", "anon");
		FILTERCHAINDEFINITIONMAP.put("/Javascript/**", "anon");
		FILTERCHAINDEFINITIONMAP.put("/libs/**", "anon");
		FILTERCHAINDEFINITIONMAP.put("/logout", "logout");
		FILTERCHAINDEFINITIONMAP.put("/pages/login/**", "anon");
		FILTERCHAINDEFINITIONMAP.put("/getRsa/**", "anon");
		FILTERCHAINDEFINITIONMAP.put("/bindAccount/**", "anon");
		FILTERCHAINDEFINITIONMAP.put("/pages/bind/**", "anon");
		FILTERCHAINDEFINITIONMAP.put("/favicon.ico/**", "anon");
		FILTERCHAINDEFINITIONMAP.put("/**", "log,csrf,authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(FILTERCHAINDEFINITIONMAP);
		return shiroFilterFactoryBean;
	}

}
