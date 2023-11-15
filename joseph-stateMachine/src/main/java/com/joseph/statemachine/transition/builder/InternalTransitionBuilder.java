package com.joseph.statemachine.transition.builder;

/**
 * @author Joseph.Liu
 */
public interface InternalTransitionBuilder<S, E, C> {

    To<S, E, C> within(S state);
}
