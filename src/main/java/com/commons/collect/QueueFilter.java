package com.commons.collect;

/**
 * <h6>Description:Queue过滤接口<h6>
 * <p></p>
 *
 * @date 2015-07-23.
 */
public interface QueueFilter<T> {
    boolean filter(T t);
}
