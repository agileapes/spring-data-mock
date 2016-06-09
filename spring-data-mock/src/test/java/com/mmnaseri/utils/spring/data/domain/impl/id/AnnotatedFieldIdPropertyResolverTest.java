package com.mmnaseri.utils.spring.data.domain.impl.id;

import com.mmnaseri.utils.spring.data.domain.IdPropertyResolver;
import com.mmnaseri.utils.spring.data.sample.models.EntityWithAnnotatedIdField;
import com.mmnaseri.utils.spring.data.sample.models.EntityWithAnnotatedIdFieldFromJPA;
import com.mmnaseri.utils.spring.data.sample.models.EntityWithMultipleAnnotatedFields;
import com.mmnaseri.utils.spring.data.sample.models.EntityWithoutAnnotatedFields;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/29/15)
 */
@SuppressWarnings("unused")
public class AnnotatedFieldIdPropertyResolverTest extends BaseRepeatableIdPropertyResolverTest {

    @Override
    protected IdPropertyResolver getIdPropertyResolver() {
        return new AnnotatedFieldIdPropertyResolver();
    }

    @Override
    protected Class<?> properEntity() {
        return EntityWithAnnotatedIdField.class;
    }

    @Override
    protected Class<?> entityWithNoProperty() {
        return EntityWithoutAnnotatedFields.class;
    }

    @Override
    protected Class<?> entityWithMultipleProperties() {
        return EntityWithMultipleAnnotatedFields.class;
    }

    /**
     * Regression test for https://github.com/mmnaseri/spring-data-mock/issues/55
     * @throws Exception
     */
    @Test
    public void testResolvingIdPropertyWhenIdAnnotationOnFieldIsFromJPA() throws Exception {
        final String property = getIdPropertyResolver().resolve(EntityWithAnnotatedIdFieldFromJPA.class, Long.class);
        assertThat(property, is(notNullValue()));
        assertThat(property, is("customIdProperty"));
    }

}