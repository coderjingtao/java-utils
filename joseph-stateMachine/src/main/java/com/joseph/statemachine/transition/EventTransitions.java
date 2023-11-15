package com.joseph.statemachine.transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * One event could be mapping many transitions,
 * it could trigger many transitions as well
 * @author Joseph.Liu
 */
public class EventTransitions<S, E, C> {

    private Map<E, List<Transition<S,E,C>>> eventTransitions;

    public EventTransitions(){
        eventTransitions = new HashMap<>();
    }

    public void put(E event, Transition<S, E, C> transition){
        if(eventTransitions.get(event) == null){
            List<Transition<S,E,C>> transitions = new ArrayList<>();
            transitions.add(transition);
            eventTransitions.put(event,transitions);
        }else{
            List<Transition<S,E,C>> transitions = eventTransitions.get(event);
            validate(transitions,transition);
            transitions.add(transition);
        }
    }

    private void validate(List<Transition<S,E,C>> existingTransitions, Transition<S,E,C> newTransition){
        for(Transition<S,E,C> transition : existingTransitions){
            if(transition.equals(newTransition)){
                throw new IllegalStateException(newTransition + " already exists.");
            }
        }
    }

    public List<Transition<S,E,C>> get(E event){
        return eventTransitions.get(event);
    }

    public List<Transition<S,E,C>> allTransitions(){
        List<Transition<S,E,C>> result = new ArrayList<>();
        for(List<Transition<S,E,C>> transitions : eventTransitions.values()){
            result.addAll(transitions);
        }
        return result;
    }
}
