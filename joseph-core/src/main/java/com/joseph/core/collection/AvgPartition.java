package com.joseph.core.collection;

import java.util.List;

/**
 * @author Joseph.Liu
 */
public class AvgPartition<T> extends Partition<T> {

    final int limit;
    final int remainder;

    public AvgPartition(List<T> list, int limit) {
        super(list, list.size() / (limit <= 0 ? 1 : limit));
        this.limit = limit;
        this.remainder = list.size() % limit;
    }

    @Override
    public List<T> get(int index) {
        final int pageSize = this.pageSize;
        final int remainder = this.remainder;

        int from = index * pageSize + Math.min(index, remainder);
        int to = from + pageSize;
        if(index + 1 <= remainder){
            to += 1;
        }
        return list.subList(from,to);
    }

    @Override
    public int size() {
        return limit;
    }
}
