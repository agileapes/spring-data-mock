package com.mmnaseri.utils.spring.data.repository;

import com.mmnaseri.utils.spring.data.error.EntityMissingKeyException;
import com.mmnaseri.utils.spring.data.tools.PropertyUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>This class will provide implementations for the methods introduced by the Spring framework through
 * {@link org.springframework.data.repository.CrudRepository}.</p>
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (10/6/15)
 */
@SuppressWarnings({"unchecked", "WeakerAccess"})
public class DefaultCrudRepository extends CrudRepositorySupport {

    /**
     * Saves all the given entities
     * @param entities    entities to save (insert or update)
     * @return saved entities
     */
    public Iterable<Object> save(Iterable entities) {
        final List<Object> list = new LinkedList<>();
        for (Object entity : entities) {
            list.add(save(entity));
        }
        return list;
    }

    /**
     * Finds the entity that was saved with this key, or returns {@literal null}
     * @param key    the key
     * @return the entity
     */
    public Object findOne(Serializable key) {
        return getDataStore().retrieve(key);
    }

    /**
     * Checks whether the given key represents an entity in the data store
     * @param key    the key
     * @return {@literal true} if the key is valid
     */
    public boolean exists(Serializable key) {
        return getDataStore().hasKey(key);
    }

    /**
     * Finds all the entities that match the given set of ids
     * @param ids    ids to look for
     * @return entities that matched the ids.
     */
    public Iterable findAll(Iterable ids) {
        final List entities = new LinkedList();
        for (Object id : ids) {
            final Object found = findOne((Serializable) id);
            if (found != null) {
                entities.add(found);
            }
        }
        return entities;
    }

    /**
     * Deletes the entity with the given id and returns the actual entity that was just deleted.
     * @param id    the id
     * @return the entity that was deleted or {@literal null} if it wasn't found
     */
    public Object delete(Serializable id) {
        final Object retrieved = getDataStore().retrieve(id);
        getDataStore().delete(id);
        return retrieved;
    }

    /**
     * Deletes the entity matching this entity's key from the data store
     * @param entity    the entity
     * @return the deleted entity
     * @throws EntityMissingKeyException if the passed entity doesn't have a key
     */
    public Object delete(Object entity) {
        final Object key = PropertyUtils.getPropertyValue(entity, getRepositoryMetadata().getIdentifierProperty());
        if (key == null) {
            throw new EntityMissingKeyException(getRepositoryMetadata().getEntityType(), getRepositoryMetadata().getIdentifierProperty());
        }
        return delete((Serializable) key);
    }

    /**
     * Deletes all specified <em>entities</em> from the data store.
     * @param entities    the entities to delete
     * @return the entities that were actually deleted
     */
    public Iterable delete(Iterable entities) {
        final List list = new LinkedList();
        for (Object entity : entities) {
            final Object deleted = delete(entity);
            if (deleted != null) {
                list.add(deleted);
            }
        }
        return list;
    }

    /**
     * Deletes everything from the data store
     * @return all the entities that were removed
     */
    public Iterable deleteAll() {
        final List list = new LinkedList();
        for (Object key : getDataStore().keys()) {
            final Object deleted = delete(((Serializable) key));
            if (deleted != null) {
                list.add(deleted);
            }
        }
        return list;
    }

}
