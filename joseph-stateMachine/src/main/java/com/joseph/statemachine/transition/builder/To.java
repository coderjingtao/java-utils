package com.joseph.statemachine.transition.builder;

/**
 * @author Joseph.Liu
 */
public interface To<S, E, C> {

    On<S, E, C> on(E event);
}
