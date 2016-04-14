package com.mmnaseri.utils.spring.data.query.impl;

import com.mmnaseri.utils.spring.data.error.DuplicateFunctionException;
import com.mmnaseri.utils.spring.data.error.FunctionNotFoundException;
import com.mmnaseri.utils.spring.data.query.DataFunction;
import com.mmnaseri.utils.spring.data.query.DataFunctionRegistry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class provides support for registering data functions. Also, it comes with the option to register
 * the default functions out-of-the-box.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/29/15)
 */
@SuppressWarnings("WeakerAccess")
public class DefaultDataFunctionRegistry implements DataFunctionRegistry {

    private final Map<String, DataFunction<?>> functions;

    public DefaultDataFunctionRegistry() {
        this(true);
    }

    public DefaultDataFunctionRegistry(boolean registerDefaults) {
        functions = new ConcurrentHashMap<>();
        if (registerDefaults) {
            register("count", new CountDataFunction());
            register("delete", new DeleteDataFunction());
        }
    }

    @Override
    public void register(String name, DataFunction<?> function) {
        if (functions.containsKey(name)) {
            throw new DuplicateFunctionException(name);
        }
        functions.put(name, function);
    }

    @Override
    public DataFunction<?> getFunction(String name) {
        if (!functions.containsKey(name)) {
            throw new FunctionNotFoundException(name);
        }
        return functions.get(name);
    }

    @Override
    public Set<String> getFunctions() {
        return functions.keySet();
    }

}
