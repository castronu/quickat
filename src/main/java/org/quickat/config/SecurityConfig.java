/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.quickat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.openid.OpenIDAuthenticationToken;


/**
 * Security Configuration.
 *
 * @author Craig Walls
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationContext context;

    //https://github.com/rwinch/spring-boot-openid/blob/master/src/main/java/demo/MyController.java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .openidLogin()
                .loginPage("/login")
                .permitAll()
                .authenticationUserDetailsService(new CustomUserDetailsService())
                .attributeExchange("https://www.google.com/.*")
                .attribute("email")
                .type("http://axschema.org/contact/email")
                .required(true)
                .and()
                .attribute("firstname")
                .type("http://axschema.org/namePerson/first")
                .required(true)
                .and()
                .attribute("lastname")
                .type("http://axschema.org/namePerson/last")
                .required(true)
                .and()
                .and()
                .attributeExchange(".*yahoo.com.*")
                .attribute("email")
                .type("http://axschema.org/contact/email")
                .required(true)
                .and()
                .attribute("fullname")
                .type("http://axschema.org/namePerson")
                .required(true)
                .and()
                .and()
                .attributeExchange(".*myopenid.com.*")
                .attribute("email")
                .type("http://schema.openid.net/contact/email")
                .required(true)
                .and()
                .attribute("fullname")
                .type("http://schema.openid.net/namePerson")
                .required(true);

    }


    class CustomUserDetailsService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

        @Override
        public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
            return new User(token.getName(), "", AuthorityUtils.createAuthorityList("ROLE_USER"));
        }
    }


}
