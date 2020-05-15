package com.erp.action.impl;

import org.hibernate.criterion.DetachedCriteria;

public interface IBaseAction {
	/**
	 * 条件查询所有部门信息
	 */
	public void listByPage();
	/**
	 * 点击修改时回显数据
	 */
	public void get();
	/**
	 * 修改方法
	 */
	public void update();
	/**
	 * 删除方法
	 */
	public void delete();
	/**
	 * 增加
	 */
	public void add();
	/**
	 * 条件查询
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria();
}
