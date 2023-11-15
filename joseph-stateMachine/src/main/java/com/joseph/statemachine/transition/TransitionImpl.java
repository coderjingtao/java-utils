package com.joseph.statemachine.transition;

import com.joseph.statemachine.action.Action;
import com.joseph.statemachine.condition.Condition;
import com.joseph.statemachine.state.State;

import java.util.Objects;

/**
 * @author Joseph.Liu
 */
public class TransitionImpl<S, E, C> implements Transition<S, E, C>{

    private State<S,E,C> source;
    private State<S,E,C> target;
    private E event;
    private Condition<C> condition;
    private Action<S,E,C> action;
    private TransitionType type = TransitionType.EXTERNAL;

    @Override
    public State<S, E, C> getSource() {
        return source;
    }

    @Override
    public void setSource(State<S, E, C> state) {
        this.source = state;
    }

    @Override
    public State<S, E, C> getTarget() {
        return target;
    }

    @Override
    public void setTarget(State<S, E, C> state) {
        this.target = state;
    }

    @Override
    public E getEvent() {
        return this.event;
    }

    @Override
    public void setEvent(E event) {
        this.event = event;
    }

    @Override
    public Condition<C> getCondition() {
        return this.condition;
    }

    @Override
    public void setCondition(Condition<C> condition) {
        this.condition = condition;
    }

    @Override
    public Action<S, E, C> getAction() {
        return this.action;
    }

    @Override
    public void setAction(Action<S, E, C> action) {
        this.action = action;
    }

    @Override
    public void setType(TransitionType type) {
        this.type = type;
    }

    @Override
    public State<S, E, C> transit(C context, boolean checkCondition) {
        this.verify();
        if(!checkCondition || condition == null || condition.isSatisfied(context)){
            if(action != null){
                action.execute(source.getId(), target.getId(), event, context);
            }
        }
        return target;
    }

    @Override
    public void verify() {
        if(type == TransitionType.INTERNAL && source != target){
            throw new IllegalStateException(String.format("Internal Transition source state %s and target state %s must be same.",source,target));
        }
    }

    @Override
    public String toString() {
        return type+" Transition : " + source + "-[" + event.toString() + "]->" + target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transition<?, ?, ?> that = (Transition<?, ?, ?>) o;

        if (!Objects.equals(source, that.getSource())){
            return false;
        }
        if (!Objects.equals(target, that.getTarget())){
            return false;
        }
        return event != null ? event.equals(that.getEvent()) : that.getEvent() == null;
    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }
}
