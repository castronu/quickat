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
    public User getLoggedUser() {
        return usersRepository.findByAuthId(Auth0Helper.getAuthId());
    }
}
