package com.joseph.bloomfilter;

import com.joseph.bloomfilter.filter.*;
import com.joseph.core.util.NumberUtil;

/**
 * @author Joseph.Liu
 */
public class BitMapBloomFilter implements BloomFilter{
    private static final long serialVersionUID = 1L;
    private BloomFilter[] filters;

    public BitMapBloomFilter(int m){
        long mNum = NumberUtil.div(String.valueOf(m), String.valueOf(5)).longValue();
        long size = mNum * 1024 * 1024 * 8;
        filters = new BloomFilter[]{
            new DefaultFilter(size),
                new ELFFilter(size),
                new JSFilter(size),
                new PJWFilter(size),
                new SDBMFilter(size)
        };
    }
    @Override
    public boolean contains(String str) {
        for(BloomFilter filter : filters){
            if(!filter.contains(str)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean add(String str) {
        boolean flag = false;
        for(BloomFilter filter : filters){
            flag |= filter.add(str);
        }
        return flag;
    }
}
