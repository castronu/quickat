package org.quickat.da.builder;

import org.quickat.da.UserGroup;

/**
 * Created by castronu on 17/01/15.
 */
public class UserGroupBuilder {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private String location;
    private Long contactId;
    private String bio;
    private String website;

    private UserGroupBuilder() {
    }

    public static UserGroupBuilder anUserGroup() {
        return new UserGroupBuilder();
    }

    public UserGroupBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserGroupBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserGroupBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public UserGroupBuilder withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public UserGroupBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    public UserGroupBuilder withContactId(Long contactId) {
        this.contactId = contactId;
        return this;
    }

    public UserGroupBuilder withBio(String bio) {
        this.bio = bio;
        return this;
    }

    public UserGroupBuilder withWebsite(String website) {
        this.website = website;
        return this;
    }

    public UserGroupBuilder but() {
        return anUserGroup().withId(id).withName(name).withDescription(description).withIcon(icon).withLocation(location).withContactId(contactId).withBio(bio).withWebsite(website);
    }

    public UserGroup build() {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        userGroup.setName(name);
        userGroup.setDescription(description);
        userGroup.setIcon(icon);
        userGroup.setLocation(location);
        userGroup.setContactId(contactId);
        userGroup.setBio(bio);
        userGroup.setWebsite(website);
        return userGroup;
    }
}
