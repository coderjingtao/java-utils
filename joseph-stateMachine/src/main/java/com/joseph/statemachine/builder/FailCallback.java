package com.joseph.statemachine.builder;

/**
 * @author Joseph.Liu
 */
public interface FailCallback<S, E, C> {

    void onFail(S sourceState, E event, C context);
}
