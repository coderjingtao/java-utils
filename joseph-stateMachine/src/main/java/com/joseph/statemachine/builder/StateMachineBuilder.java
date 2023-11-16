package com.joseph.statemachine.builder;

import com.joseph.statemachine.StateMachine;
import com.joseph.statemachine.transition.builder.ExternalTransitionBuilder;
import com.joseph.statemachine.transition.builder.InternalTransitionBuilder;

/**
 * @author Joseph.Liu
 */
public interface StateMachineBuilder<S, E, C> {

    ExternalTransitionBuilder<S, E, C> externalTransition();

    InternalTransitionBuilder<S, E, C> internalTransition();

    void setFailCallback(FailCallback<S,E,C> callback);

    StateMachine<S, E, C> build(String machineId);
}
