package org.quickat.web.dto;

import org.quickat.da.Comment;
import org.quickat.da.Quickie;
import org.quickat.da.User;

/**
 * @author Christophe Pollet
 */
public class FullQuickie {
    public Quickie quickie;
    public User speaker;
    public Iterable<Comment> comments;
    public int votes;
    public int likes;
    public boolean voted;
    public boolean liked;
}
