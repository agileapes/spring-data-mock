package com.mmnaseri.utils.spring.data.query.impl;

import com.mmnaseri.utils.spring.data.domain.Invocation;
import com.mmnaseri.utils.spring.data.error.InvalidArgumentException;
import com.mmnaseri.utils.spring.data.query.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (9/19/15)
 */
public class PageableSortParameterExtractor extends AbstractSortParameterExtractor {

    private final int index;

    public PageableSortParameterExtractor(int index) {
        this.index = index;
    }

    @Override
    public Sort extract(Invocation invocation) {
        final Object value = invocation.getArguments()[index];
        if (value == null) {
            throw new InvalidArgumentException("Page value should not be empty");
        }
        if (value instanceof Pageable) {
            final Pageable pageable = (Pageable) value;
            final org.springframework.data.domain.Sort sort = pageable.getSort();
            if (sort == null) {
                return null;
            }
            return getSort(sort);
        }
        throw new InvalidArgumentException("No valid value was passed to deduce the sort description from");
    }

}
