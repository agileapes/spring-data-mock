package com.mmnaseri.utils.spring.data.proxy.impl.adapters;

import com.mmnaseri.utils.spring.data.domain.Invocation;
import com.mmnaseri.utils.spring.data.tools.CollectionInstanceUtils;

import java.util.Collection;

/**
 * <p>This adapter will try to adapt a {@literal null} value to a collection.</p>
 *
 * <p>It adapts results if the return type is of type {@link Collection} and the original value is {@literal null}.</p>
 *
 * <p>This adapter runs at the priority of {@literal -300}.</p>
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/24/15)
 */
public class NullToCollectionResultAdapter extends AbstractResultAdapter<Collection> {

    public NullToCollectionResultAdapter() {
        super(-300);
    }

    @Override
    public boolean accepts(Invocation invocation, Object originalValue) {
        return originalValue == null && Collection.class.isAssignableFrom(invocation.getMethod().getReturnType());
    }

    @Override
    public Collection adapt(Invocation invocation, Object originalValue) {
        return CollectionInstanceUtils.getCollection(invocation.getMethod().getReturnType());
    }

}
