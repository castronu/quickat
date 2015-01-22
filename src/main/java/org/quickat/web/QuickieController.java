package org.quickat.web;

import org.quickat.da.*;
import org.quickat.da.builder.QuickieTweetBuilder;
import org.quickat.da.builder.VoteBuilder;
import org.quickat.repository.*;
import org.quickat.service.UserService;
import org.quickat.web.dto.FullComment;
import org.quickat.web.dto.FullQuickie;
import org.quickat.web.dto.QuickiesCounters;
import org.quickat.web.exception.AlreadyVotedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private static final int NB_TOP_RESULTS_TO_RETURN = 3;

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
    private UserService userService;

    @Autowired
    private Twitter twitter;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<FullQuickie> getQuickies(@RequestParam(value = "filter", defaultValue = "future", required = false) String filter) {
        Iterable<Quickie> quickies = Collections.emptyList();

        switch (filter) {
            case "future":
                quickies = quickiesRepository.findByQuickieDateAfter(new Date());
                break;
            case "topFuture":
                quickies = getTop3(Vote.Type.VOTE);
                break;
            case "past":
                quickies = quickiesRepository.findByQuickieDateBefore(new Date());
                break;
            case "topPast":
                quickies = getTop3(Vote.Type.LIKE);
                break;
        }

        User user = userService.getLoggedUser();
        List<FullQuickie> fullQuickies = new LinkedList<>();

        // FIXME use a complete Quickie mapping instead? not sure... My opinion is that we should always send DTOs though HTTP
        for (Quickie quickie : quickies) {
            FullQuickie fullQuickie = new FullQuickie();
            fullQuickie.quickie = quickie;
            fullQuickie.speaker = usersRepository.findOne(quickie.getSpeakerId());

            switch (filter) {
                case "future":
                case "topFuture":
                    fullQuickie.score = votesRepository.countByQuickieIdAndType(quickie.getId(), Vote.Type.VOTE);
                    fullQuickie.scored = votesRepository.countByQuickieIdAndVoterIdAndType(quickie.getId(), user.getId(), Vote.Type.VOTE) > 0;
                    break;
                case "past":
                case "topPast":
                    fullQuickie.score = votesRepository.countByQuickieIdAndType(quickie.getId(), Vote.Type.LIKE);
                    fullQuickie.scored = votesRepository.countByQuickieIdAndVoterIdAndType(quickie.getId(), user.getId(), Vote.Type.LIKE) > 0;
                    break;
            }

            fullQuickie.votes = votesRepository.countByQuickieIdAndType(quickie.getId(), Vote.Type.VOTE);
            fullQuickie.likes = votesRepository.countByQuickieIdAndType(quickie.getId(), Vote.Type.LIKE);
            fullQuickie.voted = votesRepository.countByQuickieIdAndVoterIdAndType(quickie.getId(), user.getId(), Vote.Type.VOTE) > 0;
            fullQuickie.liked = votesRepository.countByQuickieIdAndVoterIdAndType(quickie.getId(), user.getId(), Vote.Type.LIKE) > 0;

            fullQuickie.comments = new LinkedList<>();
            for (Comment comment : commentsRepository.findByQuickieIdOrderByDateDesc(quickie.getId())) {
                FullComment fullComment = new FullComment();
                fullComment.comment = comment.getComment();
                fullComment.date = comment.getDate();
                fullComment.user = usersRepository.findOne(comment.getUserId());
                fullQuickie.comments.add(fullComment);
            }

            fullQuickies.add(fullQuickie);
        }

        return fullQuickies;
    }

    @RequestMapping(value = "/counters", method = RequestMethod.GET)
    public QuickiesCounters getCounters() {
        QuickiesCounters quickiesCounters = new QuickiesCounters();

        quickiesCounters.future = quickiesRepository.countByQuickieDateAfter(new Date());
        quickiesCounters.past = quickiesRepository.countByQuickieDateBefore(new Date());
        quickiesCounters.my = quickiesRepository.countBySpeakerId(userService.getLoggedUser().getId());

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
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public Quickie createQuickie(@RequestBody Quickie quickie) {
        quickie.setPostDate(new Date());
        quickie.setSpeakerId(userService.getLoggedUser().getId());
        Quickie save = quickiesRepository.save(quickie);


        String tweetText = "Hey! A new quickie has been created: " + quickie.getTitle() + "! retweet me to vote for it!";
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
                .withType(getVoteType(quickieId))
                .withVoterId(userService.getLoggedUser().getId())
                .build();

        try {
            votesRepository.save(vote);
        } catch (Exception e) { // FIXME should be MySQLIntegrityConstraintViolationException but not in signature
            throw new AlreadyVotedException();
        }
    }

    private Vote.Type getVoteType(Long quickieId) {
        Quickie quickie = quickiesRepository.findOne(quickieId);
        if (quickie.getQuickieDate().after(new Date())) {
            return Vote.Type.VOTE;
        } else {
            return Vote.Type.LIKE;
        }
    }

    @RequestMapping(value = "/{id}/vote", method = RequestMethod.DELETE)
    public void unvoteQuickie(@PathVariable(value = "id") Long quickieId) {
        Vote vote = votesRepository.findByQuickieIdAndVoterIdAndType(quickieId, userService.getLoggedUser().getId(), getVoteType(quickieId));
        votesRepository.delete(vote);
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST)
    public void createComment(@PathVariable(value = "id") Long quickieId, @RequestBody Comment comment) {
        logger.info("Recording comment {} for quickieId:", comment, quickieId);
        comment.setDate(new Date());
        comment.setSubject("NA");
        comment.setUserId(userService.getLoggedUser().getId());
        commentsRepository.save(comment);
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public List<FullComment> getComments(@PathVariable(value = "id") Long quickieId) {
        List<FullComment> comments = new LinkedList<>();

        for (Comment comment : commentsRepository.findByQuickieIdOrderByDateDesc(quickieId)) {
            FullComment fullComment = new FullComment();
            fullComment.comment = comment.getComment();
            fullComment.date = comment.getDate();
            fullComment.user = usersRepository.findOne(comment.getUserId());
            comments.add(fullComment);
        }

        return comments;
    }

    @ExceptionHandler({AlreadyVotedException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void handleAlreadyVotedException() {
    }


    private Iterable<Quickie> getTop3(Vote.Type voteType) {
        List<Long> voteCounts = votesRepository.getVoteCountsOfType(voteType);
        int nbElemsToRetrieve = voteCounts.size() >= NB_TOP_RESULTS_TO_RETURN ? NB_TOP_RESULTS_TO_RETURN : voteCounts.size();
        voteCounts = voteCounts.subList(0, nbElemsToRetrieve);

        List<Quickie> results = new ArrayList<>(voteCounts.size());
        for (Long vote : voteCounts) {
            results.add(quickiesRepository.findOne(vote));
        }
        return results;
    }
}
