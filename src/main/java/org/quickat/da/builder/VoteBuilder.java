package org.quickat.da.builder;

import org.quickat.da.Vote;

import java.util.Date;

/**
 * Created by castronu on 17/01/15.
 */
public class VoteBuilder {
    private Long id;
    private Long quickieId;
    private Long voterId;
    private Long tweetId;
    private Date date;
    private Vote.Type type;

    private VoteBuilder() {
    }

    public static VoteBuilder aVote() {
        return new VoteBuilder();
    }

    public VoteBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public VoteBuilder withQuickieId(Long quickieId) {
        this.quickieId = quickieId;
        return this;
    }

    public VoteBuilder withVoterId(Long voterId) {
        this.voterId = voterId;
        return this;
    }

    public VoteBuilder withTweetId(Long tweetId) {
        this.tweetId = tweetId;
        return this;
    }

    public VoteBuilder withDate(Date date) {
        this.date = date;
        return this;
    }

    public VoteBuilder withType(Vote.Type type) {
        this.type = type;
        return this;
    }

    public VoteBuilder but() {
        return aVote().withId(id).withQuickieId(quickieId).withVoterId(voterId).withDate(date).withType(type);
    }

    public Vote build() {
        Vote vote = new Vote();
        vote.setId(id);
        vote.setQuickieId(quickieId);
        vote.setVoterId(voterId);
        vote.setTweetId(tweetId);
        vote.setDate(date);
        vote.setType(type);
        return vote;
    }
}
