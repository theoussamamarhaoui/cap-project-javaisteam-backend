package com.cap.backendcapproject.security;

import com.cap.backendcapproject.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AccountService accountService;

    WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsServiceImpl userDetailsService){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.POST,
                SecurityContants.SIGN_IN_URL, "/user/**", "/register/**", "/login/**").permitAll();

        http.authorizeRequests().antMatchers(HttpMethod.PUT,
                SecurityContants.SIGN_IN_URL, "/users/**", "/admins/**", "/candidates/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(getAuthenticationFilter());
        http.addFilterBefore(new AuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

    }

    protected AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), accountService);
        filter.setFilterProcessesUrl("/login");
        return filter;
    }
}
