package org.quickat.web;

import org.quickat.da.Quickie;
import org.quickat.repository.QuickiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by aposcia on 14.01.15.
 */
@RestController
@RequestMapping("quickies")
public class QuickieController {

    final static Logger logger = LoggerFactory.getLogger(QuickieController.class);

    @Autowired
    public QuickiesRepository quickiesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Quickie> getQuickies() {
        return quickiesRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Quickie getQuickie(@PathVariable(value = "id") Long id) {
        return quickiesRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public Quickie createQuickie(@RequestBody Quickie quickie) {
        logger.info(quickie.toString());
        quickie.setDate(new Date());
        quickie.setUserGroupId(1L);
        quickie.setSpeakerId(1L);
        return quickiesRepository.save(quickie);
    }
}
