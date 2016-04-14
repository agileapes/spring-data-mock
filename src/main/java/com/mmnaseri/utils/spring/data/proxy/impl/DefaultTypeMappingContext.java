package com.mmnaseri.utils.spring.data.proxy.impl;

import com.mmnaseri.utils.spring.data.error.RepositoryDefinitionException;
import com.mmnaseri.utils.spring.data.proxy.TypeMapping;
import com.mmnaseri.utils.spring.data.proxy.TypeMappingContext;
import com.mmnaseri.utils.spring.data.repository.DefaultCrudRepository;
import com.mmnaseri.utils.spring.data.repository.DefaultGemfireRepository;
import com.mmnaseri.utils.spring.data.repository.DefaultJpaRepository;
import com.mmnaseri.utils.spring.data.repository.DefaultPagingAndSortingRepository;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>This is the default type mapping context that is also capable of registering the default mappings
 * for the interfaces provided through Spring Data.</p>
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (10/8/15)
 */
@SuppressWarnings("WeakerAccess")
public class DefaultTypeMappingContext implements TypeMappingContext {

    private final TypeMappingContext parent;
    private ConcurrentMap<Class<?>, List<Class<?>>> mappings = new ConcurrentHashMap<>();

    /**
     * Instantiates the context and registers all the default converters
     */
    public DefaultTypeMappingContext() {
        this(true);
    }

    /**
     * Instantiates the context
     * @param registerDefaults    whether or not the default mappings should be registered.
     */
    public DefaultTypeMappingContext(boolean registerDefaults) {
        this(null);
        if (registerDefaults) {
            if (ClassUtils.isPresent("org.springframework.data.gemfire.repository.GemfireRepository", ClassUtils.getDefaultClassLoader())) {
                register(Object.class, DefaultGemfireRepository.class);
            }
            if (ClassUtils.isPresent("org.springframework.data.jpa.repository.JpaRepository", ClassUtils.getDefaultClassLoader())) {
                register(Object.class, DefaultJpaRepository.class);
            }
            register(Object.class, DefaultPagingAndSortingRepository.class);
            register(Object.class, DefaultCrudRepository.class);
        }
    }

    public DefaultTypeMappingContext(TypeMappingContext parent) {
        this.parent = parent;
    }

    @Override
    public void register(Class<?> repositoryType, Class<?> implementation) {
        if (Modifier.isAbstract(implementation.getModifiers()) || Modifier.isInterface(implementation.getModifiers())) {
            throw new RepositoryDefinitionException(repositoryType, "Cannot bind a non-concrete class as an implementation for a non-concrete class");
        }
        mappings.putIfAbsent(repositoryType, new LinkedList<Class<?>>());
        mappings.get(repositoryType).add(implementation);
    }

    @Override
    public List<Class<?>> getImplementations(Class<?> repositoryType) {
        final List<Class<?>> classes = new LinkedList<>();
        for (Class<?> repositorySuperType : mappings.keySet()) {
            if (repositorySuperType.isAssignableFrom(repositoryType)) {
                classes.addAll(mappings.get(repositorySuperType));
            }
        }
        Collections.sort(classes, AnnotationAwareOrderComparator.INSTANCE);
        if (parent != null) {
            classes.addAll(parent.getImplementations(repositoryType));
        }
        return classes;
    }

    @Override
    public List<TypeMapping<?>> getMappings(Class<?> repositoryType) {
        final List<TypeMapping<?>> typeMappings = new LinkedList<>();
        final List<Class<?>> implementations = getImplementations(repositoryType);
        for (Class<?> implementation : implementations) {
            final Object instance;
            try {
                instance = implementation.newInstance();
            } catch (InstantiationException e) {
                throw new RepositoryDefinitionException(repositoryType, "Failed to instantiate an object of type " + implementation, e);
            } catch (IllegalAccessException e) {
                throw new RepositoryDefinitionException(repositoryType, "Failed to access the constructor for " + implementation, e);
            } catch (Exception e) {
                throw new RepositoryDefinitionException(repositoryType, "Constructor threw an exception " + implementation, e);
            }
            //noinspection unchecked
            typeMappings.add(new ImmutableTypeMapping<>((Class<Object>)implementation, instance));
        }
        return typeMappings;
    }

}
