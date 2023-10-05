package com.joseph.bloomfilter.filter;

import com.joseph.core.util.HashUtil;

/**
 * @author Joseph.Liu
 */
public class PJWFilter extends FuncFilter{
    private static final long serialVersionUID = 1L;

    public PJWFilter(long maxValue) {
        this(maxValue, DEFAULT_MACHINE_NUM);
    }

    public PJWFilter(long maxValue, int machineNum) {
        super(maxValue, machineNum, HashUtil::pjwHash);
    }
}
