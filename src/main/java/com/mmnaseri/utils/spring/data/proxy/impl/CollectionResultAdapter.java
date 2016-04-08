package com.mmnaseri.utils.spring.data.proxy.impl;

import com.mmnaseri.utils.spring.data.domain.Invocation;
import com.mmnaseri.utils.spring.data.error.ResultAdapterFailureException;
import com.mmnaseri.utils.spring.data.tools.CollectionInstanceUtils;

import java.util.Collection;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (9/28/15)
 */
public class CollectionResultAdapter extends AbstractIterableResultAdapter<Collection> {

    public CollectionResultAdapter() {
        super(-300);
    }

    @Override
    protected Collection doAdapt(Invocation invocation, Iterable iterable) {
        final Collection collection;
        try {
            collection = CollectionInstanceUtils.getCollection(invocation.getMethod().getReturnType());
        } catch (IllegalArgumentException e) {
            throw new ResultAdapterFailureException(iterable, invocation.getMethod().getReturnType(), e);
        }
        for (Object item : iterable) {
            //noinspection unchecked
            collection.add(item);
        }
        return collection;
    }

    @Override
    public boolean accepts(Invocation invocation, Object originalValue) {
        return originalValue != null && Collection.class.isAssignableFrom(invocation.getMethod().getReturnType());
    }
}
