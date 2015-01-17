package org.quickat.da.builder;

import org.quickat.da.User;

/**
 * Created by castronu on 17/01/15.
 */
public class UserBuilder {
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

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public UserBuilder withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withFacebookUid(String facebookUid) {
        this.facebookUid = facebookUid;
        return this;
    }

    public UserBuilder withTwitterUid(String twitterUid) {
        this.twitterUid = twitterUid;
        return this;
    }

    public UserBuilder withLinkedinUid(String linkedinUid) {
        this.linkedinUid = linkedinUid;
        return this;
    }

    public UserBuilder withGoogleUid(String googleUid) {
        this.googleUid = googleUid;
        return this;
    }

    public UserBuilder withWebpage(String webpage) {
        this.webpage = webpage;
        return this;
    }

    public UserBuilder but() {
        return anUser().withId(id).withFirstname(firstname).withLastname(lastname).withNickname(nickname).withEmail(email).withFacebookUid(facebookUid).withTwitterUid(twitterUid).withLinkedinUid(linkedinUid).withGoogleUid(googleUid).withWebpage(webpage);
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setFacebookUid(facebookUid);
        user.setTwitterUid(twitterUid);
        user.setLinkedinUid(linkedinUid);
        user.setGoogleUid(googleUid);
        user.setWebpage(webpage);
        return user;
    }
}
