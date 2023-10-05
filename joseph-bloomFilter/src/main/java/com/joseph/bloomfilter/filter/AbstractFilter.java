package com.joseph.bloomfilter.filter;

import com.joseph.bloomfilter.BloomFilter;
import com.joseph.bloomfilter.bitMap.BitMap;
import com.joseph.bloomfilter.bitMap.IntMap;
import com.joseph.bloomfilter.bitMap.LongMap;

/**
 * @author Joseph.Liu
 */
public abstract class AbstractFilter implements BloomFilter {
    private static final long serialVersionUID = 1L;

    protected static int DEFAULT_MACHINE_NUM = BitMap.MACHINE32;

    private BitMap bitMap = null;

    protected long size;

    public AbstractFilter(long maxValue, int machineNum){
        init(maxValue,machineNum);
    }
    public AbstractFilter(long maxValue){
        init(maxValue,DEFAULT_MACHINE_NUM);
    }

    public void init(long maxValue, int machineNum){
        this.size = maxValue;

        switch (machineNum) {
            case BitMap.MACHINE32:
                bitMap = new IntMap((int) (size / machineNum));
                break;
            case BitMap.MACHINE64:
                bitMap = new LongMap((int) (size / machineNum));
                break;
            default:
                throw new RuntimeException("Error Machine number");
        }
    }

    @Override
    public boolean contains(String str) {
        return bitMap.contains(Math.abs(hash(str)));
    }

    @Override
    public boolean add(String str) {
        final long hash = Math.abs(hash(str));
        if (bitMap.contains(hash)) {
            return false;
        }

        bitMap.add(hash);
        return true;
    }

    /**
     * 自定义Hash方法
     *
     * @param str 字符串
     * @return HashCode
     */
    public abstract long hash(String str);
}
