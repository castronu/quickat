package org.quickat.da;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by castronu on 20/01/15.
 */
@Entity
@Table(name = "quickie_tweet")
public class QuickieTweet {
    @Id
    @GeneratedValue
    private Long id;
    private String tweetId;
    private Boolean active;
    private Long quickieId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getQuickieId() {
        return quickieId;
    }

    public void setQuickieId(Long quickieId) {
        this.quickieId = quickieId;
    }
}
