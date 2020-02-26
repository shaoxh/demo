package com.example.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Yugandhar Gangu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class TriggerTime {
    private static final TriggerTime TRIGGER_TIME = new TriggerTime();

    /**
     * @return TriggerTime
     */
    public static TriggerTime getInstance() {
        return TRIGGER_TIME;
    }

    private final String[] trigger_times = new String[]{"BEFORE", "AFTER"};

    /**
     *
     */
    private TriggerTime() {

    }

    /**
     * @return String[]
     */
    public String[] getTriggerTimes() {
        return this.trigger_times;
    }
}
