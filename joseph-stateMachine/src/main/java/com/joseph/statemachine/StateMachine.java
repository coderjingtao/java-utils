package com.joseph.statemachine;

import com.joseph.statemachine.event.Event;
import com.joseph.statemachine.state.State;
import com.joseph.statemachine.transition.Transition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is an implementation of IStateMachine.
 * To get a handy state machine, client can extend this class
 * @author Joseph.Liu
 */
public class StateMachine implements IStateMachine {

    private final TransitionBox transitionBox;
    private State currentState;

    public StateMachine(State initialState, Transition[] transitions){
        currentState = initialState;
        this.transitionBox = new TransitionBox(transitions);
    }

    @Override
    public State getCurrentState() {
        return this.currentState;
    }

    @Override
    synchronized public State onEvent(Event event) throws UnsupportedOperationException {
        List<Transition> sourceTransitions = transitionBox.getSourceTransitions(this.currentState);
        return sourceTransitions
                .stream()
                .filter(transition -> Objects.equals(transition.getEvent(),event))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(String.format("Doesn't allow Event %s on State %s.",event.name(),currentState.name())))
                .getTarget();
    }

    @Override
    public List<Event> acceptableEvents() {
        List<Transition> sourceTransitions = transitionBox.getSourceTransitions(this.currentState);
        return sourceTransitions.stream().map(Transition::getEvent).collect(Collectors.toList());
    }

    @Override
    public boolean isAcceptable(Event event) {
        List<Transition> sourceTransitions = transitionBox.getSourceTransitions(this.currentState);
        return sourceTransitions.stream().anyMatch(transition -> Objects.equals(transition.getEvent(),event));
    }


    private static class TransitionBox{
        private final Map<State,List<Transition>> sourceTransitionsMap = new HashMap<>();
        private final Map<State,List<Transition>> targetTransitionsMap = new HashMap<>();
        private final Map<Event,List<Transition>> eventTransitionsMap = new HashMap<>();

        public TransitionBox(Transition[] transitions){

            verifyTransitions(transitions);

            for(Transition transition : transitions){

                //sourceTransitionsMap
                List<Transition> sourceTransitions = sourceTransitionsMap.computeIfAbsent(transition.getSource(), source -> new ArrayList<>());
                sourceTransitions.add(transition);

                //targetTransitionsMap
                List<Transition> targetTransitions = targetTransitionsMap.computeIfAbsent(transition.getTarget(), target -> new ArrayList<>());
                targetTransitions.add(transition);

                //eventTransitionsMap
                List<Transition> eventTransactions = eventTransitionsMap.computeIfAbsent(transition.getEvent(), event -> new ArrayList<>());
                eventTransactions.add(transition);
            }
        }

        public List<Transition> getSourceTransitions(State source){
            return sourceTransitionsMap.getOrDefault(source,new ArrayList<>());
        }

        public List<Transition> getTargetTransitions(State target){
            return targetTransitionsMap.getOrDefault(target,new ArrayList<>());
        }

        public List<Transition> getEventTransitions(Event event){
            return eventTransitionsMap.getOrDefault(event,new ArrayList<>());
        }

        /**
         * check if it is duplicate for (source state + event)
         * @param transitions all transitions configured on client side
         */
        private void verifyTransitions(Transition[] transitions){
            Set<String> sourceEventSet = new HashSet<>();
            for(Transition transition : transitions){
                String key = transition.getSource().name() + transition.getEvent().name();
                boolean addSuccess = sourceEventSet.add(key);
                if(!addSuccess){
                    throw new IllegalArgumentException(String.format("duplicate configured transition: source = %s event = %s",transition.getSource().name(),transition.getEvent().name()));
                }
            }
        }
    }
}
