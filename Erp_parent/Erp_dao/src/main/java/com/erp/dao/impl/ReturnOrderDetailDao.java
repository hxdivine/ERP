package com.erp.dao.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.erp.dao.IReturnOrderDetailDao;
import com.erp.entity.ReturnOrderDetail;

public class ReturnOrderDetailDao extends BaseDao<ReturnOrderDetail> implements IReturnOrderDetailDao {
	/**
	 * 构建查询条件
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(ReturnOrderDetail returnOrderDetail){
		DetachedCriteria dc=DetachedCriteria.forClass(ReturnOrderDetail.class);
		if(returnOrderDetail != null ){
			if(null != returnOrderDetail.getGoodsname() && returnOrderDetail.getGoodsname().trim().length()>0){
				dc.add(Restrictions.like("goodsname", returnOrderDetail.getGoodsname(), MatchMode.ANYWHERE));
			}
			//根据状态查询
			if(null != returnOrderDetail.getState() && returnOrderDetail.getState().trim().length()>0){
				dc.add(Restrictions.eq("state", returnOrderDetail.getState()));
			}
			//根据订单查询
			if(null != returnOrderDetail.getReturnOrders() && null != returnOrderDetail.getReturnOrders().getUuid()){
				dc.add(Restrictions.eq("returnorders", returnOrderDetail.getReturnOrders()));
			}

		}
		return dc;
	}
}
