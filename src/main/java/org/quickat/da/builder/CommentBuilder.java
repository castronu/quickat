package org.quickat.da.builder;

import org.quickat.da.Comment;

import java.util.Date;

/**
 * Created by castronu on 17/01/15.
 */
public class CommentBuilder {
    private Long id;
    private Long quickieId;
    private Long userId;
    private String subject;
    private String _comment;   //TODO change with content
    private Date date;

    private CommentBuilder() {
    }

    public static CommentBuilder aComment() {
        return new CommentBuilder();
    }

    public CommentBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CommentBuilder withQuickieId(Long quickieId) {
        this.quickieId = quickieId;
        return this;
    }

    public CommentBuilder withUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public CommentBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public CommentBuilder withComment(String comment) {
        this._comment = comment;
        return this;
    }

    public CommentBuilder withDate(Date date) {
        this.date = date;
        return this;
    }

    public CommentBuilder but() {
        return aComment().withId(id).withQuickieId(quickieId).withUserId(userId).withSubject(subject).withComment(_comment).withDate(date);
    }

    public Comment build() {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setQuickieId(quickieId);
        comment.setUserId(userId);
        comment.setSubject(subject);
        comment.setComment(_comment);
        comment.setDate(date);
        return comment;
    }
}
