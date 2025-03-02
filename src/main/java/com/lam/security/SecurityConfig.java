package com.lam.security;

import com.alibaba.fastjson.JSON;
import com.lam.pojo.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.ExceptionHandlingDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.PrintWriter;
import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTFilterChainConfig jwtFilterChainConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // 确保禁用CSRF保护，如果不需要的话
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests//配置无需权限访问的白名单
                                .requestMatchers("/upload/**","/public/**", "/api/login", "/api/user/register", "/image/**", "/api/upload/**", "/api/product/page/**", "/ws/**", "/api/administrator/login").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("admin") //所有带admin前缀的都需要有admin角色
                                .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)// 如果不需要HTTP基本认证，可以将其替换为.disable()
                .formLogin(AbstractHttpConfigurer::disable) // 直接禁用表单登录
                .addFilterBefore(jwtFilterChainConfig, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(configurer ->
                        configurer.accessDeniedHandler(accessDeniedHandler()));

        return http.build();
    }
    //权限不足的异常处理 Denied（否定）
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonString = JSON.toJSONString(Result.error("权限不足，请联系管理员"));
//            Result.error("权限不足，请联系管理员");
            try (PrintWriter writer = response.getWriter()) {
                writer.write(jsonString);
                writer.flush();
            }
        };
    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User
//                .withUsername("user")
//                .password("password")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    //    @Bean
//    public AAUserDetailService customUserDetailsService() {
//        return new AAUserDetailService();
//    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //配置跨域信息
        return source;
    }



//    @Bean
//    @ConditionalOnMissingBean(UserDetailsService.class)
//    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        String generatedPassword ="";// ...;
//        return new InMemoryUserDetailsManager(User.withUsername("user")
//                .password(generatedPassword).roles("USER").build());
//    }

//    @Bean
//    @ConditionalOnMissingBean(AuthenticationEventPublisher.class)
//    DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) {
//        return new DefaultAuthenticationEventPublisher(delegate);
//    }
}