package org.quickat.service;

import org.quickat.da.Quickie;
import org.quickat.repository.QuickiesRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

/**
 * @author Christophe Pollet
 */
@Component
public class DefaultQuickiesSupplier implements QuickiesSupplier, InitializingBean {
    private static final int TOP_SIZE = 3;

    @Autowired
    private QuickiesRepository quickiesRepository;

    private Map<String, Function<List<Long>, Iterable<Quickie>>> quickiesSuppliers;

    @Override
    public void afterPropertiesSet() throws Exception {
        quickiesSuppliers = new HashMap<>(4);
        quickiesSuppliers.put("future", (List<Long> groups) -> {
            if (groups.isEmpty()) {
                return quickiesRepository.findByQuickieDateIsNullOrQuickieDateAfter(new Date());
            }
            return quickiesRepository.findByUserGroupIdInAndQuickieDateIsNullOrQuickieDateAfter(groups, new Date());
        });
        quickiesSuppliers.put("past", (List<Long> groups) -> {
            if (groups.isEmpty()) {
                return quickiesRepository.findByQuickieDateBefore(new Date());
            }
            return quickiesRepository.findByUserGroupIdInAndQuickieDateBefore(groups, new Date());
        });
        quickiesSuppliers.put("topFuture", (List<Long> groups) -> quickiesRepository.getFutureOrderedByVoteCount(TOP_SIZE));
        quickiesSuppliers.put("topPast", (List<Long> groups) -> quickiesRepository.getPastOrderedByLikeCount(TOP_SIZE));
    }

    @Override
    public Iterable<Quickie> getQuickies(String filter, List<Long> groups) {
        if (!quickiesSuppliers.containsKey(filter)) {
            throw new IllegalArgumentException("[" + filter + "] is not a valid quickies supplier");
        }

        return quickiesSuppliers.get(filter).apply(groups);
    }

    @Override
    public Iterable<Quickie> getQuickies(String filter) {
        return getQuickies(filter, Collections.emptyList());
    }
}
