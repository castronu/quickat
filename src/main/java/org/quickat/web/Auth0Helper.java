package org.quickat.web;

import com.auth0.spring.security.auth0.Auth0JWTToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

/**
 * @author Christophe Pollet
 */
public class Auth0Helper {
    public static Auth0JWTToken getToken() {
        return (Auth0JWTToken) SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getAuthId() {
        return ((Map<String, String>) getToken().getDetails()).get("sub");
    }
}
