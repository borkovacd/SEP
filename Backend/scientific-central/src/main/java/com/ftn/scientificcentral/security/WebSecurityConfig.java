package com.ftn.scientificcentral.security;

import com.ftn.scientificcentral.security.jwt.AuthEntryPointJwt;
import com.ftn.scientificcentral.security.jwt.AuthTokenFilter;
import com.ftn.scientificcentral.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // --> allows any Java application to work with Spring Security in the Spring framework
@EnableWebSecurity // --> allows Spring to find and automatically apply the class to the global Web Security
@EnableGlobalMethodSecurity( // -- > provides AOP security on methods. It enables @PreAuthorize, @PostAuthorize, it also supports JSR-250
        //securedEnabled = true,
        //jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // WebSecurityConfigurerAdapter -> This library designates this class as the security configuration under Spring Security.

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //In newer versions of Spring Boot this needs to be done, 'cause there is no more default implementation of AuthenticationManager
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // It tells Spring Security how we configure CORS and CSRF, when we want to require all users to be authenticated or not, which filter
    // (AuthTokenFilter) and when we want it to work
    // (filter before UsernamePasswordAuthenticationFilter), which Exception Handler is chosen (AuthEntryPointJwt).
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //telling spring security not to create a session
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/brands").permitAll()
                .antMatchers("/api/types").permitAll()
                .antMatchers("/api/bodyTypes").permitAll()
                .antMatchers("/api/fuelTypes").permitAll()
                .anyRequest().authenticated(); //ensures that any HTTP request that comes to the filter will be checked for authentication.

        //making sure that my filter is called before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
