package com.joseph.statemachine.visitor;

import com.joseph.statemachine.StateMachine;
import com.joseph.statemachine.state.State;

/**
 * @author Joseph.Liu
 */
public interface Visitor {

    char LF = '\n';

    String visitOnEntry(StateMachine<?,?,?> stateMachine);

    String visitOnExit(StateMachine<?,?,?> stateMachine);

    String visitOnEntry(State<?,?,?> state);

    String visitOnExit(State<?,?,?> state);
}
