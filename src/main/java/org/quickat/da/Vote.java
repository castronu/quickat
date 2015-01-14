package org.quickat.da;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Christophe Pollet
 */
@Entity(name = "votes")
public class Vote {
    public enum Type {
        VOTE, LIKE
    }

    @Id
    @GeneratedValue
    private Long id;

    private Long quickieId;
    private Long voterId;
    private Date date;
    private Type type;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getVoterId() {
        return voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }
}
