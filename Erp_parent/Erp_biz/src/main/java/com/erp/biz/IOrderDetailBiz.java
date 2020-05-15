package com.erp.biz;


import com.erp.entity.OrderDetail;

/**
 * 部门业务层接口
 * @author 骄傲的大树
 *
 */
public interface IOrderDetailBiz extends IBaseBiz<OrderDetail>{
	/**
	 * 入库
	 * @param uuid 订单明细id
	 * @param storeuuid 仓库id
	 * @param empuuid 库管员id
	 */
	void doInStore(Long uuid,Long storeuuid,Long empuuid);
	/**
	 * 出库
	 * @param uuid 订单明细id
	 * @param storeuuid 仓库id
	 * @param empuuid 库管员id
	 */
	void doOutStore(Long uuid,Long storeuuid,Long empuuid);
}
