package com.erp.dao.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.erp.dao.IOrderDetailDao;
import com.erp.entity.OrderDetail;

public class OrderDetailDao extends BaseDao<OrderDetail> implements IOrderDetailDao {

	/**
	 * 构建查询条件
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(OrderDetail orderDetail){
		DetachedCriteria dc=DetachedCriteria.forClass(OrderDetail.class);
		if(orderDetail!=null){
			if(null != orderDetail.getGoodsname() && orderDetail.getGoodsname().trim().length()>0){
				dc.add(Restrictions.like("goodsname", orderDetail.getGoodsname(), MatchMode.ANYWHERE));
			}
			//根据状态查询
			if(null != orderDetail.getState() && orderDetail.getState().trim().length()>0){
				dc.add(Restrictions.eq("state", orderDetail.getState()));
			}
			//根据订单查询
			if(null != orderDetail.getOrders() && null != orderDetail.getOrders().getUuid()){
				dc.add(Restrictions.eq("orders", orderDetail.getOrders()));
			}

		}
		return dc;
	}
}
