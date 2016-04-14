package com.mmnaseri.utils.spring.data.sample.mocks;

import com.mmnaseri.utils.spring.data.store.DataStoreEvent;

/**
 * @author Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (4/10/16)
 */
public class EventTrigger {

    private final Long timestamp;
    private final DataStoreEvent event;

    public EventTrigger(Long timestamp, DataStoreEvent event) {
        this.timestamp = timestamp;
        this.event = event;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public DataStoreEvent getEvent() {
        return event;
    }
}
