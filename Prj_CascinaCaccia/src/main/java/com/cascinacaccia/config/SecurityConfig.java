package com.cascinacaccia.config;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cascinacaccia.entities.User;
import com.cascinacaccia.repos.UserDAO;

@Configuration
public class SecurityConfig {
	
	@Autowired
	UserDAO userDAO;
	
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
                .requestMatchers("/login", "/").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .successHandler(customAuthenticationSuccessHandler())
                .defaultSuccessUrl("/profile", true) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)  //invalidate the HTTP session
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

        .build();
    }
    
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername(); //email
            Optional<User> optionalUser = userDAO.findByEmail(email);
            
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String fullName = user.getName();
                String userId = user.getId();
                List<String> role = authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                userDAO.save(user);

                //set attributes in session
                request.getSession().setAttribute("name", fullName);
                request.getSession().setAttribute("userId", userId);
                request.getSession().setAttribute("role", role);  //set role in session
                System.out.println("Full Name set in session: " + fullName);
            }
            
            response.sendRedirect("/");
        };
    }
}

