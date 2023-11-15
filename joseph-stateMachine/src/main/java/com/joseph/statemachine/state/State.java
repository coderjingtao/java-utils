package com.joseph.statemachine.state;

import com.joseph.statemachine.transition.Transition;
import com.joseph.statemachine.transition.TransitionType;
import com.joseph.statemachine.visitor.Visitable;

import java.util.Collection;
import java.util.List;

/**
 * the State of a StateMachine
 * @author Joseph.Liu
 */
public interface State<S, E, C> extends Visitable {
    /**
     * Get the id of the state
     * @return
     */
    S getId();

    Transition<S, E, C> addTransition(E event, State<S,E,C> target, TransitionType transitionType);

    List<Transition<S,E,C>> getEventTransitions(E event);

    Collection<Transition<S,E,C>> getAllTransitions();

}
