package org.quickat.repository;

import org.quickat.da.Quickie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by aposcia on 14.01.15.
 */
@Repository
public interface QuickiesRepository extends CrudRepository<Quickie, Long> {

    Iterable<Quickie> findBySpeakerId(Long speakerId);

    Iterable<Quickie> findByQuickieDateIsNullOrQuickieDateAfter(Date date);

    @Query("select q " +
            "from Quickie q " +
            "where q.userGroupId in :groups and (q.quickieDate is null or q.quickieDate > :date)")
    Iterable<Quickie> findByUserGroupIdInAndQuickieDateIsNullOrQuickieDateAfter(@Param("groups") List<Long> groupIds, @Param("date") Date date);

    Iterable<Quickie> findByQuickieDateBefore(Date date);

    Iterable<Quickie> findByUserGroupIdInAndQuickieDateBefore(List<Long> groups, Date date);

    long countByQuickieDateIsNullOrQuickieDateAfter(Date date);

    long countByQuickieDateBefore(Date date);

    long countBySpeakerId(Long speakerId);

    @Query(value = "select quickies.* " +
            "from votes, quickies " +
            "where votes.quickie_id = quickies.id and votes.type = 'VOTE' and (quickies.quickie_date is null or quickies.quickie_date > now()) " +
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
