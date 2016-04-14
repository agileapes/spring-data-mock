package com.mmnaseri.utils.spring.data.domain.impl;

import com.mmnaseri.utils.spring.data.domain.Invocation;

import java.lang.reflect.Method;

/**
 * This is an immutable invocation.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/23/15)
 */
public class ImmutableInvocation implements Invocation {

    private final Method method;
    private final Object[] arguments;

    public ImmutableInvocation(Method method, Object[] arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }
}
