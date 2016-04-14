package com.mmnaseri.utils.spring.data.query.impl;

import com.mmnaseri.utils.spring.data.domain.impl.ImmutableInvocation;
import com.mmnaseri.utils.spring.data.error.InvalidArgumentException;
import com.mmnaseri.utils.spring.data.query.Page;
import org.springframework.data.domain.PageRequest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (4/10/16)
 */
public class PageablePageParameterExtractorTest {

    @Test(expectedExceptions = InvalidArgumentException.class)
    public void testNullInvocation() throws Exception {
        final PageablePageParameterExtractor extractor = new PageablePageParameterExtractor(0);
        extractor.extract(null);
    }


    @Test(expectedExceptions = InvalidArgumentException.class)
    public void testPassingNullValue() throws Exception {
        final PageablePageParameterExtractor extractor = new PageablePageParameterExtractor(0);
        extractor.extract(new ImmutableInvocation(null, new Object[]{null}));
    }

    @Test(expectedExceptions = InvalidArgumentException.class)
    public void testPassingWrongType() throws Exception {
        final PageablePageParameterExtractor extractor = new PageablePageParameterExtractor(0);
        extractor.extract(new ImmutableInvocation(null, new Object[]{new Object()}));
    }

    @Test
    public void testPassingPageRequest() throws Exception {
        final PageablePageParameterExtractor extractor = new PageablePageParameterExtractor(0);
        final PageRequest pageRequest = new PageRequest(3, 7);
        final Page extracted = extractor.extract(new ImmutableInvocation(null, new Object[]{pageRequest}));
        assertThat(extracted, is(notNullValue()));
        assertThat(extracted.getPageNumber(), is(pageRequest.getPageNumber()));
        assertThat(extracted.getPageSize(), is(pageRequest.getPageSize()));
    }
    
}