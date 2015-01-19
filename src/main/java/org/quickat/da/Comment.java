package org.quickat.da;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Christophe Pollet
 */
@Entity(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private Long quickieId;
    private Long userId;
    private String subject;
    private String comment;
    private Date date;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuickieId() {
        return quickieId;
    }

    public void setQuickieId(Long quickieId) {
        this.quickieId = quickieId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", quickieId=" + quickieId +
                ", userId=" + userId +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}
