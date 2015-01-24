package org.quickat.service;

import org.quickat.da.Quickie;

/**
 * @author Christophe Pollet
 */
public interface QuickiesSupplier {
    Iterable<Quickie> getQuickies(String filter);
}
