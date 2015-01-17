package org.quickat.web;

import org.quickat.ToDelete;
import org.quickat.da.Quickie;
import org.quickat.da.Vote;
import org.quickat.da.builder.VoteBuilder;
import org.quickat.repository.QuickiesRepository;
import org.quickat.repository.VoteRepository;
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

    @Autowired
    private VoteRepository voteRepository;

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
        quickie.setPostDate(new Date());
        quickie.setSpeakerId(ToDelete.USER_ID);

        return quickiesRepository.save(quickie);
    }

    @RequestMapping(value = "/{id}/vote", method = RequestMethod.POST)
    public void voteQuickie(@PathVariable(value = "id") Long quickieId) {
        Vote vote = VoteBuilder.aVote()
                .withDate(new Date())
                .withQuickieId(quickieId)
                .withType(Vote.Type.VOTE)
                .withVoterId(ToDelete.USER_ID)
                .build();

        voteRepository.save(vote);
    }

    @RequestMapping(value = "/{id}/vote", method = RequestMethod.DELETE)
    public void unvoteQuickie(@PathVariable(value = "id") Long quickieId) {
        Vote vote = voteRepository.findByQuickieIdAndVoterIdAndType(quickieId, ToDelete.USER_ID, Vote.Type.VOTE);
        voteRepository.delete(vote);
    }
}
