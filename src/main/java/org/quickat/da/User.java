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
    private String webpage;
    private String about;
    private String picture;
    private String authId;
    private String facebookUid;
    private String twitterUid;
    private String linkedinUid;
    private String googleUid;


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getFacebookUid() {
        return facebookUid;
    }

    public void setFacebookUid(String facebookUid) {
        this.facebookUid = facebookUid;
    }

    public String getGoogleUid() {
        return googleUid;
    }

    public void setGoogleUid(String googleUid) {
        this.googleUid = googleUid;
    }

    public String getLinkedinUid() {
        return linkedinUid;
    }

    public void setLinkedinUid(String linkedinUid) {
        this.linkedinUid = linkedinUid;
    }

    public String getTwitterUid() {
        return twitterUid;
    }

    public void setTwitterUid(String twitterUid) {
        this.twitterUid = twitterUid;
    }
}
