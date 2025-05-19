package com.eniac.projects.crm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.eniac.projects.crm.security.CustomAuthenticationProvider;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider authProvider;

	@Autowired
	public void configureGlobalSecuity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin().loginPage("/login").permitAll(); 

		http.formLogin().successHandler(new CustomAuthenticationSuccessHandler());

		http.formLogin().failureHandler(new CustomAuthenticationFailureHandler());

		http.logout().logoutSuccessUrl("/login?logout");

		http.exceptionHandling().accessDeniedPage("/accessDenied");

		http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired");

		http.authorizeRequests().antMatchers("/lead").hasAnyAuthority("ROLE_SALES", "ROLE_ADMIN", "ROLE_WAREHOUSE")
				.antMatchers("/category").hasAnyAuthority("ROLE_SALES", "ROLE_ADMIN", "ROLE_WAREHOUSE")
				.antMatchers("/distributor").hasAnyAuthority("ROLE_SALES", "ROLE_ADMIN", "ROLE_WAREHOUSE")
				.antMatchers("/").permitAll().antMatchers("/resources/**").permitAll()
				.antMatchers("/login").permitAll().antMatchers("/mobile/login").permitAll()
				.antMatchers("/**").authenticated();

		http.csrf().disable();

	}
}
