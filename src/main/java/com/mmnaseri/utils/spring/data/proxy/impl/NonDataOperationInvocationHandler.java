package com.mmnaseri.utils.spring.data.proxy.impl;

import com.mmnaseri.utils.spring.data.error.UnknownDataOperationException;
import com.mmnaseri.utils.spring.data.proxy.NonDataOperationHandler;
import com.mmnaseri.utils.spring.data.proxy.impl.regular.EqualsNonDataOperationHandler;
import com.mmnaseri.utils.spring.data.proxy.impl.regular.HashCodeNonDataOperationHandler;
import com.mmnaseri.utils.spring.data.proxy.impl.regular.ToStringNonDataOperationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class will handle invocations that are strictly non-data-related.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (4/8/16)
 */
public class NonDataOperationInvocationHandler implements InvocationHandler {

    private final List<NonDataOperationHandler> handlers;

    public NonDataOperationInvocationHandler() {
        this(true);
    }

    public NonDataOperationInvocationHandler(boolean registerDefaults) {
        handlers = new LinkedList<>();
        if (registerDefaults) {
            handlers.add(new EqualsNonDataOperationHandler());
            handlers.add(new HashCodeNonDataOperationHandler());
            handlers.add(new ToStringNonDataOperationHandler());
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        for (NonDataOperationHandler handler : handlers) {
            if (handler.handles(proxy, method, args)) {
                return handler.invoke(proxy, args);
            }
        }
        throw new UnknownDataOperationException(method);
    }

    public void register(NonDataOperationHandler handler) {
        handlers.add(handler);
    }

    public List<NonDataOperationHandler> getHandlers() {
        return Collections.unmodifiableList(handlers);
    }

}
