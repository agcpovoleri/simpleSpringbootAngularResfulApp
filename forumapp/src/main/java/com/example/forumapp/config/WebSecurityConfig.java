package com.example.forumapp.config;

import com.example.forumapp.config.filter.StatelessAuthenticationFilter;
import com.example.forumapp.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Augusto on 06/10/2015.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginService service;

    @Autowired
    private MessageSource messages;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
//
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/logout").authenticated()
                .antMatchers("/api/auth/refresh").authenticated()
                .antMatchers("/api/user/*").authenticated()
                .antMatchers("/api/user").permitAll()
                .antMatchers("/api/posts").authenticated()
//                .antMatchers("/swagger-resources").permitAll()
//                .antMatchers("/swagger-resources/configuration/*").permitAll()
//                .antMatchers("/v2/api-docs").permitAll()

                //.anyRequest().permitAll()
                //.anyRequest().authenticated()
                .and()

                .addFilterAfter(new StatelessAuthenticationFilter(service), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedEntryPoint());
    }

    private AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response,  authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, messages.getMessage("unauthorized.message", null, LocaleContextHolder.getLocale()));
        };
    }

}
