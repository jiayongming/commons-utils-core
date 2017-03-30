package com.commons.collect;

/**
 * <h6>Description:List过滤接口<h6>
 * <p></p>
 *
 * @date 2015-07-23.
 */
public interface ListFilter<T> {
    boolean filter(T t);
}
