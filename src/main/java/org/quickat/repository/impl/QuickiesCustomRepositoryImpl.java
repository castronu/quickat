package org.quickat.repository.impl;

import org.quickat.da.Quickie;

import java.util.Collections;

/**
 * Created by aposcia on 19.01.15.
 */
public class QuickiesCustomRepositoryImpl implements org.quickat.repository.QuickiesCustomRepository {
    @Override
    public Iterable<Quickie> getTop3ActiveQuickies() {
        return Collections.emptyList();
    }

    @Override
    public Iterable<Quickie> getTop3InactiveQuickies() {
        return Collections.emptyList();
    }
}
