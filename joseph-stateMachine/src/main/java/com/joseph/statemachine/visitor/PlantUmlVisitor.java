package com.joseph.statemachine.visitor;

import com.joseph.statemachine.StateMachine;
import com.joseph.statemachine.state.State;
import com.joseph.statemachine.transition.Transition;

/**
 * @author Joseph.Liu
 */
public class PlantUmlVisitor implements Visitor{
    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> stateMachine) {
        return "@startuml" + LF;
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> stateMachine) {
        return "@enduml";
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        for(Transition<?,?,?> transition : state.getAllTransitions()){
            sb.append(transition.getSource().getId())
                    .append(" --> ")
                    .append(transition.getTarget().getId())
                    .append(" : ")
                    .append(transition.getEvent())
                    .append(LF);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> state) {
        return "";
    }
}
