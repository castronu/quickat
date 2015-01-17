package org.quickat.da.builder;

import org.quickat.da.Quickie;

import java.util.Date;

/**
 * Created by aposcia on 17.01.15.
 */
public class QuickieBuilder {
    private Long id;
    private String title;
    private String description;
    private Date postDate;
    private Date quickieDate;
    private Long userGroupId;
    private Long speakerId;

    private QuickieBuilder() {
    }

    public static QuickieBuilder aQuickie() {
        return new QuickieBuilder();
    }

    public QuickieBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public QuickieBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public QuickieBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public QuickieBuilder withPostDate(Date postDate) {
        this.postDate = postDate;
        return this;
    }

    public QuickieBuilder withQuickieDate(Date quickieDate) {
        this.quickieDate = quickieDate;
        return this;
    }

    public QuickieBuilder withUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
        return this;
    }

    public QuickieBuilder withSpeakerId(Long speakerId) {
        this.speakerId = speakerId;
        return this;
    }

    public QuickieBuilder but() {
        return aQuickie().withId(id).withTitle(title).withDescription(description).withPostDate(postDate).withQuickieDate(quickieDate).withUserGroupId(userGroupId).withSpeakerId(speakerId);
    }

    public Quickie build() {
        Quickie quickie = new Quickie();
        quickie.setId(id);
        quickie.setTitle(title);
        quickie.setDescription(description);
        quickie.setPostDate(postDate);
        quickie.setQuickieDate(quickieDate);
        quickie.setUserGroupId(userGroupId);
        quickie.setSpeakerId(speakerId);
        return quickie;
    }
}
