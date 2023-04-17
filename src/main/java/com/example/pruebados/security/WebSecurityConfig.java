package com.example.pruebados.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig  {
    // AuthenticationManager as
    //Esto es la clase para hacerlo personalizado los filtros de Security de Spring por el protocolo http

    @Bean
        SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager ) throws Exception {


        return httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
   }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                            PasswordEncoder passwordEncoder) throws Exception{
    return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(UserDetailsService())
            .passwordEncoder(passwordEncoder())
            .and()
            .build();

}

    //esto es para hasear la contraseña
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
}

}
