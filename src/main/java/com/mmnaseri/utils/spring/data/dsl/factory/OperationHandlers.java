package com.mmnaseri.utils.spring.data.dsl.factory;

import com.mmnaseri.utils.spring.data.proxy.NonDataOperationHandler;
import com.mmnaseri.utils.spring.data.proxy.impl.NonDataOperationInvocationHandler;

/**
 * Lets us define the operation handlers
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (4/8/16)
 */
@SuppressWarnings("WeakerAccess")
public interface OperationHandlers extends EventListener {

    /**
     * Sets the invocation handler used for handling non-data-related operations
     * @param invocationHandler    the invocation handler
     * @return the rest of the configuration
     */
    EventListener withOperationHandlers(NonDataOperationInvocationHandler invocationHandler);

    /**
     * Registers an operation handler
     * @param handler    the handler
     * @return the rest of the configuration
     */
    OperationHandlersAnd withOperationHandler(NonDataOperationHandler handler);

}
