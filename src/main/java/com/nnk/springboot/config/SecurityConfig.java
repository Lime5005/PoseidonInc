package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(s);
                if (user == null) {
                    throw new UsernameNotFoundException("User not found with username: " + s);
                }

                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signup","/css/**","/js/**").permitAll()
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/app/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.formLogin()
                .defaultSuccessUrl("/bidList/list",true);
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }
}
