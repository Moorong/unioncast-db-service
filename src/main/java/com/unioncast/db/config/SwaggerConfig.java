package com.unioncast.db.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Predicate;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * SwaggerConfig
 */
@Configuration
public class SwaggerConfig {

//	@Value("${server.contextPath}")
//	private String pathMapping;

	private ApiInfo initApiInfo() {
		ApiInfo apiInfo = new ApiInfo("广视数据存储项目 Platform API", // 大标题
				initContextInfo(), // 简单的描述
				"1.0.0", // 版本
				"服务条款", "后台开发团队（琚超超，刘蓉，张亚磊，张哲【排名不分先后，按字母顺序来】）", // 作者
				"The Apache License, Version 2.0", // 链接显示文字
				"http://www.baidu.com"// 网站链接
		);
		return apiInfo;
	}

	private String initContextInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("REST API 设计在细节上有很多自己独特的需要注意的技巧，并且对开发人员在构架设计能力上比传统 API 有着更高的要求。").append("<br/>")
				.append("以下是本项目的API文档");
		return sb.toString();
	}

	@Bean
	public Docket restfulApi() {
//		System.out.println("http://localhost:9330" + pathMapping + "/swagger-ui.html");
		return new Docket(DocumentationType.SWAGGER_2).groupName("RestfulApi")
				// .genericModelSubstitutes(DeferredResult.class)
				.genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(true).forCodeGeneration(false)
//				.pathMapping(pathMapping) // base，最终调用接口后会和paths拼接在一起
				.select().paths(doFilteringRules()).build().apiInfo(initApiInfo());
		// .select().paths(Predicates.not(PathSelectors.regex("/error.*"))).build().apiInfo(initApiInfo());

	}

	/**
	 * 设置过滤规则 这里的过滤规则支持正则匹配
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Predicate<String> doFilteringRules() {
		// return Predicates.not(PathSelectors.regex("/error.*"));
		// return or(regex("/hello.*"), regex("/rest/adxSspFinanceManagement.*"));//success
		return or(regex("/rest.*"));
	}
}