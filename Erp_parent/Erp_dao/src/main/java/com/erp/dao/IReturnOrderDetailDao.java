package com.erp.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.entity.ReturnOrderDetail;
import com.erp.entity.StoreDetail;

public interface IReturnOrderDetailDao extends IBaseDao<ReturnOrderDetail>{
	/**
	 * 构建查询条件
	 * @return
	 */
	DetachedCriteria getDetachedCriteria(ReturnOrderDetail returnOrderDetail);
}
