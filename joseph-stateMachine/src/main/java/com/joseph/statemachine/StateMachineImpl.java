package com.joseph.statemachine;

import com.joseph.statemachine.builder.FailCallback;
import com.joseph.statemachine.state.State;
import com.joseph.statemachine.state.StateHelper;
import com.joseph.statemachine.transition.Transition;
import com.joseph.statemachine.visitor.ConsoleVisitor;
import com.joseph.statemachine.visitor.PlantUmlVisitor;
import com.joseph.statemachine.visitor.Visitor;

import java.util.List;
import java.util.Map;

/**
 * @author Joseph.Liu
 */
public class StateMachineImpl<S, E, C> implements StateMachine<S, E, C>{

    private String machineId;
    /**
     * the mapping of User-defined State and System-defined State
     * User-defined State named [StateId]
     * System-defined State named [State]
     */
    private final Map<S, State<S,E,C>> stateMap;

    private boolean ready;

    private FailCallback<S, E, C> failCallback;

    public StateMachineImpl(Map<S, State<S,E,C>> stateMap){
        this.stateMap = stateMap;
    }

    @Override
    public String getMachineId() {
        return machineId;
    }
    public void setMachineId(String machineId){
        this.machineId = machineId;
    }

    @Override
    public S fireEvent(S sourceStateId, E event, C context) {
        isReady();
        Transition<S, E, C> transition = getTransition(sourceStateId, event, context);
        if(transition == null){
            failCallback.onFail(sourceStateId,event,context);
            return sourceStateId;
        }
        return transition.transit(context,false).getId();
    }
    private Transition<S,E,C> getTransition(S sourceStateId, E event, C context){
        State<S, E, C> sourceState = StateHelper.getState(stateMap, sourceStateId);
        List<Transition<S, E, C>> eventTransitions = sourceState.getEventTransitions(event);
        if(eventTransitions == null || eventTransitions.size() == 0){
            System.err.printf("Event [%s] cannot find its transition%n",event.toString());
            return null;
        }
        Transition<S,E,C> result = null;
        for(Transition<S,E,C> transition : eventTransitions){
            if(transition.getCondition() == null){
                result = transition;
            }else if(transition.getCondition().isSatisfied(context)){
                result = transition;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean verify(S sourceStateId, E event) {
        isReady();
        State<S, E, C> sourceState = StateHelper.getState(stateMap, sourceStateId);
        List<Transition<S, E, C>> transitions = sourceState.getEventTransitions(event);
        return transitions != null && transitions.size() > 0;
    }

    private void isReady(){
        if(!ready){
            throw new IllegalStateException("State Machine is not built for use yet");
        }
    }
    public void setReady(boolean ready){
        this.ready = ready;
    }

    @Override
    public void showStateMachine() {
        Visitor visitor = new ConsoleVisitor();
        visit(visitor);
    }

    @Override
    public String generatePlantUml() {
        Visitor visitor = new PlantUmlVisitor();
        return visit(visitor);
    }

    @Override
    public String visit(Visitor visitor) {
        StringBuilder sb = new StringBuilder();
        sb.append(visitor.visitOnEntry(this));
        for(State<S, E, C> state : stateMap.values()){
            sb.append(state.visit(visitor));
        }
        sb.append(visitor.visitOnExit(this));
        return sb.toString();
    }

    public void setFailCallback(FailCallback<S, E, C> failCallback){
        this.failCallback = failCallback;
    }
}
