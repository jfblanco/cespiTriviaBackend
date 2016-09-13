package ar.com.boemiz.config;

import ar.com.boemiz.security.*;
import ar.com.boemiz.security.xauth.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;


import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/scripts/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/assets/**")
            .antMatchers("/swagger-ui/**")
            .antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
        .and()
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
//            .antMatchers("/api/_search/meetings/**").permitAll()
//            .antMatchers("/api/_search/meetings").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/nivels").permitAll()
            .antMatchers("/api/nivelesHabilitados").permitAll()
            .antMatchers("/api/partidas").permitAll()
            .antMatchers("/api/preguntas").permitAll()
            .antMatchers("/api/dificultadsHabilitadas").permitAll()
            .antMatchers("/api/categorias").permitAll()
            .antMatchers("/api/categoriasHabilitadas").permitAll()
            .antMatchers("/api/imagen").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/account/reset_password/init").permitAll()
            .antMatchers("/api/account/reset_password/finish").permitAll()
            .antMatchers("/api/**").authenticated()
            //            .antMatchers("/api/logs/**").permitAll()
//            .antMatchers("/api/**").permitAll()                
            //.antMatchers(org.springframework.http.HttpMethod.OPTIONS, "/api/**").permitAll()
            .antMatchers("/websocket/tracker").permitAll()
            .antMatchers("/websocket/**").permitAll()
            .antMatchers("/metrics/**").permitAll()
            .antMatchers("/health/**").permitAll()
            .antMatchers("/trace/**").permitAll()
            .antMatchers("/dump/**").permitAll()
            .antMatchers("/shutdown/**").permitAll()
            .antMatchers("/beans/**").permitAll()
            .antMatchers("/configprops/**").permitAll()
            .antMatchers("/info/**").permitAll()
            .antMatchers("/autoconfig/**").permitAll()
            .antMatchers("/env/**").permitAll()
            .antMatchers("/trace/**").permitAll()
            .antMatchers("/api-docs/**").permitAll()
            .antMatchers("/protected/**").permitAll()
        .and()
            .apply(securityConfigurerAdapter());

    }

    private XAuthTokenConfigurer securityConfigurerAdapter() {
      return new XAuthTokenConfigurer(userDetailsService, tokenProvider);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
