package com.mmnaseri.utils.spring.data.proxy.impl.adapters;

import com.mmnaseri.utils.spring.data.domain.Invocation;

import java.util.Collections;

/**
 * <p>This adapter will try to adapt a {@literal null} value to an iterable.</p>
 *
 * <p>It adapts results if the return type is of type {@link Iterable} and the original value is {@literal null}.</p>
 *
 * <p>This adapter runs at the priority of {@literal -250}.</p>
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/24/15)
 */
public class NullToIterableResultAdapter extends AbstractResultAdapter<Iterable> {

    public NullToIterableResultAdapter() {
        super(-250);
    }

    @Override
    public boolean accepts(Invocation invocation, Object originalValue) {
        return invocation.getMethod().getReturnType().equals(Iterable.class) && originalValue == null;
    }

    @Override
    public Iterable adapt(Invocation invocation, Object originalValue) {
        return Collections.emptyList();
    }

}
