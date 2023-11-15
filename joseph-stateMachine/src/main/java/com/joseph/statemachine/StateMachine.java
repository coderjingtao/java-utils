package com.joseph.statemachine;

import com.joseph.statemachine.visitor.Visitable;

/**
 * A state machine
 * @author Joseph.Liu
 */
public interface StateMachine<S,E,C> extends Visitable {

    /**
     * Get the current state
     * @return current state
     */
    String getMachineId();
    /**
     * execute the event under the source state
     * @param sourceState source state
     * @param event event to be executed
     * @param context context data
     * @return the target state
     */
    S fireEvent(S sourceState, E event, C context);
    /**
     * verify if the event is acceptable and fired for the source state
     * @param sourceState source state
     * @param event an event
     * @return true if the event can be fired from the source state
     */
    boolean verify(S sourceState, E event);

    /**
     * display the structure of the state machine
     */
    void showStateMachine();
}
