package com.razi.ubbtt.handlers;

import com.razi.ubbtt.domain.User;
import com.razi.ubbtt.repositories.RoleRepository;
import com.razi.ubbtt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * If we're not in the DB, log in using the SCS server
 */

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    UserService userService;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        Store store;
        try
        {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("scs.ubbcluj.ro", 993, username, password);

            // We've successfully connected ourselves to the DB
            // Now let's save the user to the DB, allowing faster logins
            User user = new User();
            user.setUsername(username);
            user.setPassword(password); // unencrypted
            userService.saveUser(user);

            // Login
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (auth.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            redirectStrategy.sendRedirect(request, response, "/admin/home");
        }
        catch (javax.mail.MessagingException e) {
            System.out.println("Could not login");
            //e.printStackTrace();
            redirectStrategy.sendRedirect(request, response, "/login?error=true");
        }
    }
}