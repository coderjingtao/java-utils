package com.joseph.statemachine.condition;

/**
 * @author Joseph.Liu
 */
public interface Condition<C> {

    boolean isSatisfied(C context);

    default String name(){
        return this.getClass().getSimpleName();
    }
}
