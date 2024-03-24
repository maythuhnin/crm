package com.eniac.projects.bet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.eniac.projects.bet.security.CustomAuthenticationProvider;

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

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin().loginPage("/login")
						.usernameParameter("userId")
						.passwordParameter("password")
						.permitAll();
		
		http.formLogin().successHandler(new CustomAuthenticationSuccessHandler());
		
		http.formLogin().failureHandler(new CustomAuthenticationFailureHandler());
		
		http.logout().logoutSuccessUrl("/login?logout");
		
		http.exceptionHandling().accessDeniedPage("/accessDenied");
		
		http.sessionManagement().maximumSessions(1)
								.expiredUrl("/login?expired");
		
		http.authorizeRequests().antMatchers("/dashboard").hasAnyAuthority("ROLE_SALES", "ROLE_ADMIN", "ROLE_WAREHOUSE")
								.antMatchers("/category").hasAnyAuthority("ROLE_SALES", "ROLE_ADMIN", "ROLE_WAREHOUSE")
								.antMatchers("/distributor").hasAnyAuthority("ROLE_SALES", "ROLE_ADMIN", "ROLE_WAREHOUSE")
								.antMatchers("/").permitAll()
								.antMatchers("/resources/**").permitAll()
								.antMatchers("/login").permitAll()
								.antMatchers("/**").authenticated();
		
		http.csrf().disable();
	
	}
}
