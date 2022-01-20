package com.herokuapp.agendatoday.Security;

import com.herokuapp.agendatoday.dao.CustomUserDetailsService;
import com.herokuapp.agendatoday.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    private UserRepository userRepo;

    private CustomUserDetailsService customUserDetailsService;

    public SecurityConfiguration(UserRepository userRepo, CustomUserDetailsService customUserDetailsService) {
        this.userRepo = userRepo;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService(userRepo);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    //authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    //authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/list/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                //.loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/list")
                .failureUrl("/badlogin")
                .and()
                .logout()
                //.logoutUrl("process_logout")
                .logoutSuccessUrl("/");
    }

}
