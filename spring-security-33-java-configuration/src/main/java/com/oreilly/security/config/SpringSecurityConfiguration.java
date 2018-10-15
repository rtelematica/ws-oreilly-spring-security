package com.oreilly.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@SuppressWarnings("unused")
	@Autowired
	private DataSource datasource;

	@Autowired
	private UserDetailsService customUserDetailService;

	@Override // configure authentication manager
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		/*auth
			.inMemoryAuthentication()
				.withUser("ivgarcia")
				.password("password")
				.roles("USER");*/

		/*auth
			.jdbcAuthentication()
				.dataSource(datasource);*/
		auth
			.userDetailsService(customUserDetailService);
	}

	@Override // configure http object similar to <security:http />
	protected void configure(HttpSecurity http) throws Exception {

		http
			.authorizeRequests()
				.antMatchers("/appointments/**")
					.access("hasRole('USER') or hasRole('ADMIN')")
				.antMatchers("/schedule/**")
					.access("hasAuthority('ROLE_ADMIN') and principal.username == 'ivgarcia'")
				.antMatchers("/**").permitAll()
			.and()
				.formLogin()
					.loginPage("/login") // GET
					.loginProcessingUrl("/login") //POST
					.usernameParameter("custom_username")
					.passwordParameter("custom_password")
					.defaultSuccessUrl("/appointments", true)
					.failureUrl("/login?error=1")
			.and()
				.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login?logout=1")
			.and()
				.rememberMe()
					.key("rememberMe");
	}

	@Bean
	public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() {
		return new DefaultWebSecurityExpressionHandler();
	}
}
