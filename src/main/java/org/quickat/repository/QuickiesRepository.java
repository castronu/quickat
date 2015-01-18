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
}
