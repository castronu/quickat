package org.quickat.service;

import org.quickat.da.Quickie;
import org.quickat.repository.QuickiesRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Christophe Pollet
 */
@Component
public class DefaultQuickiesSupplier implements QuickiesSupplier, InitializingBean {
    private static final int TOP_SIZE = 3;

    @Autowired
    private QuickiesRepository quickiesRepository;

    private Map<String, Supplier<Iterable<Quickie>>> quickiesSuppliers;

    @Override
    public void afterPropertiesSet() throws Exception {
        quickiesSuppliers = new HashMap<>(4);
        quickiesSuppliers.put("future", () -> quickiesRepository.findByQuickieDateAfter(new Date()));
        quickiesSuppliers.put("topFuture", () -> quickiesRepository.getFutureOrderedByVoteCount(TOP_SIZE));
        quickiesSuppliers.put("past", () -> quickiesRepository.findByQuickieDateBefore(new Date()));
        quickiesSuppliers.put("topPast", () -> quickiesRepository.getPastOrderedByLikeCount(TOP_SIZE));
    }

    @Override
    public Iterable<Quickie> getQuickies(String filter) {
        if (!quickiesSuppliers.containsKey(filter)) {
            throw new IllegalArgumentException("[" + filter + "] is not a valid quickies supplier");
        }

        return quickiesSuppliers.get(filter).get();
    }
}
