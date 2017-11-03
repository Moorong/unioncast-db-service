package com.unioncast.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DbRestApiApplication {

//	private static final Logger LOG = LogManager.getLogger(DbRestApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DbRestApiApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurerAdapter adapter() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addInterceptors(InterceptorRegistry registry) {
//				super.addInterceptors(registry);
//				registry.addInterceptor(new HandlerInterceptor() {
//					@Override
//					public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//							throws Exception {
//						// TODO 计划把controller里那些日志转移到这里进行输出
//						LOG.info("preHandle");
//						return true;
//					}
//
//					@Override
//					public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//							ModelAndView modelAndView) throws Exception {
//						LOG.info("postHandle");
//					}
//
//					@Override
//					public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
//							Object handler, Exception ex) throws Exception {
//						LOG.info("afterCompletion");
//					}
//				}).addPathPatterns("/**");
//			}
//		};
//	}
	
	
}
