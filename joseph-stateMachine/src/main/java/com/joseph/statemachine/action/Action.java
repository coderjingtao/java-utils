package com.joseph.statemachine.action;

/**
 * the action of a StateMachine
 * @author Joseph.Liu
 */
public interface Action<S, E, C> {
    /**
     * execute the action
     */
    void execute(S from, S to, E event, C context);
}
