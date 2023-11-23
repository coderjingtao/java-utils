package com.joseph.core.collection;

import com.joseph.core.lang.Assert;

import java.util.AbstractList;
import java.util.List;

/**
 * @author Joseph.Liu
 */
public class Partition<T> extends AbstractList<List<T>> {

    protected final List<T> list;
    protected final int pageSize;

    public Partition(List<T> list, int pageSize){
        this.list = Assert.notNull(list);
        this.pageSize = Math.min(list.size(),pageSize);
    }

    /**
     * 根据page no获取list中的sublist
     * @param index page no. start from 0
     * @return sublist
     */
    @Override
    public List<T> get(int index) {
        final int from = index * pageSize;
        final int to = Math.min(from + pageSize, list.size());
        return list.subList(from,to);
    }

    /**
     * 返回按照当前的page size,一共分割了多少份的sublist
     * @return sublist的数量
     */
    @Override
    public int size() {
        final int size = this.pageSize;
        if(size == 0){
            return 0;
        }
        final int total = list.size();
        //当list total并不是整份pageSize时,page多加一页,即page += 1
        return (total + size - 1) / size;
    }
}
