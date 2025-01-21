package com.cascinacaccia.config;

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

import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.UserDAO;
import com.cascinacaccia.services.UserService;

import jakarta.servlet.http.Cookie;

/*
 * SecurityConfig is a configuration class for Spring Security, enabling various security features 
 * and customizing login, session management, and authorization mechanisms.
 * 
 * This class defines beans and configurations for:
 * - Password encoding using BCrypt.
 * - HTTP security settings, including CSRF, authorization rules, form login, and logout.
 * - Session management, including session expiry handling and restricting concurrent sessions.
 */
@Configuration
@EnableScheduling
public class SecurityConfig {
	
	@Autowired
	UserDAO userDAO;
	@Lazy
	@Autowired
	private UserService userService;
	
	/*
     * Defines a BCrypt password encoder for securely hashing passwords before storing them in the database.
     * 
     * @return A BCryptPasswordEncoder bean for password hashing.
     */
	@Bean
	//hash passwords before storing them in the database
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	/*
     * Configures HTTP security for the application, including:
     * - Disabling CSRF protection (not recommended for production environments).
     * - Defining which URL paths are publicly accessible and which require authentication.
     * - Customizing login behavior, including a success handler that stores user information in session attributes.
     * - Configuring logout behavior, including invalidating the session and deleting cookies.
     * - Setting up session management, including handling session expiration, session fixation, and concurrent sessions.
     * 
     * @param http The HttpSecurity object used to configure the security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If there is an error in setting up HTTP security.
     */
    @Bean
    //configures HTTP security settings for the application
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/set-user-offline", "/struttura", "/submit-booking", "/prenota", "/attivita", "/login", "/error", "/", "/submit-form", "/resetPassword/**", "/resetPassword", "/forgotPassword", "/styles/**", "/scripts/**", "/img/**", "/translation/**").permitAll()
                
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .successHandler((request, response, authentication) -> {
                    
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String email = userDetails.getUsername();

                    //create a cookie with the user's email
                    Cookie emailCookie = new Cookie("userEmail", email);
                    emailCookie.setHttpOnly(false);
                    emailCookie.setSecure(false);
                    emailCookie.setPath("/");
                    emailCookie.setMaxAge(7 * 24 * 60 * 60);
                    
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

                            //update user state to "OFFLINE"
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
    
    /*
     * Defines a SessionRegistry bean used to keep track of active sessions.
     * 
     * @return A SessionRegistryImpl instance for managing user sessions.
     */
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

