//package com.unioncast.db.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.StandardPasswordEncoder;
//
///**
// * 禁用csrf（无奈之举）
// * @author juchaochao
// *
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	DataSource dataSource;
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		// super.configure(auth);//会导致Bad credentials Error
//		// auth.inMemoryAuthentication().withUser("juchaochao").password("password").roles("USER").and().withUser("admin")
//		// .password("password").roles("USER", "ADMIN");//内存用户存储
//		// auth.inMemoryAuthentication().withUser("juchaochao").password("password").authorities("ROLE_USER").and()
//		// .withUser("admin").password("password").authorities("ROLE_USER",
//		// "ROLE_ADMIN");//role()方法是authorities()方法的简写形式，会自动添加"ROLE_"前缀
//		auth.jdbcAuthentication().dataSource(dataSource)
//				.usersByUsernameQuery("select username,password,true from spitter where username = ?")
//				.authoritiesByUsernameQuery("select username,'ROLE_USER' from spitter where username = ?")
//				.passwordEncoder(new StandardPasswordEncoder("53cr3t"));// RDBMS(Relational
//		// Database
//		// Management
//		// System)
//	}
//
//	// public static void main(String[] args) {
//	// StandardPasswordEncoder encoder = new StandardPasswordEncoder("53cr3t");
//	// System.out.println(encoder.encode("password"));
//	// }
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// http.authorizeRequests().antMatchers("/*/**").authenticated().antMatchers(HttpMethod.POST,
//		// "/*/**")
//		// .authenticated().anyRequest().permitAll();
//		// http.authorizeRequests().antMatchers("/*/**").hasRole("USER").antMatchers(HttpMethod.POST,
//		// "/*/**")
//		// .hasRole("USER").anyRequest().permitAll().and().requiresChannel().antMatchers("/*/**").requiresSecure()
//		// .antMatchers("/").requiresInsecure();// https://127.0.0.1:8443/
//		// http://127.0.0.1:8080
//		// http.formLogin().and().authorizeRequests().antMatchers("/").authenticated().anyRequest().permitAll();//
//		// 启用默认的登录页面
//		// http.formLogin().loginPage("/login").and().authorizeRequests().antMatchers("/").authenticated().anyRequest()
//		// .permitAll();//使用自定义的登录页面
//		// http.formLogin().loginPage("/login").and().rememberMe().tokenValiditySeconds(2419200).and().httpBasic().and()
//		// .authorizeRequests().antMatchers("/").authenticated().anyRequest().permitAll();//2419200:两周内有效，通过在cookie中存储一个token
//
//		// http.formLogin().loginPage("/login").and().authorizeRequests().antMatchers("/").authenticated()
//		// .antMatchers(HttpMethod.POST,
//		// "/contact").authenticated().anyRequest().permitAll();//
//		// 2419200:两周内有效，通过在cookie中存储一个token
//
//		// http.formLogin().loginPage("/login").permitAll().and().authorizeRequests().antMatchers("/").authenticated()
//		// .anyRequest().permitAll();
//
//		// http.formLogin().loginPage("/login").permitAll().and().authorizeRequests()
//		// .antMatchers("/")
//		// .authenticated().anyRequest().permitAll().and().httpBasic();
//		// .and().csrf().disable();// 禁用CSRF
//
//		http.formLogin().loginPage("/login").permitAll().and().rememberMe().tokenValiditySeconds(2419200).and()
//				.authorizeRequests().antMatchers("/*.css").permitAll().antMatchers("/images/**").permitAll()
//				.antMatchers("/rest/**").permitAll().antMatchers("/restClient/**").permitAll().antMatchers("/**")
//				.authenticated().anyRequest().permitAll().and().httpBasic().and().csrf().disable();
//
//		// http.formLogin().loginPage("/login").permitAll().and().rememberMe().tokenValiditySeconds(2419200).and()
//		// .authorizeRequests().anyRequest().permitAll().and().httpBasic();
//	}
//
//}
