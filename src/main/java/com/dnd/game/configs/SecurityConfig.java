package com.dnd.game.configs;

import com.dnd.game.entities.UserAccount;
import com.dnd.game.repositories.UserManagementRepo;
import com.dnd.game.repositories.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    @Bean
//    CommandLineRunner initUsers(UserManagementRepo userManagementRepo) {
//        return args -> {
//            userManagementRepo.save(new UserAccount("bob", "password", "y.smirnov01@gmail.com", new SimpleGrantedAuthority("ROLE_ADMIN")));
//            userManagementRepo.save(new UserAccount("jack", "password", "jack@gmail.com", new SimpleGrantedAuthority("ROLE_USER")));
//        };
//    }


//    @Bean
//    public OAuth2AuthorizedClientManager clientManager(ClientRegistrationRepository clientRegRepo,
//                                                       OAuth2AuthorizedClientRepository authClientRepo) {
//        OAuth2AuthorizedClientProvider clientProvider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .authorizationCode()
//                        .refreshToken()
//                        .clientCredentials()
//                        .password()
//                        .build();
//
//        DefaultOAuth2AuthorizedClientManager clientManager =
//                new DefaultOAuth2AuthorizedClientManager(clientRegRepo, authClientRepo);
//        clientManager.setAuthorizedClientProvider(clientProvider);
//
//        return clientManager;
//    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/fonts/**", "/images/**", "/css/**", "/js/**", "/about", "/all-songs", "/", "/songs/**",
                                        "/login", "/contact", "/error", "/favicon.ico", "/oauth2/**", "/register", "/tabs/**")
                                .permitAll()
                                .requestMatchers("/account", "/delete-song/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/delete-song/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/all-songs").authenticated()
                                .anyRequest().denyAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/account", true))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/account", true));

        return http.build();
    }

    @Bean
    UserDetailsService userService(UserRepo repo) {
        return username -> {
            UserAccount userAccount = repo.findByUsername(username);
            if (userAccount == null) {
                System.out.println("User with " + username + " not found");
            }
            return userAccount.asUser();
        };
    }


}
