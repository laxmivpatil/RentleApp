package com.techverse.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.api.client.util.Value;

import org.springframework.security.config.http.SessionCreationPolicy;



@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

	
	 @Autowired
	    private PermitAllPathsProperties permitAllPathsProperties;
	 
 
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and()
	            .authorizeRequests(authorize -> authorize
	                .antMatchers("/api/**").authenticated()
	                .antMatchers(permitAllPathsProperties.getPermitAllPaths().toArray(new String[0])).permitAll() // Public APIs
	                .anyRequest().permitAll())
	           
	            //.addFilterBefore(new IpWhitelistFilter(), UsernamePasswordAuthenticationFilter.class) // Add IP whitelisting filter
	             .addFilterBefore(new JwtValidator(), UsernamePasswordAuthenticationFilter.class)
	            .csrf().disable()
	            .cors().configurationSource(request -> {
	                CorsConfiguration corsConfig = new CorsConfiguration();
	                corsConfig.setAllowedOrigins(Arrays.asList(
	                    "http://localhost:3000",
	                    "http://localhost:4200"
	                ));
	                corsConfig.setAllowedMethods(Collections.singletonList("*"));
	                corsConfig.setAllowCredentials(true);
	                corsConfig.setAllowedHeaders(Collections.singletonList("*"));
	                corsConfig.setExposedHeaders(Arrays.asList("Authorization"));
	                corsConfig.setMaxAge(3600L);
	                return corsConfig;
	            })
	            .and()
	            .httpBasic()
	            .and()
	            .formLogin();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}


