package com.commons.model;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @author ymjia 
 * 分页工具类
 * @param <T>
 */
@Slf4j
@ToString
public final class PageBean<T> implements Serializable{
	
	private static final long serialVersionUID = -1481305626367150084L;

	@Getter @Setter
	private Integer totalCount ;// 总记录数

	@Getter
	private Integer pageSize ;// 每页查询记录数
	/**
	 * 1 代表第一页
	 */
	@Getter
	private Integer currentPage ;// 当前页码

	@Setter
	private Integer pageNum ;// 总页数
	/**
	 * 0 代表第一条
	 */
	@Setter
	private Integer start ;// 查询起始点

	@Setter
	private Integer end ;// 查询结束点

	@Getter @Setter
	private List<T> resultList ;
	
	public PageBean() {
		this.pageSize = null == pageSize ? 10 : pageSize ; // (默认10条记录)
		this.currentPage = null == currentPage ? 1 : currentPage;// 默认第1页,如果大于了最大页数，那么就展示末页
	}

	private PageBean(Integer currentPage, Integer pageSize) {
		this.pageSize = null == pageSize ? 10 : pageSize ; // (默认10条记录)
		this.currentPage = null == currentPage ? 1 : currentPage;// 默认第1页,如果大于了最大页数，那么就展示末页
	}

	public static PageBean getPageBean (Integer currentPage,Integer pageSize){
		return new PageBean(currentPage,pageSize) ;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = null == pageSize ? 10 : pageSize ; // (默认10条记录)
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = null == currentPage ? 1 : currentPage;
	}
	/**
	 * 计算分页总数
	 * totalCount	--总记录数
	 * pageSize		--每页记录数
	 */
	public Integer getPageNum() {
		return totalCount % pageSize > 0 ? totalCount / pageSize + 1 : totalCount / pageSize;
	}

	/**
	 * 计算查询起始点
	 * currentPage	--当前页码
	 * pageSize		--每页记录数
	 */
	public Integer getStart() {
		return ( (currentPage - 1) * pageSize + 1 ) - 1 ;
	}

	/**
	 * 计算查询结束点
	 * currentPage	--当前页码
	 * pageSize		--每页记录数
	 */
	public Integer getEnd() {
		// 非末页时的结束查询点
		int end = currentPage * pageSize;
		if (Objects.equal(currentPage,this.getPageNum())) {
			int remainder = this.getTotalCount() % pageSize;
			if (remainder > 0) {
				// 最后一页剩余的记录数（包括只有1页的情况）
				end = (currentPage - 1) * pageSize + remainder;
			}
		}
		return end;
	}

}