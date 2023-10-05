package com.joseph.bloomfilter.filter;

import com.joseph.core.util.HashUtil;

/**
 * @author Joseph.Liu
 */
public class DefaultFilter extends FuncFilter{

    private static final long serialVersionUID = 1L;

    public DefaultFilter(long maxValue){
        this(maxValue,DEFAULT_MACHINE_NUM);
    }

    public DefaultFilter(long maxValue, int machineNum) {
        super(maxValue, machineNum, HashUtil::javaDefaultHash);
    }
}
