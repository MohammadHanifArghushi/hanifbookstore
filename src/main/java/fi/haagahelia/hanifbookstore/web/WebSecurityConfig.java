package fi.haagahelia.hanifbookstore.web;

import fi.haagahelia.hanifbookstore.service.UserDetailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig {

        @Autowired
        private UserDetailServiceImpl userDetailsService;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

                authProvider.setUserDetailsService(userDetailsService);

                authProvider.setPasswordEncoder(passwordEncoder);

                return authProvider;
        }

        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(authorize -> authorize

                                                .requestMatchers("/css/**", "/h2-console/**").permitAll()

                                                .anyRequest().authenticated())
                                .csrf(csrf -> csrf

                                                .ignoringRequestMatchers("/h2-console/**"))
                                .headers(headers -> headers

                                                .frameOptions(frameOptions -> frameOptions.disable()))
                                .formLogin(formLogin -> formLogin

                                                .loginPage("/login")
                                                .defaultSuccessUrl("/booklist", true)
                                                .permitAll())
                                .logout(logout -> logout

                                                .permitAll());
                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {

                PasswordEncoder encoder = passwordEncoder();

                UserDetails user = User.builder()
                                .username("user")
                                .password(encoder.encode("userpassword"))
                                .roles("USER")
                                .build();

                UserDetails admin = User.builder()
                                .username("admin")
                                .password(encoder.encode("adminpassword"))
                                .roles("USER", "ADMIN")
                                .build();

                return new InMemoryUserDetailsManager(user, admin);
        }

}
