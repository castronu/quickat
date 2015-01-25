package org.quickat.web.exception;

import org.quickat.web.dto.UserProfile;

/**
 * @author Christophe Pollet
 */
public class UserAlreadyExistsException extends RuntimeException {
    private UserProfile userProfile;

    public UserAlreadyExistsException(UserProfile userProfile) {
        super();
        this.userProfile = userProfile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
