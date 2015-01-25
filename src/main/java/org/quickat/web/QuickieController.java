package org.quickat.web;

import org.quickat.da.*;
import org.quickat.da.builder.QuickieTweetBuilder;
import org.quickat.da.builder.VoteBuilder;
import org.quickat.repository.*;
import org.quickat.service.QuickiesSupplier;
import org.quickat.service.UserService;
import org.quickat.web.dto.FullComment;
import org.quickat.web.dto.FullQuickie;
import org.quickat.web.dto.QuickiesCounters;
import org.quickat.web.exception.AlreadyVotedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    private final static Logger logger = LoggerFactory.getLogger(QuickieController.class);

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

    @Autowired
    private QuickiesSupplier quickiesSupplier;

    @Autowired
    private Environment env;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<FullQuickie> getQuickies(@RequestParam(value = "filter", defaultValue = "future", required = false) String filter,
                                             @RequestParam(value = "groups", defaultValue = "", required = false) String groups) {
        Iterable<Quickie> quickies = Collections.emptyList();

        try {
            String[] groupListArray = groups.split(",");
            List<Long> groupList = new ArrayList<>(groupListArray.length);
            for (String s : groupListArray) {
                try {
                    groupList.add(Long.parseLong(s));
                } catch (NumberFormatException e) {
                    // We don't care :)
                }
            }

            quickies = quickiesSupplier.getQuickies(filter, groupList);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }

        User user = userService.getLoggedUser();
        List<FullQuickie> fullQuickies = new LinkedList<>();

        // FIXME use a complete Quickie mapping instead? not sure... My opinion is that we should always send DTOs though HTTP
        for (Quickie quickie : quickies) {
            FullQuickie fullQuickie = buildFullQuickie(quickie, user);

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

            fullQuickies.add(fullQuickie);
        }

        return fullQuickies;
    }

    private FullQuickie buildFullQuickie(Quickie quickie, User user) {
        FullQuickie fullQuickie = new FullQuickie();
        fullQuickie.quickie = quickie;
        fullQuickie.speaker = usersRepository.findOne(quickie.getSpeakerId());

        // FIXME remove if useless
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

        return fullQuickie;
    }

    @RequestMapping(value = "/counters", method = RequestMethod.GET)
    public QuickiesCounters getCounters() {
        QuickiesCounters quickiesCounters = new QuickiesCounters();

        quickiesCounters.future = quickiesRepository.countByQuickieDateIsNullOrQuickieDateAfter(new Date());
        quickiesCounters.past = quickiesRepository.countByQuickieDateBefore(new Date());
        quickiesCounters.my = quickiesRepository.countBySpeakerId(userService.getLoggedUser().getId());

        return quickiesCounters;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FullQuickie getQuickie(@PathVariable(value = "id") Long id) {
        return buildFullQuickie(quickiesRepository.findOne(id), userService.getLoggedUser());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteQuickie(@PathVariable(value = "id") Long id) {
        User user = usersRepository.findByAuthId(Auth0Helper.getAuthId());
        Quickie quickie = quickiesRepository.findOne(id);

        if (quickie == null || !quickie.getSpeakerId().equals(user.getId())) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.FORBIDDEN);
        }

        if (quickie.getQuickieDate() == null || quickie.getQuickieDate().before(new Date())) {
            quickiesRepository.delete(id);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        }

        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateQuickie(@RequestBody Quickie quickie) {
        User user = usersRepository.findByAuthId(Auth0Helper.getAuthId());
        Quickie originalQuickie = quickiesRepository.findOne(quickie.getId());

        if (originalQuickie == null || !originalQuickie.getSpeakerId().equals(user.getId())) {
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.FORBIDDEN);
        }

        originalQuickie.setUserGroupId(quickie.getUserGroupId());
        originalQuickie.setDescription(quickie.getDescription());
        originalQuickie.setQuickieDate(quickie.getQuickieDate());
        originalQuickie.setTitle(quickie.getTitle());

        quickiesRepository.save(originalQuickie);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public Quickie createQuickie(@RequestBody Quickie quickie) {
        quickie.setPostDate(new Date());
        quickie.setSpeakerId(userService.getLoggedUser().getId());
        Quickie save = quickiesRepository.save(quickie);

        if (env.getProperty("twitter.post.enable", Boolean.class, false)) {
            String tweetText = "Hey #UGQuickie! A new quickie has been proposed: " +
                    quickie.getTitle() +
                    " (http://quickat.cpollet.net/#/quickies/" + save.getId() +
                    ")! Retweet me to vote for it!";
            Tweet tweet = twitter.timelineOperations().updateStatus(new TweetData(tweetText));

            QuickieTweet quickieTweet = QuickieTweetBuilder.aQuickieTweet().
                    withQuickieId(save.getId()).
                    withActive(true).
                    withTweetId(String.valueOf(tweet.getId())).build();

            quickieTweetsRepository.save(quickieTweet);
            logger.info("Tweet Created!");
        }

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
        if (quickie.getQuickieDate() == null || quickie.getQuickieDate().after(new Date())) {
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
}
