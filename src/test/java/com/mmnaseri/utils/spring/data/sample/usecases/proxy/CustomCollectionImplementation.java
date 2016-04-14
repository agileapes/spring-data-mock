package com.mmnaseri.utils.spring.data.sample.usecases.proxy;

import com.mmnaseri.utils.spring.data.proxy.impl.adapters.EmptyIterator;

import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (4/12/16, 5:31 PM)
 */
class CustomCollectionImplementation extends AbstractCollection {

    @Override
    public Iterator iterator() {
        return EmptyIterator.INSTANCE;
    }

    @Override
    public int size() {
        return 0;
    }

}
