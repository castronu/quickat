package org.quickat.repository;

import org.quickat.da.Quickie;
import org.quickat.da.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Christophe Pollet
 */
@Repository
public interface UserGroupsRepository extends CrudRepository<UserGroup, Long> {
}
