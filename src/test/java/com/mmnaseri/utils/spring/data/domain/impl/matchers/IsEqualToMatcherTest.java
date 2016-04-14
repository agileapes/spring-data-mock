package com.mmnaseri.utils.spring.data.domain.impl.matchers;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (9/30/15)
 */
public class IsEqualToMatcherTest {

    @Test
    public void testWhenBothAreNull() throws Exception {
        assertThat(new IsEqualToMatcher().matches(null, null, new Object[]{null}), is(true));
    }

    @Test
    public void testWhenTheyAreTheSameInstance() throws Exception {
        final Object obj = new Object();
        assertThat(new IsEqualToMatcher().matches(null, obj, obj), is(true));
    }

    @Test
    public void testWhenTheyHaveTheSameValue() throws Exception {
        assertThat(new IsEqualToMatcher().matches(null, 1, 1), is(true));
    }

    @Test
    public void testWhenTheyDiffer() throws Exception {
        assertThat(new IsEqualToMatcher().matches(null, new Object(), new Object()), is(false));
    }

    @Test
    public void testWhenTheyHaveDifferentValues() throws Exception {
        assertThat(new IsEqualToMatcher().matches(null, 1, 2), is(false));
    }

}