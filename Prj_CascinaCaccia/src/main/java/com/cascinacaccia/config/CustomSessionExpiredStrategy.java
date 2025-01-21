package com.cascinacaccia.config;

import java.io.IOException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy 
 * to define the behavior when a user's session expires.
 * 
 * This implementation listens for a session expiration event and redirects the user 
 * to the login page with a custom error message indicating that the session has expired.
 */
public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy {
    
	@Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) 
            throws IOException, ServletException {
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();

        // Redirect to /login with a custom error message
        response.sendRedirect("/login?errorSession=sessionExpired");
    }
}
