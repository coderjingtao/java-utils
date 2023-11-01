package com.joseph.statemachine.action;

/**
 * the action of a StateMachine
 * @author Joseph.Liu
 */
public interface Action {
    /**
     * Get the name of action
     * @return action name
     */
    String name();

    /**
     * execute the action
     * @return true = success
     */
    boolean doAction();
}
