package org.quickat.service;

import org.quickat.da.User;
import org.quickat.repository.UsersRepository;
import org.quickat.web.Auth0Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Christophe Pollet
 */
@Service
public class DefaultUserService implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    // FIXME that's really ugly :P
    public User getLoggedUser() {
        try {
            User user = usersRepository.findByAuthId(Auth0Helper.getAuthId());

            if (user == null) {
                throw new Exception();
            }

            return user;
        } catch (Exception e) {
            User user = new User();
            user.setId(-1l);
            return user;
        }
    }
}
