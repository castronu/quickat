package org.quickat.config;

import com.auth0.spring.security.auth0.Auth0AuthenticationProvider;
import org.quickat.web.filter.Auth0AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;


/**
 * @author Christophe Pollet
 *         source: https://gist.github.com/ethankhall/c9633b9319a866d79734
 */
@Configuration
@ComponentScan("com.auth0")
@EnableWebSecurity
public class Auth0Config extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment env;

    @Bean(name = "auth0Filter")
    public Auth0AuthenticationFilter auth0AuthenticationFilter() {
        return new Auth0AuthenticationFilter();
    }

    @Bean(name = "auth0AuthenticationProvider")
    public Auth0AuthenticationProvider auth0AuthenticationProvider() {
        Auth0AuthenticationProvider provider = new Auth0AuthenticationProvider();
        provider.setClientSecret(env.getRequiredProperty("auth0.clientSecret"));
        provider.setClientId(env.getRequiredProperty("auth0.clientId"));
        provider.setSecuredRoute(env.getRequiredProperty("auth0.securedRoute"));
        return provider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(auth0AuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(auth0AuthenticationFilter(), SecurityContextPersistenceFilter.class)
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers(env.getRequiredProperty("auth0.securedRoute")).authenticated()
                .antMatchers(HttpMethod.POST, "/quickies/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/quickies/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/quickies/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/users/**").authenticated();
    }
}
