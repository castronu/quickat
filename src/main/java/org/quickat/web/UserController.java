package org.quickat.web;

import org.quickat.da.Quickie;
import org.quickat.da.User;
import org.quickat.repository.QuickiesRepository;
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

    @Autowired
    public QuickiesRepository quickiesRepository;

    @RequestMapping
    public Iterable<User> getUsers() {
        return usersRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "id") Long id) {
        return usersRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public User getUsers(@RequestBody User user) {
        logger.info(user.toString());

        return usersRepository.save(user);
    }


    @RequestMapping(value = "/{speakerId}/quickies", method = RequestMethod.GET)
    public Iterable<Quickie> getQuickiesOfSpeaker(@PathVariable(value = "speakerId") Long speakerId) {
        logger.info("getQuickiesOfSpeaker with id {}", speakerId);
        return quickiesRepository.findBySpeakerId(speakerId);
    }

}
