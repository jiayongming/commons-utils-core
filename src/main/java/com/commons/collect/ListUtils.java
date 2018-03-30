package com.commons.collect;

import com.commons.model.PageBean;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

@Slf4j
public final class ListUtils {

	private ListUtils() { }
	/**
	 * 去除list集合中的重复元素,并且维护元素的顺序不变
	 * @param list
	 * @return
	 */
	public static <E> List<E> removeRepeat(List<E> list){
		Set<E> sets = Sets.newLinkedHashSet(list);
		list.clear() ;
		list.addAll(sets) ;
		return list ;
	}
	
	public static void main(String[] args) {
		List<Integer> ints = Lists.newArrayListWithCapacity(10);
		ints.add(1) ;
		ints.add(1) ;
		ints.add(2) ;
		ints.add(3) ;
		ints.add(3) ;
		ints.add(4) ;
		ints.add(4) ;
		ints.add(5) ;
		ints.add(6) ;
		
		List<Integer> removeRepeat = removeRepeat(ints);
		final List<Integer> sorter = sorter(removeRepeat, 2, 2);
		log.info("removeRepeat:{}" ,removeRepeat);
		log.info("sorter{}" ,sorter);
	}

	/**
	 * @author jiayongming 对list进行分页
	 * @param list
	 * @param currentPage
	 * @param pageSize
	 * @param <E>
	 * @return
	 */
	public static <E> List<E> sorter(List<E> list,Integer currentPage,Integer pageSize){
		if (CollectionUtils.isEmpty(list)){
			return list ;
		}
		PageBean pageBean = PageBean.getPageBean(currentPage, pageSize);
		pageBean.setTotalCount(list.size()) ;
		Integer start = pageBean.getStart();
		Integer end = pageBean.getEnd();
		List<E> result = Lists.newArrayListWithCapacity(end - start >= 0 ? end - start : start - end);
		for (int i = start; i < end ; i++) {
			if ( start >= list.size() || i == list.size() ) {
				break ;
			}
			result.add(list.get(i)) ;
		}

		return result ;
	}
	
}
