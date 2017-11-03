package com.unioncast.db.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.unioncast.common.user.model.Authentication;
import com.unioncast.common.user.model.User;
import com.unioncast.db.rdbms.core.service.common.AuthenticateService;
import com.unioncast.db.rdbms.core.service.common.UserService;

/**
 * <p>
 * token拦截器设置
 * </p>
 * 
 * @author dmpchengyunyun
 * @date 2016年10月10日上午10:34:13
 */
@Configuration
public class TokenInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private AuthenticateService authenticationService;
	
	@Autowired
	private UserService userService;

	private static final Logger LOG = LoggerFactory.getLogger(TokenInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authHeaderStr = request.getHeader("authHeader");
		if (StringUtils.isBlank(authHeaderStr)) {
			LOG.warn("authHeaders数据为空");
			return false;
		}
		LOG.info("authHeader:{}", authHeaderStr);
		String[] authHeaders = authHeaderStr.trim().split(";");
		if (authHeaders.length != 2) {
			LOG.warn("authHeaders格式不对,{}", authHeaderStr);
			return false;
		}
		Integer systemId = null;
		try {
			systemId = Integer.parseInt(authHeaders[0]);
		} catch (Exception e) {
			LOG.warn("解析systemId异常", authHeaders[0]);
			systemId = null;
			return false;
		}
		String token = authHeaders[1];
		if (authenticationService == null) {
			BeanFactory factory = WebApplicationContextUtils
					.getRequiredWebApplicationContext(request.getServletContext());
			authenticationService = (AuthenticateService) factory.getBean("authenticationService");
		}
		Authentication authHeader = authenticationService.findByToken(systemId, token);
		if (authHeader == null) {
			LOG.warn("数据没有匹配成功", authHeaderStr);
			return false;
		}
		
		String accountIdStr = request.getHeader("userId");
		if(StringUtils.isBlank(accountIdStr)) {
			LOG.warn("用户为空");
			return false;
		}
		
		User[] users = userService.find(Long.parseLong(accountIdStr));
		
		if(users == null || users.length == 0) {
			LOG.warn("当前用户不存在", accountIdStr);
			return false;
		}
		
		//需要将用户id放在session中还是？
		request.getSession().setAttribute("user", users[0]);
		
		return true;
	}
}
