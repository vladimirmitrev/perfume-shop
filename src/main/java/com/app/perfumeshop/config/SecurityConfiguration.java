package com.app.perfumeshop.config;

import com.app.perfumeshop.model.enums.UserRoleEnum;
import com.app.perfumeshop.repository.UserRepository;
import com.app.perfumeshop.service.PerfumeShopUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final String rememberMeKey;
    private final PerfumeShopUserDetailsService perfumeShopUserDetailsService;

    public SecurityConfiguration(@Value("${perfumeshop.remember.me.key}")
                                 String rememberMeKey, PerfumeShopUserDetailsService perfumeShopUserDetailsService) {
        this.rememberMeKey = rememberMeKey;
        this.perfumeShopUserDetailsService = perfumeShopUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                // Define which urls are visible by which users
                authorizeRequests -> authorizeRequests
                        // All static resources which are situated in js, images, css are available for anyone
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(
                                "/",
                                "/users/login",
                                "/users/register",
                                "/users/login-error",
                                "/about-us",
                                "/contact-us",
                                "/maintenance",
                                "/error",
                                "/products/all",
                                "/products/details/**",
                                "/brands",
                                "/api/all-brands",
                                "/brand-products/**",
                                "/search").permitAll()
                        .requestMatchers("/products/add", "/products/edit/**",
                                "/products/delete/**", "/orders-all", "/cancel-order",
                                "/cancel-customer-order", "/users/details-profile/**").hasAnyRole(UserRoleEnum.ADMIN.name(), UserRoleEnum.EMPLOYEE.name())
                        .requestMatchers("/users/all", "/users/change-role/**").hasAnyRole(UserRoleEnum.ADMIN.name())
//                        .requestMatchers("/my-orders").hasRole(UserRoleEnum.USER.name())
                        // all other requests are authenticated.
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> {
                    formLogin
                            // redirect here when we access something which is not allowed.
                            // also this is the page where we perform login.
                            .loginPage("/users/login")
                            // The names of the input fields (in our case in login.html)
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/", true)
                            .failureForwardUrl("/users/login-error")
                            .permitAll();
                }
        ).logout(
                logout -> {
                    logout
                            // the URL where we should POST something in order to perform the logout
                            .logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        ).rememberMe(
                rememberMe -> {
                    rememberMe
                            .key(rememberMeKey)
                            .rememberMeParameter("rememberme")
                            .rememberMeCookieName("rememberme")
                            .userDetailsService(perfumeShopUserDetailsService);
                }
        )
                .build();
    }

    // If you use this like a @Bean remove @Service annotation from
    // PerfumeShopUserDetailsService.java and remove rememberMe
//    @Bean
//    public PerfumeShopUserDetailsService userDetailsService(UserRepository userRepository) {
//        return new PerfumeShopUserDetailsService(userRepository);
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
