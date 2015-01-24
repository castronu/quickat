package org.quickat.repository;

import org.quickat.da.Quickie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by aposcia on 14.01.15.
 */
@Repository
public interface QuickiesRepository extends CrudRepository<Quickie, Long> {

    Iterable<Quickie> findBySpeakerId(Long speakerId);

    Iterable<Quickie> findByQuickieDateAfter(Date date);

    Iterable<Quickie> findByQuickieDateBefore(Date date);

    long countByQuickieDateAfter(Date date);

    long countByQuickieDateBefore(Date date);

    long countBySpeakerId(Long speakerId);

    @Query(value = "select quickies.* " +
            "from votes, quickies " +
            "where votes.quickie_id = quickies.id and votes.type = 'VOTE' and quickies.quickie_date > now() " +
            "group by votes.quickie_id " +
            "order by count(votes.id) desc " +
            "limit ?#{[0]}", nativeQuery = true)
    Iterable<Quickie> getFutureOrderedByVoteCount(int limit);

    @Query(value = "select quickies.* " +
            "from votes, quickies " +
            "where votes.quickie_id = quickies.id and votes.type = 'LIKE' and quickies.quickie_date < now() " +
            "group by votes.quickie_id " +
            "order by count(votes.id) desc " +
            "limit ?#{[0]}", nativeQuery = true)
    Iterable<Quickie> getPastOrderedByLikeCount(int limit);
}
