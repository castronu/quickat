package org.quickat.repository;


import org.quickat.da.Vote;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Christophe Pollet
 */
public interface VoteRepository extends CrudRepository<Vote, Long> {
    Vote findByQuickieIdAndVoterIdAndType(Long quickieId, Long userId, Vote.Type type);

    int countByQuickieIdAndType(Long id, Vote.Type vote);

    int countByQuickieIdAndVoterIdAndType(Long quickieId, Long userId, Vote.Type type);
}
