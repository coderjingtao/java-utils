package com.joseph.statemachine.event;

/**
 * the Event of a StateMachine
 * @author Joseph.Liu
 */
public interface Event {
    /**
     * Return the name of event
     * @return the name of event
     */
    String name();

    /**
     * execute the event
     */
    void execute();
}
