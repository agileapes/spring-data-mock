package com.mmnaseri.utils.spring.data.store.impl;

import com.mmnaseri.utils.spring.data.sample.mocks.AllCatchingEventListener;
import com.mmnaseri.utils.spring.data.sample.mocks.SpyingEventListener;
import com.mmnaseri.utils.spring.data.store.DataStoreEvent;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (4/9/16)
 */
public class SmartDataStoreEventListenerTest {

    @Test
    public void testEventTypeRecognition() throws Exception {
        final SmartDataStoreEventListener<DataStoreEvent> listener = new SmartDataStoreEventListener<>(new AllCatchingEventListener());
        assertThat(listener.getEventType(), is(notNullValue()));
        assertThat(listener.getEventType(), is(equalTo(DataStoreEvent.class)));
    }

    @Test
    public void testEventDelegation() throws Exception {
        final SpyingEventListener<DataStoreEvent> spy = new AllCatchingEventListener();
        final SmartDataStoreEventListener<DataStoreEvent> listener = new SmartDataStoreEventListener<>(spy);
        final AfterDeleteDataStoreEvent first = new AfterDeleteDataStoreEvent(null, null, null);
        final BeforeInsertDataStoreEvent second = new BeforeInsertDataStoreEvent(null, null, null);
        listener.onEvent(first);
        listener.onEvent(second);
        final List<DataStoreEvent> events = spy.getEvents();
        assertThat(events, hasSize(2));
        assertThat(events.get(0), Matchers.<DataStoreEvent>is(first));
        assertThat(events.get(1), Matchers.<DataStoreEvent>is(second));
    }
    
}