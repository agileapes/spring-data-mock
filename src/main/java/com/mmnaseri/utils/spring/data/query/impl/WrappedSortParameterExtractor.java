package com.mmnaseri.utils.spring.data.query.impl;

import com.mmnaseri.utils.spring.data.domain.Invocation;
import com.mmnaseri.utils.spring.data.error.InvalidArgumentException;
import com.mmnaseri.utils.spring.data.error.RepositoryDefinitionException;
import com.mmnaseri.utils.spring.data.query.Sort;
import com.mmnaseri.utils.spring.data.query.SortParameterExtractor;

/**
 * This extractor will return the statically defined sort metadata for a query method (as parsed from the method name).
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/20/15)
 */
public class WrappedSortParameterExtractor implements SortParameterExtractor {

    private final Sort sort;

    public WrappedSortParameterExtractor(Sort sort) {
        if (sort == null) {
            throw new RepositoryDefinitionException(null, "Predefined sort method must not resolve to null");
        }
        this.sort = sort;
    }

    @Override
    public Sort extract(Invocation invocation) {
        if (invocation == null) {
            throw new InvalidArgumentException("Invocation cannot be null");
        }
        return sort;
    }

}
