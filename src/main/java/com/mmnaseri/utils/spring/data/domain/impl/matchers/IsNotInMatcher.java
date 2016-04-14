package com.mmnaseri.utils.spring.data.domain.impl.matchers;

import com.mmnaseri.utils.spring.data.domain.Parameter;

import java.util.Collection;

/**
 * This matcher checks whether or not the argument passed to the query method (the collection) contains the
 * value on the object itself and fails the check if it does.
 *
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/29/15)
 */
public class IsNotInMatcher extends AbstractCollectionMatcher {

    @Override
    protected boolean matches(Parameter parameter, Object actual, Collection collection) {
        return !collection.contains(actual);
    }

}
