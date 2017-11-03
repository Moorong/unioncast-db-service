package com.unioncast.db.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p>
 * 拦截器配置，可配置多个拦截器，有顺序关系
 * </p>
 * 
 * @author dmpchengyunyun
 * @date 2016年10月10日上午10:32:23
 */
@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 添加日志拦截器
		registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
		// 添加token拦截器
		// registry.addInterceptor(new
		// TokenInterceptor()).addPathPatterns("/**");
	}
}
