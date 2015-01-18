package org.quickat.web;

import org.quickat.da.UserGroup;
import org.quickat.repository.UserGroupsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Christophe Pollet
 */
@RestController
@RequestMapping("userGroups")
public class UserGroupsController {
    final static Logger logger = LoggerFactory.getLogger(QuickieController.class);

    @Autowired
    public UserGroupsRepository userGroupsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<UserGroup> getGroups() {
        return userGroupsRepository.findAll();
    }
}
