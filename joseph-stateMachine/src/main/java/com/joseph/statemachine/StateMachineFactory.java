package com.joseph.statemachine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Joseph.Liu
 */
public class StateMachineFactory {

    /**
     * state machine map : key = MachineId, value = instance of StateMachine
     */
    private static final Map<String, StateMachine> STATE_MACHINE_MAP = new ConcurrentHashMap<>();

    public static <S, E, C> void register(StateMachine<S, E, C> stateMachine){
        String machineId = stateMachine.getMachineId();
        if(STATE_MACHINE_MAP.get(machineId) != null){
            throw new IllegalStateException("There's a state machine that already has a same id ["+machineId+"], don't register it again.");
        }
        STATE_MACHINE_MAP.put(stateMachine.getMachineId(), stateMachine);
    }

    public static <S, E, C> StateMachine<S, E, C> get(String machineId){
        StateMachine stateMachine = STATE_MACHINE_MAP.get(machineId);
        if(stateMachine == null){
            throw new IllegalStateException("There's no state machine instance with id ["+ machineId +"], please register it first");
        }
        return stateMachine;
    }
}
