package com.joseph.bloomfilter.filter;

import com.joseph.core.util.HashUtil;

/**
 * @author Joseph.Liu
 */
public class SDBMFilter extends FuncFilter{
    private static final long serialVersionUID = 1L;

    public SDBMFilter(long maxValue) {
        this(maxValue, DEFAULT_MACHINE_NUM);
    }

    public SDBMFilter(long maxValue, int machineNum) {
        super(maxValue, machineNum, HashUtil::sdbmHash);
    }
}
