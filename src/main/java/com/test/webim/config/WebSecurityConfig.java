package com.test.webim.config;

import com.test.webim.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsServiceImpl userService;



    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .antMatcher("/")
                .authorizeRequests()
                .antMatchers("/index","/webjars/**", "**/callback/**","/login**","/error**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error").successHandler(getSuccessAuthenticationHandler())
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .rememberMe()
                .and()
                .addFilter(getPreAuthenticationFilter());

    }


    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userService)
                .and()
                .authenticationProvider(getVkAuthenticationProvider());
    }

    @Bean
    public PreAuthenticationFilter getPreAuthenticationFilter() {
        return new PreAuthenticationFilter();
    }

    @Bean
    public VkAuthenticationProvider getVkAuthenticationProvider() {
        return new VkAuthenticationProvider();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SuccessAuthenticationHandler getSuccessAuthenticationHandler() {
        return new SuccessAuthenticationHandler();
    }


}

