package com.mmnaseri.utils.spring.data.dsl.mock;

import com.mmnaseri.utils.spring.data.domain.KeyGenerator;
import com.mmnaseri.utils.spring.data.domain.impl.key.*;
import org.springframework.core.GenericTypeResolver;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class will provide a key generator for the requested key type, based on the preset list of available
 * key generators.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (10/12/15)
 */
@SuppressWarnings("WeakerAccess")
class KeyGeneratorProvider {

    private final Map<Class<? extends Serializable>, List<Class<? extends KeyGenerator>>> generators;

    KeyGeneratorProvider() {
        final List<Class<? extends KeyGenerator>> discoveredKeyGenerators = getKeyGeneratorTypes();
        generators = new ConcurrentHashMap<>();
        for (Class<? extends KeyGenerator> generatorType : discoveredKeyGenerators) {
            final Class<?> keyType = GenericTypeResolver.resolveTypeArgument(generatorType, KeyGenerator.class);
            assert keyType != null;
            final Class<? extends Serializable> actualKeyType = keyType.asSubclass(Serializable.class);
            if (!generators.containsKey(actualKeyType)) {
                generators.put(actualKeyType, new CopyOnWriteArrayList<Class<? extends KeyGenerator>>());
            }
            generators.get(actualKeyType).add(generatorType);
        }
    }

    private List<Class<? extends KeyGenerator>> getKeyGeneratorTypes() {
        return Arrays.<Class<? extends KeyGenerator>>asList(
                RandomIntegerKeyGenerator.class,
                RandomLongKeyGenerator.class,
                SequentialIntegerKeyGenerator.class,
                SequentialLongKeyGenerator.class,
                ConfigurableSequentialIntegerKeyGenerator.class,
                ConfigurableSequentialLongKeyGenerator.class,
                UUIDKeyGenerator.class
        );
    }

    @SuppressWarnings("unchecked")
    private <S extends Serializable> List<Class<? extends KeyGenerator<S>>> getKeyGenerators(Class<S> keyType) {
        final LinkedList<Class<? extends KeyGenerator<S>>> keyGenerators = new LinkedList<>();
        if (generators.containsKey(keyType)) {
            final List<Class<? extends KeyGenerator>> classes = generators.get(keyType);
            for (Class<? extends KeyGenerator> type : classes) {
                keyGenerators.add((Class<? extends KeyGenerator<S>>) type);
            }
        }
        for (Class<? extends Serializable> generatorKeyType : generators.keySet()) {
            if (keyType.isAssignableFrom(generatorKeyType)) {
                final List<Class<? extends KeyGenerator>> classes = generators.get(generatorKeyType);
                for (Class<? extends KeyGenerator> type : classes) {
                    keyGenerators.add((Class<? extends KeyGenerator<S>>) type);
                }
            }
        }
        return keyGenerators;
    }

    /**
     * Provides a key generator for the specified key type. This is to automate the process of getting
     * a key generator, when no alternative is provided by the user.
     * @param keyType    the type of keys for which a generator is required
     * @param <S>        the type of keys the generator will provide
     * @return the generator or {@literal null} if none could be found to satisfy the key type
     */
    public <S extends Serializable> Class<? extends KeyGenerator<S>> getKeyGenerator(Class<S> keyType) {
        final List<Class<? extends KeyGenerator<S>>> generators = getKeyGenerators(keyType);
        return generators.isEmpty() ? null : generators.get(0);
    }

}
