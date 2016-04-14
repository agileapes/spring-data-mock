package com.mmnaseri.utils.spring.data.store.impl;

import com.mmnaseri.utils.spring.data.error.DataStoreException;
import com.mmnaseri.utils.spring.data.store.DataStore;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This is the default, most basic implementation provided for a data store that stores entities in an in-memory
 * {@link java.util.Map map} by mapping entity keys to entities.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/17/15)
 */
public class MemoryDataStore<K extends Serializable, E> implements DataStore<K, E> {
    
    private final ConcurrentMap<K, E> store = new ConcurrentHashMap<>();
    private final Class<E> entityType;

    public MemoryDataStore(Class<E> entityType) {
        this.entityType = entityType;
    }

    @Override
    public boolean hasKey(K key) {
        return store.containsKey(key);
    }

    @Override
    public boolean save(K key, E entity) {
        if (key == null) {
            throw new DataStoreException(entityType, "Cannot save an entity with a null key");
        } else if (entity == null) {
            throw new DataStoreException(entityType, "Cannot save a null entity");
        }
        return store.put(key, entity) == null;
    }

    @Override
    public boolean delete(K key) {
        if (key == null) {
            throw new DataStoreException(entityType, "Cannot delete an entity with a null key");
        }
        if (store.containsKey(key)) {
            store.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public E retrieve(K key) {
        if (key == null) {
            throw new DataStoreException(entityType, "Cannot retrieve an entity with a null key");
        }
        if (store.containsKey(key)) {
            return store.get(key);
        }
        return null;
    }

    @Override
    public Collection<K> keys() {
        return new LinkedList<>(store.keySet());
    }

    @Override
    public synchronized Collection<E> retrieveAll() {
        return new LinkedList<>(store.values());
    }

    @Override
    public Class<E> getEntityType() {
        return entityType;
    }

}
