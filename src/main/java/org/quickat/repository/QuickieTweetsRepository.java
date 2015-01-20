package org.quickat.repository;

import org.quickat.da.Quickie;
import org.quickat.da.QuickieTweet;
import org.quickat.da.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by castronu on 20/01/15.
 */
@Repository
public interface QuickieTweetsRepository extends CrudRepository<QuickieTweet, Long> {

    Iterable<QuickieTweet> findByTweetId(Long speakerId);
}
