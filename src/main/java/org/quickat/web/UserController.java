package org.quickat.web;

import org.quickat.da.User;
import org.quickat.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by aposcia on 14.01.15.
 */
@RestController
@RequestMapping("users")
public class UserController {

    final static Logger logger = LoggerFactory.getLogger(QuickieController.class);

    @Autowired
    public UsersRepository usersRepository;

    @RequestMapping
    public Iterable<User> getUsers() {
        return usersRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getQuickie(@PathVariable(value = "id") Long id) {
        return usersRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public User createQuickie(@RequestBody User user) {
        logger.info(user.toString());

        return usersRepository.save(user);
    }

}
