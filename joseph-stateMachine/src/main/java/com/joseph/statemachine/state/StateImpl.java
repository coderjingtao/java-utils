package com.joseph.statemachine.state;

import com.joseph.statemachine.transition.EventTransitions;
import com.joseph.statemachine.transition.Transition;
import com.joseph.statemachine.transition.TransitionImpl;
import com.joseph.statemachine.transition.TransitionType;
import com.joseph.statemachine.visitor.Visitor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Joseph.Liu
 */
public class StateImpl<S, E, C> implements State<S, E, C>{

    protected final S stateId;
    private final EventTransitions<S,E,C> eventTransitions = new EventTransitions<>();

    StateImpl(S stateId){
        this.stateId = stateId;
    }

    @Override
    public S getId() {
        return stateId;
    }

    @Override
    public Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType transitionType) {
        Transition<S,E,C> newTransition = new TransitionImpl<>();
        newTransition.setSource(this);
        newTransition.setTarget(target);
        newTransition.setEvent(event);
        newTransition.setType(transitionType);
        eventTransitions.put(event,newTransition);
        return newTransition;
    }

    @Override
    public List<Transition<S, E, C>> getEventTransitions(E event) {
        return eventTransitions.get(event);
    }

    @Override
    public Collection<Transition<S, E, C>> getAllTransitions() {
        return eventTransitions.allTransitions();
    }

    @Override
    public String toString() {
        return stateId.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof State){
            State other = (State) o;
            return Objects.equals(other.getId(), getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return stateId != null ? stateId.hashCode() : 0;
    }

    @Override
    public String visit(Visitor visitor) {
        String entry = visitor.visitOnEntry(this);
        String exit = visitor.visitOnExit(this);
        return entry + exit;
    }
}
