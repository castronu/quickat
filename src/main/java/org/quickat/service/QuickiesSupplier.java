package org.quickat.service;

import org.quickat.da.Quickie;

import java.util.List;

/**
 * @author Christophe Pollet
 */
public interface QuickiesSupplier {
    Iterable<Quickie> getQuickies(String filter, List<Long> groups);

    Iterable<Quickie> getQuickies(String filter);
}
