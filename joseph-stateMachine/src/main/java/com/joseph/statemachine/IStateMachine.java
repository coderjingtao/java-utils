package com.joseph.statemachine;

import com.joseph.statemachine.event.Event;
import com.joseph.statemachine.state.State;

import java.util.List;

/**
 * The interface of a state machine
 * @author Joseph.Liu
 */
public interface IStateMachine {

    /**
     * Get the current state
     * @return current state
     */
    State getCurrentState();

    /**
     * execute the event under the current state
     * @param event event to be executed
     * @return the next state after completing the event
     * @throws UnsupportedOperationException unsupported event
     */
    State onEvent(Event event) throws UnsupportedOperationException;

    /**
     * get all acceptable event under the current state
     * @return the list of events
     */
    List<Event> acceptableEvents();

    /**
     * judge if the event is acceptable for the current state, but the event won't be executed.
     * @param event an event
     * @return true if the event can be performed under the current state
     */
    boolean isAcceptable(Event event);
}
