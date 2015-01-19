package org.quickat.repository;

import org.quickat.da.Quickie;

/**
 * Created by aposcia on 19.01.15.
 */
public interface QuickiesCustomRepository {
    Iterable<Quickie> getTop3ActiveQuickies();

    Iterable<Quickie> getTop3InactiveQuickies();
}
