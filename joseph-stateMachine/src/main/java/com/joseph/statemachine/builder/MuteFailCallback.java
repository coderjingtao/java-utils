package com.joseph.statemachine.builder;

/**
 * default fail callback for doing nothing
 * @author Joseph.Liu
 */
public class MuteFailCallback<S,E,C> implements FailCallback<S,E,C>{

    @Override
    public void onFail(S sourceState, E event, C context) {

    }
}
