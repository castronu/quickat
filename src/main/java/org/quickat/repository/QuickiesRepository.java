package org.quickat.repository;

import org.quickat.da.Quickie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aposcia on 14.01.15.
 */
@Repository
public interface QuickiesRepository extends CrudRepository<Quickie, Long> {


//    @
    //  public getBlabla(String id);
}
