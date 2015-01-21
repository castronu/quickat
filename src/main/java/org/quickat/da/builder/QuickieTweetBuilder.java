package org.quickat.da.builder;

import org.quickat.da.QuickieTweet;

/**
 * Created by castronu on 20/01/15.
 */
public class QuickieTweetBuilder {
    private Long id;
    private String tweetId;
    private Boolean active;
    private Long quickieId;

    private QuickieTweetBuilder() {
    }

    public static QuickieTweetBuilder aQuickieTweet() {
        return new QuickieTweetBuilder();
    }

    public QuickieTweetBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public QuickieTweetBuilder withTweetId(String tweetId) {
        this.tweetId = tweetId;
        return this;
    }

    public QuickieTweetBuilder withActive(Boolean active) {
        this.active = active;
        return this;
    }

    public QuickieTweetBuilder withQuickieId(Long quickieId) {
        this.quickieId = quickieId;
        return this;
    }

    public QuickieTweetBuilder but() {
        return aQuickieTweet().withId(id).withTweetId(tweetId).withActive(active).withQuickieId(quickieId);
    }

    public QuickieTweet build() {
        QuickieTweet quickieTweet = new QuickieTweet();
        quickieTweet.setId(id);
        quickieTweet.setTweetId(tweetId);
        quickieTweet.setActive(active);
        quickieTweet.setQuickieId(quickieId);
        return quickieTweet;
    }
}
