package com.joseph.statemachine.visitor;

import com.joseph.statemachine.StateMachine;
import com.joseph.statemachine.state.State;
import com.joseph.statemachine.transition.Transition;

/**
 * @author Joseph.Liu
 */
public class ConsoleVisitor implements Visitor{
    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> stateMachine) {
        String entry = "-----StateMachine: ["+stateMachine.getMachineId()+"]-------";
        System.out.println(entry);
        return entry;
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> stateMachine) {
        String exit =  "-----------------------------------------------";
        System.out.println(exit);
        return exit;
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        String stateStr = "State: [" + state.getId() + "]";
        sb.append(stateStr).append(LF);
        System.out.println(stateStr);
        for(Transition<?,?,?> transition : state.getAllTransitions()){
            String transitionStr = "  Transition: [" + transition + "]";
            sb.append(transitionStr).append(LF);
            System.out.println(transitionStr);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> state) {
        return "";
    }
}
