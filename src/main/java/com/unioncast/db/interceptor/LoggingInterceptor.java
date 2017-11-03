package com.unioncast.db.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>
 * 日志拦截器设置
 * </p>
 * 
 * @author dmpchengyunyun
 * @date 2016年10月10日上午10:33:49
 */
@Configuration
public class LoggingInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptor.class);

	/** 请求执行开始时间 */
	public static final String START_TIME = "_start_time";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOG.info("current visiting uri: {}", request.getRequestURI());
		long startTime = System.currentTimeMillis();
		request.setAttribute(START_TIME, startTime);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String requestUri = request.getRequestURI();
		long startTime = (Long) request.getAttribute(START_TIME);
		long executeTime = System.currentTimeMillis() - startTime;
		LOG.info("current visiting uri: {}, use total time(ms): {}", requestUri, executeTime);
	}

}
