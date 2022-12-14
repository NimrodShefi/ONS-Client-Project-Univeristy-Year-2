package ons.group8.config;

import ons.group8.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private MyUserDetailsService myuserDetailsService;
    private LoginFailureHandler loginFailureHandler;
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public WebSecurityConfig(MyUserDetailsService myUserDetailsService, LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler) {
        this.myuserDetailsService = myUserDetailsService;
        this.loginFailureHandler = loginFailureHandler;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http


                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/pictures/**").permitAll()
                .antMatchers("/sign-up/**", "/login**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .defaultSuccessUrl("/").permitAll()
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403-access-denied");

        http.httpBasic().disable();
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.headers().contentSecurityPolicy(csp -> csp.policyDirectives(
                "default-src 'self';" +
                "script-src 'self' https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js;" +
                "style-src 'self' https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css;" +
                "font-src 'self' https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/fonts/;"
        ));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myuserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
