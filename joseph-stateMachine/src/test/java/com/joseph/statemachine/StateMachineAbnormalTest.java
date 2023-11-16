package com.joseph.statemachine;

import com.joseph.statemachine.action.Action;
import com.joseph.statemachine.builder.StateMachineBuilder;
import com.joseph.statemachine.builder.StateMachineBuilderFactory;
import com.joseph.statemachine.condition.Condition;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Joseph.Liu
 */
public class StateMachineAbnormalTest {

    static String machineId = "JOSEPH_TEST_ABNORMAL_MACHINE";

    @Test
    public void notMeetConditionTest(){
        StateMachineBuilder<StateMachineTest.OrderState, StateMachineTest.OrderEvent, StateMachineTest.OrderContext> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(StateMachineTest.OrderState.PENDING_FULFILL)
                .to(StateMachineTest.OrderState.CLOSED)
                .on(StateMachineTest.OrderEvent.CLOSE_SO)
                .when(notPassCondition())
                .perform(doOrderAction());

        StateMachine<StateMachineTest.OrderState, StateMachineTest.OrderEvent, StateMachineTest.OrderContext> machine = builder.build(machineId);
        StateMachineTest.OrderState orderState = machine.fireEvent(StateMachineTest.OrderState.PENDING_FULFILL, StateMachineTest.OrderEvent.CLOSE_SO, new StateMachineTest.OrderContext());
        Assert.assertEquals(orderState, StateMachineTest.OrderState.PENDING_FULFILL);
    }

    @Test(expected = IllegalStateException.class)
    public void duplicateTransitionTest(){
        StateMachineBuilder<StateMachineTest.OrderState, StateMachineTest.OrderEvent, StateMachineTest.OrderContext> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(StateMachineTest.OrderState.INIT)
                .to(StateMachineTest.OrderState.PENDING_FULFILL)
                .on(StateMachineTest.OrderEvent.CREATE_SO)
                .when(passCondition())
                .perform(doOrderAction());
        builder.externalTransition()
                .from(StateMachineTest.OrderState.INIT)
                .to(StateMachineTest.OrderState.PENDING_FULFILL)
                .on(StateMachineTest.OrderEvent.CREATE_SO)
                .when(passCondition())
                .perform(doOrderAction());
    }

    @Test(expected = IllegalStateException.class)
    public void duplicateMachineTest(){
        StateMachineBuilder<StateMachineTest.OrderState, StateMachineTest.OrderEvent, StateMachineTest.OrderContext> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(StateMachineTest.OrderState.INIT)
                .to(StateMachineTest.OrderState.PENDING_FULFILL)
                .on(StateMachineTest.OrderEvent.CREATE_SO)
                .when(passCondition())
                .perform(doOrderAction());
        builder.build(machineId);
        builder.build(machineId);
    }

    private Condition<StateMachineTest.OrderContext> passCondition(){
        return ctx -> true;
    }
    private Condition<StateMachineTest.OrderContext> notPassCondition(){
        return ctx -> false;
    }
    private Action<StateMachineTest.OrderState, StateMachineTest.OrderEvent, StateMachineTest.OrderContext> doOrderAction(){
        return (from, to , event, ctx) -> System.out.printf("%s is operating order %s, from %s to %s, on event %s%n",ctx.orderOperator,ctx.orderOperator,from,to,event);
    }
}
