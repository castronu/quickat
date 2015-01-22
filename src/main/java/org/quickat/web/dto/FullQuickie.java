package org.quickat.web.dto;

import org.quickat.da.Quickie;
import org.quickat.da.User;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public class FullQuickie {
    public Quickie quickie;
    public User speaker;
    public List<FullComment> comments;
    public int votes;
    public int likes;
    public boolean voted;
    public boolean liked;
    public int score;
    public boolean scored;
}
