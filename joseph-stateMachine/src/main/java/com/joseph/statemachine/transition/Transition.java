package com.joseph.statemachine.transition;

import com.joseph.statemachine.event.Event;
import com.joseph.statemachine.state.State;

/**
 * the State Transition of a StateMachine via an Event
 * @author Joseph.Liu
 */
public interface Transition {
    /**
     * get the source state of a transition
     * @return source state
     */
    State getSource();

    /**
     * get the event of a transition
     * @return event
     */
    Event getEvent();

    /**
     * get the target state of a transition
     * @return target state
     */
    State getTarget();
}
