package com.joseph.statemachine.transition;

import com.joseph.statemachine.action.Action;
import com.joseph.statemachine.condition.Condition;
import com.joseph.statemachine.state.State;

/**
 * @author Joseph.Liu
 */
public interface Transition<S, E, C> {

    State<S,E,C> getSource();

    void setSource(State<S,E,C> state);

    State<S,E,C> getTarget();

    void setTarget(State<S,E,C> state);

    E getEvent();

    void setEvent(E event);

    Condition<C> getCondition();

    void setCondition(Condition<C> condition);

    Action<S, E, C> getAction();

    void setAction(Action<S, E, C> action);

    //---------------TransitionType -----------------

    void setType(TransitionType type);

    /**
     * do transition from the source state to the target state
     * @param context context
     * @param checkCondition if it needs to check condition
     * @return target state
     */
    State<S,E,C> transit(C context, boolean checkCondition);

    /**
     * verify the correctness of the transition
     */
    void verify();
}
