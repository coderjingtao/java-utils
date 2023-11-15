package com.joseph.statemachine.transition.builder;

import com.joseph.statemachine.action.Action;
import com.joseph.statemachine.condition.Condition;
import com.joseph.statemachine.state.State;
import com.joseph.statemachine.state.StateHelper;
import com.joseph.statemachine.transition.Transition;
import com.joseph.statemachine.transition.TransitionType;

import java.util.Map;

/**
 * @author Joseph.Liu
 */
public class TransitionBuilderImpl<S,E,C> implements ExternalTransitionBuilder<S,E,C>,
        InternalTransitionBuilder<S,E,C>,
        From<S,E,C>, On<S,E,C>,
        To<S,E,C>, When<S,E,C> {

    final Map<S, State<S,E,C>> stateMap;
    final TransitionType transitionType;

    private State<S,E,C> source;
    private State<S,E,C> target;
    private Transition<S,E,C> transition;

    public TransitionBuilderImpl(Map<S, State<S,E,C>> stateMap,TransitionType transitionType){
        this.stateMap = stateMap;
        this.transitionType = transitionType;
    }

    @Override
    public From<S, E, C> from(S sourceState) {
        source = StateHelper.getState(stateMap, sourceState);
        return this;
    }

    @Override
    public To<S, E, C> to(S targetState) {
        target = StateHelper.getState(stateMap, targetState);
        return this;
    }

    @Override
    public To<S, E, C> within(S state) {
        source = target = StateHelper.getState(stateMap,state);
        return this;
    }

    @Override
    public When<S, E, C> when(Condition<C> condition) {
        transition.setCondition(condition);
        return this;
    }

    @Override
    public On<S, E, C> on(E event) {
        transition = source.addTransition(event, target, transitionType);
        return this;
    }

    @Override
    public void perform(Action<S, E, C> action) {
        transition.setAction(action);
    }
}
