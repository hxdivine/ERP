package com.erp.dao;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.entity.OrderDetail;

public interface IOrderDetailDao extends IBaseDao<OrderDetail>{

	/**
	 * 构建查询条件
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(OrderDetail orderDetail);
	
}
