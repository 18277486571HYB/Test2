package com.hyb.test2.config;


import com.hyb.test2.handler.CustomLogoutSuccessHandler;
import com.hyb.test2.handler.CustomizeAuthenticationEntryPoint;
import com.hyb.test2.handler.FailHandler;
import com.hyb.test2.handler.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    SuccessHandler successHandler;

    @Autowired
    FailHandler failHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

//    @Autowired
//    CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;

//    @Autowired
//    MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//                .usernameParameter("mobile")
//                .passwordParameter("password")
                .loginPage("/unLogin")
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureHandler(failHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(customLogoutSuccessHandler)
                // 无效会话
                .invalidateHttpSession(true)
                // 清除身份验证
                .clearAuthentication(true)
                .and().csrf().disable();
                //异常处理(权限拒绝、登录失效等)
//                .exceptionHandling()
//                .authenticationEntryPoint(customizeAuthenticationEntryPoint);
        http.authorizeRequests()
                .antMatchers(
                        "/oauth/**",
                        "/login/**",
                        "/unLogin",
                        "/logout/**",
                        "/uac/oauth/token",
                        "http://localhost:3000/login",
                        "http://localhost:8085/uac/login"

                ).permitAll().anyRequest().authenticated();

        //http.addFilterAt(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

//    @Bean
//    MyUsernamePasswordAuthenticationFilter myAuthenticationFilter() throws Exception {
//        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
//        filter.setAuthenticationSuccessHandler(successHandler);
//        filter.setAuthenticationFailureHandler(failHandler);
//        return filter;
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}


