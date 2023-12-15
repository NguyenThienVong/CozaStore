package com.poly.config;

//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.poly.entity.Accounts;
import com.poly.service.AccountService;
import com.poly.utils.ParamService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Mã hóa mật khẩu
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	AccountService accountService;
	
	@Autowired
	ParamService paramService;


	// Quản lý người dữ liệu người sử dụng
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Accounts accounts = accountService.findById(username);
				String password = accounts.getPassword();
				String[] roles = accounts.getAuthorities().stream().map(er -> er.getRoles().getId())
						.collect(Collectors.toList()).toArray(new String[0]);														
				return User.withUsername(username).password(password).roles(roles).build();
			} catch (Exception e) {
				throw new UsernameNotFoundException(username + "not found !");
			}
		});
	}

	// Phân quyền sử dụng đăng nhập
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		
		// CSRF,CORS
		http.csrf().disable().cors().disable();
		// Phân quyền sử dụng
		http.authorizeRequests().antMatchers("/order/**")
			.authenticated().antMatchers("/admin/**")
			.hasAnyRole("STAFF", "USER").antMatchers("/rest/authorities").hasRole("STAFF").anyRequest().permitAll();

		// Điều khiển lỗi truy cập không đúng vai trò
		http.exceptionHandling().accessDeniedPage("/auth/access/denied");

		// Giao diện đăng nhập
		http.formLogin().loginPage("/security/login/form")
						.loginProcessingUrl("/security/login")
						.defaultSuccessUrl("/security/login/success", false)
						.failureUrl("/security/login/error")
						;

		http.rememberMe().tokenValiditySeconds(86400);

		http.exceptionHandling().accessDeniedPage("/security/unauthoried");

		// Đăng Xuất
		http.logout().logoutUrl("/security/logoff")
					.logoutSuccessUrl("/security/logoff/success");

		// OAuth2 - Đăng nhập từ mạng xã hội
		http.oauth2Login().defaultSuccessUrl("/oauth2/login/success", true)
						.failureUrl("/security/login/error")
						.authorizationEndpoint().baseUri("/oauth2/authorization");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

}
