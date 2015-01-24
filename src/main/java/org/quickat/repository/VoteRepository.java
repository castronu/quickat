package org.quickat.repository;


import org.quickat.da.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Christophe Pollet
 */
@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
    Vote findByQuickieIdAndVoterIdAndType(Long quickieId, Long userId, Vote.Type type);

    Vote findByQuickieIdAndType(Long quickieId, Vote.Type type);

    int countByQuickieIdAndType(Long id, Vote.Type vote);

    int countByQuickieIdAndVoterIdAndType(Long quickieId, Long userId, Vote.Type type);

    int countByQuickieIdAndTweetIdAndType(Long quickieId, long tweetId, Vote.Type vote);
}
