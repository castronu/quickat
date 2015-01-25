package org.quickat.web.dto;

import org.quickat.da.Quickie;
import org.quickat.da.User;
import org.quickat.da.UserGroup;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public class FullQuickie {
    public Quickie quickie;
    public User speaker;
    public List<FullComment> comments;
    public UserGroup userGroup;
    public int votes;
    public int likes;
    public boolean voted;
    public boolean liked;
    public int score;
    public boolean scored;
}
