package com.prashant.BlogApp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.prashant.BlogApp.security.CustomUserDetailService;
import com.prashant.BlogApp.security.JwtAuthenticationEntryPoint;
import com.prashant.BlogApp.security.JwtAuthenticationFilter;


// import com.blogapp.security.CustomUserDeatilService;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

	public static final String[] PUBLIC_URLS = {
		"/api/v1/auth/**",
		"/v3/api-docs",
		"/v2/api-docs",
		"/swagger-resources/**",
		"/swagger-ui/**",
		"/webjars/**",

	};

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.authorizeHttpRequests()
		.antMatchers(PUBLIC_URLS).permitAll()
		.antMatchers(HttpMethod.GET).permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
		
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		


		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customUserDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

   	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}


// 	//  @Bean
// 	// @Override
// 	// public AuthenticationManager authenticationManagerBean() throws Exception {
// 	// 	return super.authenticationManagerBean();
// 	// }
	
}




// @SuppressWarnings("deprecation")
// @Configuration
// @EnableWebSecurity
// @EnableWebMvc
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
// 	// public static final String[] PUBLIC_URLS= {
// 	// 	"/api/v1/auth/**",
// 	// 	"/v3/api-docs",
// 	// 	"/v2/api-docs",
// 	// 	"/swagger-resources/**",
// 	// 	"/swagger-uri/**",
// 	// 	"/webjars/**"
		
		
// 	// };
	
// 	@Autowired
// 	private CustomUserDetailService customUserDetailService;
	
// 	@Autowired
// 	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
// 	@Autowired
// 	private JwtAuthenticationFilter jwtAuthenticationFilter;

// 	@Override
// 	protected void configure(HttpSecurity http) throws Exception {
// 			http
// 		.csrf()
// 		.disable()
// 		.authorizeHttpRequests()
// 		.antMatchers("/api/v1/auth/**").permitAll()
// 		.antMatchers(HttpMethod.GET).permitAll()
// 		.anyRequest()
// 		.authenticated()
// 		.and()
// 		.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
// 		.and()
// 		.sessionManagement()
// 		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
// 		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		
// 	}

// 	@Override
// 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
// 		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
		
// 	}
// 	@Bean
// 	public PasswordEncoder passwordEncoder(){
// 		return new BCryptPasswordEncoder();
// 	}
//     @Bean
// 	@Override
// 	public AuthenticationManager authenticationManagerBean() throws Exception {
// 		return super.authenticationManagerBean();
// 	}
// }