package com.erp.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface IBaseDao<T> {
	/**
	 * 查询所有部门信息
	 * 
	 * @return
	 */
	List<T> getList();
	/**
	 * 查询所有部门信息
	 * 
	 * @return
	 */
	List<T> getList(DetachedCriteria detachedCriteria);

	/**
	 * 新增
	 * 
	 * @param t
	 */
	void add(T t);

	/**
	 * 修改
	 * 
	 * @param t
	 */
	void update(T t);

	/**
	 * 根据id获取
	 * 
	 * @param t
	 */
	T getById(long id);

	/**
	 * 删除
	 * 
	 * @param t
	 */
	void delete(long id);
	/**
	 * 查询数据库总记数个数
	 * @param detachedCriteria
	 * @return
	 */
	long findCount(DetachedCriteria detachedCriteria);

	/**
	 * 分页数据查询
	 * @param detachedCriteria
	 * @param begin
	 * @param pageSize
	 * @return
	 */
	List<T> findByPage(DetachedCriteria detachedCriteria,int firstResult,int maxResult);
	/**
	 * 根据id获取
	 * 
	 * @param t
	 */
	T getById(String id);

}
