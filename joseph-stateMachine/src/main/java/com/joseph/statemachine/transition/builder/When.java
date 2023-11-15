package com.joseph.statemachine.transition.builder;

import com.joseph.statemachine.action.Action;

/**
 * @author Joseph.Liu
 */
public interface When<S, E, C> {
    /**
     *
     * @param action the action in a transition
     */
    void perform(Action<S, E, C> action);
}
