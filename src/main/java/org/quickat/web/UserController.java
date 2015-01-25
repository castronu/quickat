package org.quickat.web;

import org.quickat.da.Quickie;
import org.quickat.da.User;
import org.quickat.repository.QuickiesRepository;
import org.quickat.repository.UsersRepository;
import org.quickat.service.UserService;
import org.quickat.web.dto.UserProfile;
import org.quickat.web.exception.NotAllowedException;
import org.quickat.web.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

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

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<UserProfile> getUsers() {
        List<UserProfile> userProfiles = new LinkedList<>();

        for (User user : usersRepository.findAll()) {
            userProfiles.add(buildUserProfile(user));
        }

        return userProfiles;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public UserProfile getUser() {
        return buildUserProfile(userService.getLoggedUser());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserProfile getUser(@PathVariable(value = "id") Long id) {
        return buildUserProfile(usersRepository.findOne(id));
    }

    private UserProfile buildUserProfile(User user) {
        UserProfile userProfile = new UserProfile();
        userProfile.id = user.getId();
        userProfile.about = user.getAbout();
        userProfile.email = user.getEmail();
        userProfile.firstName = user.getFirstname();
        userProfile.lastName = user.getLastname();
        userProfile.nickname = user.getNickname();
        userProfile.picture = user.getPicture();
        userProfile.webPage = user.getWebpage();

        return userProfile;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public UserProfile createUser(@RequestBody UserProfile userProfile) {
        User user = usersRepository.findByAuthId(userProfile.userId);
        if (user != null) {
            userProfile.id = user.getId();
            throw new UserAlreadyExistsException(userProfile);
        }

        user = new User();
        user.setFirstname(userProfile.firstName);
        user.setPicture(userProfile.picture);
        user.setNickname(userProfile.nickname);
        user.setAuthId(userProfile.userId);
        usersRepository.save(user);

        userProfile.id = user.getId();

        return userProfile;
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable(value = "id") Long id, @RequestBody UserProfile userProfile) {
        User user = usersRepository.findByAuthId(Auth0Helper.getAuthId());

        if (!userProfile.id.equals(id) || !user.getId().equals(id)) {
            throw new NotAllowedException();
        }

        user.setFirstname(userProfile.firstName);
        user.setLastname(userProfile.lastName);
        user.setPicture(userProfile.picture);
        user.setNickname(userProfile.nickname);
        user.setWebpage(userProfile.webPage);
        user.setEmail(userProfile.email);
        user.setAbout(userProfile.about);

        usersRepository.save(user);
    }

    @RequestMapping(value = "/{speakerId}/quickies", method = RequestMethod.GET)
    public Iterable<Quickie> getQuickiesOfSpeaker(@PathVariable(value = "speakerId") Long speakerId) {
        logger.info("getQuickiesOfSpeaker with id {}", speakerId);
        return quickiesRepository.findBySpeakerId(speakerId);
    }

    @RequestMapping(value = "/me/quickies", method = RequestMethod.GET)
    public Iterable<Quickie> getQuickiesOfCurrentSpeaker() {
        Long speakerId = userService.getLoggedUser().getId();

        logger.info("getQuickiesOfSpeaker with id {}", speakerId);
        return quickiesRepository.findBySpeakerId(speakerId);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public UserProfile handleAlreadyExistsException(UserAlreadyExistsException e) {
        return e.getUserProfile();
    }

    @ExceptionHandler({NotAllowedException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void handleNotAllowedException() {
    }
}
