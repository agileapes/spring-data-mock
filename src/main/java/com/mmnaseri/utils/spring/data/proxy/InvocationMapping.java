package com.mmnaseri.utils.spring.data.proxy;

import com.mmnaseri.utils.spring.data.store.DataStoreOperation;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <p>This interface is used to represent data about a single invocation mapping, consisting of the method and
 * the data store operation to which it is bound.</p>
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/29/15)
 */
public interface InvocationMapping<K extends Serializable, E> {

    /**
     * @return the method
     */
    Method getMethod();

    /**
     * @return the data store operation
     */
    DataStoreOperation<?, K, E> getOperation();

}
