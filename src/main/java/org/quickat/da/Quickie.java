package org.quickat.da;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by aposcia on 14.01.15.
 */
@Entity
@Table(name = "quickies")
public class Quickie {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private Date postDate;
    private Date quickieDate;
    private Long userGroupId;
    private Long speakerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Long speakerId) {
        this.speakerId = speakerId;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }


    public Date getQuickieDate() {
        return quickieDate;
    }

    public void setQuickieDate(Date quickieDate) {
        this.quickieDate = quickieDate;
    }

    @Override
    public String toString() {
        return "Quickie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", postDate=" + postDate +
                ", quickieDate=" + quickieDate +
                ", userGroupId=" + userGroupId +
                ", speakerId=" + speakerId +
                '}';
    }
}
