package org.quickat.service;

import org.quickat.da.QuickieTweet;
import org.quickat.da.Vote;
import org.quickat.da.builder.VoteBuilder;
import org.quickat.repository.QuickieTweetsRepository;
import org.quickat.repository.VoteRepository;
import org.quickat.web.QuickieController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by david on 2014-12-19.
 */
import org.springframework.scheduling.annotation.EnableScheduling;


public class TweetProcessor {
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#\\w+");
    public final static Logger LOGGER = LoggerFactory.getLogger(TweetProcessor.class);

    @Autowired
    private  QuickieTweetsRepository quickieTweetsRepository;
    @Autowired
    private  Twitter twitter;
    @Autowired
    private  VoteRepository voteRepository;

    /*public TweetProcessor(QuickieTweetsRepository quickieTweetsRepository,
                          VoteRepository voteRepository,
                          Twitter twitter) {
        this.quickieTweetsRepository = quickieTweetsRepository;
        this.twitter = twitter;
        this.voteRepository = voteRepository;
    } */



    @Scheduled(fixedRate = 30000)
    public void run() {

            LOGGER.info("I'm alive... {}",new Date());
            Iterable<QuickieTweet> all = quickieTweetsRepository.findAll();//TODO ONLY FIND ACTIVE!
            for (QuickieTweet quickieTweet : all) {
                processQuickieTweet(quickieTweet);
            }


    }

    private void processQuickieTweet(QuickieTweet tweetEntity) {
        List<Tweet> retweets;
        try {
            retweets = twitter.timelineOperations().getRetweets(Long.valueOf(tweetEntity.getTweetId()));
        } catch (ResourceNotFoundException e) {
            LOGGER.error(e.getMessage());       //TODO losing details
            return;
        }
        for (Tweet retweet : retweets) {
            long twitterUserId = retweet.getUser().getId();
            Vote byQuickieIdAndVoterIdAndType = voteRepository.findByQuickieIdAndVoterIdAndType(
                    tweetEntity.getQuickieId(),
                    twitterUserId, Vote.Type.VOTE);
            if (byQuickieIdAndVoterIdAndType==null) {
                LOGGER.info("User {} is going to vote for the quickie {}",retweet.getUser().getId(),tweetEntity.getQuickieId());
                //TODO insert in vote repository
                Vote vote = VoteBuilder.aVote().
                        withDate(new Date()).
                        withQuickieId(tweetEntity.getQuickieId()).
                        withType(Vote.Type.VOTE).
                        withVoterId(retweet.getUser().getId()).build();
                voteRepository.save(vote);
                LOGGER.info("Vote created! {}",vote);
            } else {
                LOGGER.info("User {} has already voted for the quickie {}",retweet.getUser().getId(),tweetEntity.getQuickieId());
            }

        }


    }

}
