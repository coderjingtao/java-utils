package com.joseph.statemachine.transition.builder;

import com.joseph.statemachine.condition.Condition;

/**
 * @author Joseph.Liu
 */
public interface On<S, E, C>{

    When<S, E, C> when(Condition<C> condition);
}
