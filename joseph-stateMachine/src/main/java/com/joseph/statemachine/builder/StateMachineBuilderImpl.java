package com.joseph.statemachine.builder;

import com.joseph.statemachine.StateMachine;
import com.joseph.statemachine.StateMachineFactory;
import com.joseph.statemachine.StateMachineImpl;
import com.joseph.statemachine.state.State;
import com.joseph.statemachine.transition.TransitionType;
import com.joseph.statemachine.transition.builder.ExternalTransitionBuilder;
import com.joseph.statemachine.transition.builder.InternalTransitionBuilder;
import com.joseph.statemachine.transition.builder.TransitionBuilderImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Joseph.Liu
 */
public class StateMachineBuilderImpl<S,E,C> implements StateMachineBuilder<S,E,C>{

    /**
     * the mapping of User-defined State and System-defined State
     * User-defined State named [StateId]
     * System-defined State named [State]
     */
    private final Map<S, State<S,E,C>> stateMap = new ConcurrentHashMap<>();
    private final StateMachineImpl<S,E,C> stateMachine = new StateMachineImpl<>(stateMap);

    @Override
    public ExternalTransitionBuilder<S, E, C> externalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public InternalTransitionBuilder<S, E, C> internalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.INTERNAL);
    }

    @Override
    public StateMachine<S, E, C> build(String machineId) {
        stateMachine.setMachineId(machineId);
        stateMachine.setReady(true);
        StateMachineFactory.register(stateMachine);
        return stateMachine;
    }
}
