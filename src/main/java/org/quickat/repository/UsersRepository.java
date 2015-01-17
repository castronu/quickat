package org.quickat.repository;

import org.quickat.da.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aposcia on 14.01.15.
 */
@Repository
public interface UsersRepository extends CrudRepository<User, Long> {


//    @
    //  public getBlabla(String id);
}
