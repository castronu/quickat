package org.quickat.service;

import org.quickat.da.QuickieTweet;
import org.quickat.da.Vote;
import org.quickat.da.builder.VoteBuilder;
import org.quickat.repository.QuickieTweetsRepository;
import org.quickat.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;

import java.util.Date;
import java.util.List;

public class TweetProcessor {
    public final static Logger LOGGER = LoggerFactory.getLogger(TweetProcessor.class);

    @Autowired
    private QuickieTweetsRepository quickieTweetsRepository;

    @Autowired
    private Twitter twitter;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private Environment env;

    @Scheduled(fixedRate = 30000)
    public void run() {
        if (env.getProperty("twitter.vote.enable", Boolean.class, false)) {
            LOGGER.info("Polling tweets");

            Iterable<QuickieTweet> all = quickieTweetsRepository.findAll();//TODO ONLY FIND ACTIVE!
            for (QuickieTweet quickieTweet : all) {
                processQuickieTweet(quickieTweet);
            }
        }
    }

    private void processQuickieTweet(QuickieTweet tweetEntity) {
        List<Tweet> retweets;
        try {
            retweets = twitter.timelineOperations().getRetweets(Long.valueOf(tweetEntity.getTweetId()));
        } catch (ResourceNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            return;
        }
        for (Tweet retweet : retweets) {
            long twitterUserId = retweet.getUser().getId();
            Vote byQuickieIdAndVoterIdAndType = voteRepository.findByQuickieIdAndVoterIdAndType(
                    tweetEntity.getQuickieId(),
                    twitterUserId, Vote.Type.VOTE);
            if (byQuickieIdAndVoterIdAndType == null) {
                LOGGER.info("User {} is going to vote for the quickie {}", retweet.getUser().getId(), tweetEntity.getQuickieId());
                Vote vote = VoteBuilder.aVote().
                        withDate(new Date()).
                        withQuickieId(tweetEntity.getQuickieId()).
                        withType(Vote.Type.VOTE).
                        withVoterId(retweet.getUser().getId()).build();
                voteRepository.save(vote);
                LOGGER.info("Vote created! {}", vote);
            } else {
                LOGGER.info("User {} has already voted for the quickie {}", retweet.getUser().getId(), tweetEntity.getQuickieId());
            }
        }
    }

}
