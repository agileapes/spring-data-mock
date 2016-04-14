package com.mmnaseri.utils.spring.data.proxy.impl.converters;

import com.mmnaseri.utils.spring.data.domain.impl.ImmutableInvocation;
import com.mmnaseri.utils.spring.data.sample.usecases.proxy.ReturnTypeSampleRepository;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (10/6/15)
 */
public class IteratorToIterableConverterTest {

    @Test
    public void testConvertingIteratorToIterable() throws Exception {
        final IteratorToIterableConverter converter = new IteratorToIterableConverter();
        final Object converted = converter.convert(new ImmutableInvocation(ReturnTypeSampleRepository.class.getMethod("findLong"), null), Arrays.asList(1, 2, 3, 4).iterator());
        assertThat(converted, is(notNullValue()));
        assertThat(converted, is(instanceOf(Iterable.class)));
        final Iterable iterable = (Iterable) converted;
        final Iterator iterator = iterable.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), equalTo((Object) 1));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), equalTo((Object) 2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), equalTo((Object) 3));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), equalTo((Object) 4));
        assertThat(iterator.hasNext(), is(false));
    }

}