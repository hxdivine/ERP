package com.erp.biz;


import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.entity.ReturnOrders;

/**
 * 退货订单业务逻辑层接口
 * @author 骄傲的大树
 *
 */
public interface IReturnOrdersBiz extends IBaseBiz<ReturnOrders>{

	/**
	 * 根据订单采购退货申请
	 * @param sn
	 * @return
	 */
	void add(ReturnOrders returnOrders);
	
	/**
	 * 审核退货订单
	 * @param uuid	订单id
	 * @param empuuid	审核人id
	 */
	void doCheck(Long uuid,Long empuuid);
	
	List returnOrdersReport(Date startDate, Date endDate);



	/**
	 * 根据订单采购退货申请
	 * @param sn
	 * @return
	 */
	void add1(ReturnOrders returnOrders);

}
