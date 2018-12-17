/* 
 * Author ::. Sivateja Kandula | www.java4s.com 
 *
 */

package com.cisco.exam.configs;


import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter{
	  
	  
	 /* @Autowired
	  private CustomAuthentiationProvider customAuthProvider;
	   
	
	// Authentication : User --> Roles
		protected void configure(AuthenticationManagerBuilder auth) throws Exception 
		{
			
			auth.authenticationProvider(customAuthProvider);
			 
		  }
			auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
					.withUser("user").password("pass").roles("USER")
					.and()
					.withUser("admin").password("pass").roles("USER", "ADMIN");
		}

		// Authorization : Role -> Access
		protected void configure(HttpSecurity http) throws Exception {
			   http.authorizeRequests().anyRequest().authenticated()
	            .and()
	            .httpBasic();
			   http.csrf().disable();
		}*/
	
	 @Autowired
	  private CustomAuthentiationProvider customAuthProvider;
	   
	
	// Authentication : User --> Roles
		protected void configure(AuthenticationManagerBuilder auth) throws Exception 
		{
			
			auth.authenticationProvider(customAuthProvider);
			 
		  }
			/*auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
					.withUser("user").password("pass").roles("USER")
					.and()
					.withUser("admin").password("pass").roles("USER", "ADMIN");
		}
*/
		// Authorization : Role -> Access
		protected void configure(HttpSecurity http) throws Exception {
			   http.authorizeRequests().anyRequest().authenticated()
	            .and()
	            .httpBasic();
			   http.csrf().disable();
			   http.cors().configurationSource(new CorsConfigurationSource() {

			        @Override
			        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
			            CorsConfiguration config = new CorsConfiguration();
			            config.setAllowedHeaders(Collections.singletonList("*"));
			            config.setAllowedMethods(Collections.singletonList("*"));
			            config.addAllowedOrigin("*");
			            config.setAllowCredentials(true);
			            return config;
			        }
			      });
		}
	
}

