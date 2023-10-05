package com.joseph.bloomfilter.filter;

import com.joseph.core.util.HashUtil;

/**
 * @author Joseph.Liu
 */
public class ELFFilter extends FuncFilter{
    private static final long serialVersionUID = 1L;

    public ELFFilter(long maxValue) {
        this(maxValue, DEFAULT_MACHINE_NUM);
    }

    public ELFFilter(long maxValue, int machineNum) {
        super(maxValue, machineNum, HashUtil::elfHash);
    }
}
