package com.commons.collect;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Slf4j
public final class BigDecimalUtils {

	private BigDecimalUtils() {
		throw new AssertionError(BigDecimalUtils.class.getName()+" all methods is static so no instance for you ;") ;
	}
	
	public static BigDecimal avgBigDecimal(List<BigDecimal> bigDecimals){
		
		BigDecimal sum = new BigDecimal("0.00");
		Optional<List<BigDecimal>> fromBigDecimal = Optional.fromNullable(bigDecimals);
		if (!fromBigDecimal.isPresent() || bigDecimals.isEmpty()) {
			return sum ;
		}
		
		//删除空值
		Iterator<BigDecimal> iterator = bigDecimals.iterator();
		while (iterator.hasNext()) {
			if (null == iterator.next()) {
				iterator.remove();
			}
		}
		
		for (BigDecimal bigDecimal : bigDecimals) {
			Optional<BigDecimal> fromNullable = Optional.fromNullable(bigDecimal);
			if (fromNullable.isPresent()) {
				sum = sum.add(bigDecimal);
			}
		}
		
		return sum.divide(new BigDecimal(bigDecimals.size())) ;
	}
	
	public static void main(String[] args) {
		List<BigDecimal> newArrayList = Lists.newArrayList();
		
		BigDecimal avgBigDecimal = avgBigDecimal(newArrayList);
		log.info(avgBigDecimal.toString());
	}

}
