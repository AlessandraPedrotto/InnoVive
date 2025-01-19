package com.cascinacaccia.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.services.UserService;

import jakarta.servlet.http.Cookie;

@Configuration
@EnableScheduling
public class SecurityConfig {
	
	@Autowired
	UserDAO userDAO;
	@Lazy
	@Autowired
	private UserService userService;
	
	@Bean
	//hash passwords before storing them in the database
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	
    @Bean
    //configures HTTP security settings for the application
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/set-user-offline", "/struttura", "/submit-booking", "/prenota", "/attivita", "/login", "/error", "/", "/informationForm", "/submit-form", "/generalForm", "/chatbot", "/bookingForm", "/resetPassword/**", "/resetPassword", "/forgotPassword", "/styles/**", "/scripts/**", "/img/**", "/translation/**").permitAll()
                
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .successHandler((request, response, authentication) -> {
                    
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String email = userDetails.getUsername();

                    // Create a cookie with the user's email
                    Cookie emailCookie = new Cookie("userEmail", email);
                    emailCookie.setHttpOnly(false);  // Allows JavaScript to access the cookie
                    emailCookie.setSecure(false);    // Set to true in production if using HTTPS
                    emailCookie.setPath("/");        // Make it available across the site
                    emailCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                    
                    Optional<User> optionalUser = userDAO.findByEmail(email);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();

                        String fullName = user.getName();
                        String userId = user.getId();
                        List<String> roles = authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList());

                        //set session attributes
                        request.getSession().setAttribute("name", fullName);
                        request.getSession().setAttribute("userId", userId);
                        request.getSession().setAttribute("role", roles);
                        request.getSession().setAttribute("email", email);
                    }

                    //redirect to the profile page after login
                    response.sendRedirect("/profile");
                })
                .defaultSuccessUrl("/profile", true) 
                .permitAll()
            )
            .logout(logout -> logout
            		.logoutSuccessHandler((request, response, authentication) -> {
                        if (authentication != null) {
                            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                            String email = userDetails.getUsername();

                            // Update user state to "OFFLINE"
                            userService.markInactiveUsersOffline();
                        }

                        response.sendRedirect("/");
                    })
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                )
                .sessionManagement(session -> session
                    .invalidSessionUrl("/login?errorSession=sessionExpired")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    .sessionRegistry(sessionRegistry())
                    .expiredSessionStrategy(new CustomSessionExpiredStrategy())
                    .and()
                    .sessionFixation().migrateSession()
                )

        .build();
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    /*
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername(); //email
            
            userService.updateUserStateAndLastAccess(email, "ONLINE");
            
            Optional<User> optionalUser = userDAO.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                String fullName = user.getName();
                String userId = user.getId();
                List<String> role = authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                
                //set attributes in session
                request.getSession().setAttribute("name", fullName);
                request.getSession().setAttribute("userId", userId);
                request.getSession().setAttribute("role", role);  //set role in session
                
                System.out.println("User logged in: " + email + ", State: " + user.getState());
            }
            
            response.sendRedirect("/profile");
        };
    }
    */
}

