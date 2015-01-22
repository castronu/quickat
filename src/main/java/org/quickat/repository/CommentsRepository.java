package org.quickat.repository;

import org.quickat.da.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aposcia on 14.01.15.
 */
@Repository
public interface CommentsRepository extends CrudRepository<Comment, Long> {
    Iterable<Comment> findByQuickieId(Long quickieId);

    Iterable<Comment> findByQuickieIdOrderByDateDesc(Long quickieId);
}