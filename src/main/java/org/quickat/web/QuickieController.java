package org.quickat.web;

import org.quickat.ToDelete;
import org.quickat.da.Comment;
import org.quickat.da.Quickie;
import org.quickat.da.QuickieTweet;
import org.quickat.da.Vote;
import org.quickat.da.builder.QuickieTweetBuilder;
import org.quickat.da.builder.VoteBuilder;
import org.quickat.repository.*;
import org.quickat.web.dto.FullQuickie;
import org.quickat.web.dto.QuickiesCounters;
import org.quickat.web.exception.AlreadyVotedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public QuickieTweetsRepository quickieTweetsRepository;

    @Autowired
    private VoteRepository votesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private Twitter twitter;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<FullQuickie> getQuickies(@RequestParam(value = "filter", defaultValue = "future", required = false) String filter) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("GetQuickies request from user: {}", auth.getName());

        Iterable<Quickie> quickies = Collections.emptyList();

        switch (filter) {
            case "future":
                quickies = quickiesRepository.findByQuickieDateAfter(new Date());
                break;

            case "past":
                quickies = quickiesRepository.findByQuickieDateBefore(new Date());
        }

        List<FullQuickie> fullQuickies = new LinkedList<FullQuickie>();

        // FIXME use a complete Quickie mapping instead? not sure... My opinion is that we should always send DTOs though HTTP
        for (Quickie quickie : quickies) {
            FullQuickie fullQuickie = new FullQuickie();
            fullQuickie.quickie = quickie;
            fullQuickie.speaker = usersRepository.findOne(quickie.getSpeakerId());
            fullQuickie.votes = votesRepository.countByQuickieIdAndType(quickie.getId(), Vote.Type.VOTE);
            fullQuickie.voted = votesRepository.countByQuickieIdAndVoterIdAndType(fullQuickie.quickie.getId(), ToDelete.USER_ID, Vote.Type.VOTE) > 0;

            //FIXME: user in comment... cf CPO comment
            fullQuickie.comments = commentsRepository.findByQuickieId(fullQuickie.quickie.getId());
            fullQuickies.add(fullQuickie);
        }

        return fullQuickies;
    }

    @RequestMapping(value = "/counters", method = RequestMethod.GET)
    public QuickiesCounters getCounters() {
        QuickiesCounters quickiesCounters = new QuickiesCounters();

        quickiesCounters.future = quickiesRepository.countByQuickieDateAfter(new Date());
        quickiesCounters.past = quickiesRepository.countByQuickieDateBefore(new Date());
        quickiesCounters.my = quickiesRepository.countBySpeakerId(ToDelete.USER_ID);

        return quickiesCounters;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Quickie getQuickie(@PathVariable(value = "id") Long id) {
        return quickiesRepository.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteQuickie(@PathVariable(value = "id") Long id) {
        logger.info("Delete quickie with id: {}", id);
        Quickie quickie = quickiesRepository.findOne(id);
        if (quickie.getQuickieDate().before(new Date())) {
            quickiesRepository.delete(id);
            return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public Quickie createQuickie(@RequestBody Quickie quickie) {
        quickie.setPostDate(new Date());
        quickie.setSpeakerId(ToDelete.USER_ID);
        Quickie save = quickiesRepository.save(quickie);


        String tweetText="Hey! A new quickie has been created: "+quickie.getTitle()+ "! retweet me to vote for it!";
        Tweet tweet = twitter.timelineOperations().updateStatus(new TweetData(tweetText));

        QuickieTweet quickieTweet = QuickieTweetBuilder.aQuickieTweet().
                withQuickieId(save.getId()).
                withActive(true).
                withTweetId(String.valueOf(tweet.getId())).build();

        quickieTweetsRepository.save(quickieTweet);
        logger.info("Tweet Created!");
        return save;
    }

    @RequestMapping(value = "/{id}/vote", method = RequestMethod.POST)
    public void voteQuickie(@PathVariable(value = "id") Long quickieId) {
        Vote vote = VoteBuilder.aVote()
                .withDate(new Date())
                .withQuickieId(quickieId)
                .withType(Vote.Type.VOTE)
                .withVoterId(ToDelete.USER_ID)
                .build();

        try {
            votesRepository.save(vote);
        } catch (Exception e) { // FIXME should be MySQLIntegrityConstraintViolationException but not in signature
            throw new AlreadyVotedException();
        }
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST)
    public void createComment(@PathVariable(value = "id") Long quickieId, @RequestBody Comment comment) {
        logger.info("Recording comment {} for quickieId:", comment, quickieId);
        comment.setDate(new Date());
        comment.setSubject("NA");
        comment.setUserId(ToDelete.USER_ID);
        commentsRepository.save(comment);
    }

    @RequestMapping(value = "/{id}/vote", method = RequestMethod.DELETE)
    public void unvoteQuickie(@PathVariable(value = "id") Long quickieId) {
        Vote vote = votesRepository.findByQuickieIdAndVoterIdAndType(quickieId, ToDelete.USER_ID, Vote.Type.VOTE);
        votesRepository.delete(vote);
    }

    @ExceptionHandler({AlreadyVotedException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void handleAlreadyVotedException() {
    }

    @RequestMapping(value = "/top3")
    public Iterable<Quickie> getTop3(@RequestParam(value = "type", defaultValue = "VOTE", required = false) Vote.Type voteType) {
        //TODO: use votetype in repository, may use a param for number of top results that we want
        List<Long> voteCounts = votesRepository.getVoteCounts();
        voteCounts = voteCounts.subList(0, 3);

        List<Quickie> results = new ArrayList<>(voteCounts.size());
        for (Long vote : voteCounts) {
            results.add(quickiesRepository.findOne(vote));
        }
        return results;


    }
}
