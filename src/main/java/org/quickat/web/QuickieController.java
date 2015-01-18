package org.quickat.web;

import org.quickat.da.Quickie;
import org.quickat.repository.QuickiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by aposcia on 14.01.15.
 */
@RestController
@RequestMapping("quickies")
public class QuickieController {

    public final static Logger LOGGER = LoggerFactory.getLogger(QuickieController.class);


    @Autowired
    public QuickiesRepository quickiesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Quickie> getQuickies() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("GetQuickies request from user: {}",auth.getName());
        return quickiesRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Quickie getQuickie(@PathVariable(value = "id") Long id) {
        return quickiesRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public Quickie createQuickie(@RequestBody Quickie quickie) {
        LOGGER.info(quickie.toString());
        quickie.setPostDate(new Date());
        quickie.setUserGroupId(1L);
        quickie.setSpeakerId(1L);
        return quickiesRepository.save(quickie);
    }
}
