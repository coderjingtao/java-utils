package com.joseph.statemachine.transition.builder;

/**
 * @author Joseph.Liu
 */
public interface ExternalTransitionBuilder<S, E, C> {

    From<S, E, C> from(S sourceState);

}
