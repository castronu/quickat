package org.quickat.repository;

import org.quickat.da.Quicky;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by aposcia on 14.01.15.
 */
@RepositoryRestResource(collectionResourceRel = "quickies", path = "quickies")
public interface QuickiesRepository extends PagingAndSortingRepository<Quicky, Long> {

    List<Quicky> findAll();

}
