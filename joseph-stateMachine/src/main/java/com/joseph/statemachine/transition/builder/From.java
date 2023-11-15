package com.joseph.statemachine.transition.builder;

/**
 * @author Joseph.Liu
 */
public interface From<S, E, C> {

    To<S, E, C> to(S targetState);
}
