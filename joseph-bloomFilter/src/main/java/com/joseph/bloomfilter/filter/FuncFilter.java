package com.joseph.bloomfilter.filter;

import java.util.function.Function;

/**
 * @author Joseph.Liu
 */
public class FuncFilter extends AbstractFilter{

    private static final long serialVersionUID = 1L;

    private final Function<String, Number> hashFunc;

    public FuncFilter(long maxValue, int machineNum, Function<String, Number> hashFunc) {
        super(maxValue, machineNum);
        this.hashFunc = hashFunc;
    }

    public FuncFilter(long maxValue, Function<String, Number> hashFunc) {
        this(maxValue, DEFAULT_MACHINE_NUM, hashFunc);
    }

    @Override
    public long hash(String str) {
        return hashFunc.apply(str).longValue() % size;
    }
}
