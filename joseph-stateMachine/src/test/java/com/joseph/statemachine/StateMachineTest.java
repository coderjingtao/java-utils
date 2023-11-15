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
public class StateMachineTest {

    static String machineId = "JOSEPH_TEST_MACHINE";

    enum OrderState {
        INIT,
        PENDING_FULFILL,
        CLOSED,
        PENDING_INVOICE,
        OPEN_INVOICE,
    }
    enum OrderEvent{
        CREATE_SO,
        LABEL_SO_TO_CLOSE,
        CLOSE_SO,
        CREATE_FULFILL,
        CREATE_INVOICE,
    }

    static class OrderContext{
        String orderId = "123456";
        String orderOperator = "Joseph.Liu";
    }

    private StateMachine<OrderState,OrderEvent,OrderContext> buildStateMachine(String machineId){
        StateMachineBuilder<OrderState,OrderEvent,OrderContext> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(OrderState.INIT)
                .to(OrderState.PENDING_FULFILL)
                .on(OrderEvent.CREATE_SO)
                .when(checkOrderCondition())
                .perform(doOrderAction());

        builder.internalTransition()
                        .within(OrderState.PENDING_FULFILL)
                                .on(OrderEvent.LABEL_SO_TO_CLOSE)
                                        .when(checkOrderCondition())
                                                .perform(doOrderAction());

        builder.externalTransition()
                .from(OrderState.PENDING_FULFILL)
                .to(OrderState.CLOSED)
                .on(OrderEvent.CLOSE_SO)
                .when(checkOrderCondition())
                .perform(doOrderAction());

        builder.externalTransition()
                .from(OrderState.PENDING_FULFILL)
                .to(OrderState.PENDING_INVOICE)
                .on(OrderEvent.CREATE_FULFILL)
                .when(checkOrderCondition())
                .perform(doOrderAction());

        builder.externalTransition()
                .from(OrderState.PENDING_INVOICE)
                .to(OrderState.OPEN_INVOICE)
                .on(OrderEvent.CREATE_INVOICE)
                .when(checkOrderCondition())
                .perform(doOrderAction());

        StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = builder.build(machineId);

//        StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = StateMachineFactory.get(machineId);
        stateMachine.showStateMachine();
        return stateMachine;
    }



    @Test
    public void testOneExternalTransition(){
        StateMachineBuilder<OrderState,OrderEvent,OrderContext> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(OrderState.PENDING_FULFILL)
                .to(OrderState.CLOSED)
                .on(OrderEvent.CLOSE_SO)
                .when(checkOrderCondition())
                .perform(doOrderAction());

        StateMachine<OrderState, OrderEvent, OrderContext> machine = builder.build(machineId);
        OrderState orderState = machine.fireEvent(OrderState.PENDING_FULFILL, OrderEvent.CLOSE_SO, new OrderContext());
        Assert.assertEquals(orderState,OrderState.CLOSED);
    }

    private Condition<OrderContext> checkOrderCondition(){
        return context -> context.orderId.equals("123456");
    }
    private Action<OrderState, OrderEvent, OrderContext> doOrderAction(){
        return (from, to , event, ctx) -> System.out.printf("%s is operating order %s, from %s to %s, on event %s%n",ctx.orderOperator,ctx.orderOperator,from,to,event);
    }

    @Test
    public void testVerify(){
        StateMachineBuilder<OrderState,OrderEvent,OrderContext> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(OrderState.INIT)
                .to(OrderState.PENDING_FULFILL)
                .on(OrderEvent.CREATE_SO)
                .when(checkOrderCondition())
                .perform(doOrderAction());

        StateMachine<OrderState, OrderEvent, OrderContext> machine = builder.build(machineId);
        Assert.assertTrue(machine.verify(OrderState.INIT,OrderEvent.CREATE_SO));
        Assert.assertFalse(machine.verify(OrderState.INIT,OrderEvent.CREATE_FULFILL));
    }

    @Test
    public void testOneInternalTransition(){
        StateMachineBuilder<OrderState,OrderEvent,OrderContext> builder = StateMachineBuilderFactory.create();
        builder.internalTransition()
                .within(OrderState.PENDING_FULFILL)
                .on(OrderEvent.LABEL_SO_TO_CLOSE)
                .when(checkOrderCondition())
                .perform(doOrderAction());

        StateMachine<OrderState, OrderEvent, OrderContext> machine = builder.build(machineId);
        OrderState orderState = machine.fireEvent(OrderState.PENDING_FULFILL, OrderEvent.LABEL_SO_TO_CLOSE, new OrderContext());
        Assert.assertEquals(orderState,OrderState.PENDING_FULFILL);
    }

    @Test
    public void testInternalAndExternalTransition(){
        StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = buildStateMachine(machineId);
        OrderContext context = new OrderContext();

        OrderState target = stateMachine.fireEvent(OrderState.INIT, OrderEvent.CREATE_SO, context);
        Assert.assertEquals(target,OrderState.PENDING_FULFILL);

        target = stateMachine.fireEvent(OrderState.PENDING_FULFILL, OrderEvent.LABEL_SO_TO_CLOSE, context);
        Assert.assertEquals(target,OrderState.PENDING_FULFILL);

        target = stateMachine.fireEvent(OrderState.PENDING_FULFILL, OrderEvent.CLOSE_SO, context);
        Assert.assertEquals(target,OrderState.CLOSED);

        target = stateMachine.fireEvent(OrderState.PENDING_FULFILL, OrderEvent.CREATE_FULFILL, context);
        Assert.assertEquals(target,OrderState.PENDING_INVOICE);

        target = stateMachine.fireEvent(OrderState.PENDING_INVOICE, OrderEvent.CREATE_INVOICE, context);
        Assert.assertEquals(target,OrderState.OPEN_INVOICE);
    }

    @Test
    public void testMultiThread(){
        buildStateMachine(machineId);

        for(int i = 0; i < 10; i++){
            Thread thread = new Thread( () -> {
                StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = StateMachineFactory.get(machineId);
                OrderState target = stateMachine.fireEvent(OrderState.INIT, OrderEvent.CREATE_SO, new OrderContext());
                Assert.assertEquals(target,OrderState.PENDING_FULFILL);
            });
            thread.start();
        }
        for(int i = 0; i < 10; i++){
            Thread thread = new Thread( () -> {
                StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = StateMachineFactory.get(machineId);
                OrderState target = stateMachine.fireEvent(OrderState.PENDING_FULFILL, OrderEvent.CLOSE_SO, new OrderContext());
                Assert.assertEquals(target,OrderState.CLOSED);
            });
            thread.start();
        }
        for(int i = 0; i < 10; i++){
            Thread thread = new Thread( () -> {
                StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = StateMachineFactory.get(machineId);
                OrderState target = stateMachine.fireEvent(OrderState.PENDING_FULFILL, OrderEvent.CREATE_FULFILL, new OrderContext());
                Assert.assertEquals(target,OrderState.PENDING_INVOICE);
            });
            thread.start();
        }
        for(int i = 0; i < 10; i++){
            Thread thread = new Thread( () -> {
                StateMachine<OrderState, OrderEvent, OrderContext> stateMachine = StateMachineFactory.get(machineId);
                OrderState target = stateMachine.fireEvent(OrderState.PENDING_INVOICE, OrderEvent.CREATE_INVOICE, new OrderContext());
                Assert.assertEquals(target,OrderState.OPEN_INVOICE);
            });
            thread.start();
        }
    }
}
