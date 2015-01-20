package org.quickat.da;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by aposcia on 14.01.15.
 */
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String firstname;
    private String lastname;
    private String nickname;
    private String email;
    private String facebookUid;
    private String twitterUid;
    private String linkedinUid;
    private String googleUid;
    private String webpage;
    private String about;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookUid() {
        return facebookUid;
    }

    public void setFacebookUid(String facebookUid) {
        this.facebookUid = facebookUid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGoogleUid() {
        return googleUid;
    }

    public void setGoogleUid(String googleUid) {
        this.googleUid = googleUid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLinkedinUid() {
        return linkedinUid;
    }

    public void setLinkedinUid(String linkedinUid) {
        this.linkedinUid = linkedinUid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTwitterUid() {
        return twitterUid;
    }

    public void setTwitterUid(String twitterUid) {
        this.twitterUid = twitterUid;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
