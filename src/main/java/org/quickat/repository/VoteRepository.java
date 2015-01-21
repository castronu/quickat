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
    static final String FIND_TOP3_QUERY = "select v.quickieId from Vote v where type = :type group by v.quickieId order by count(v) desc";

    Vote findByQuickieIdAndVoterIdAndType(Long quickieId, Long userId, Vote.Type type);

    Vote findByQuickieIdAndType(Long quickieId, Vote.Type type);

    int countByQuickieIdAndType(Long id, Vote.Type vote);

    int countByQuickieIdAndVoterIdAndType(Long quickieId, Long userId, Vote.Type type);

    @Query(value = FIND_TOP3_QUERY)
    List<Long> getVoteCountsOfType(@Param(value = "type") Vote.Type type);

}
