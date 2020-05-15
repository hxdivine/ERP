package com.erp.biz;


import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.erp.entity.ReturnOrderDetail;

/**
 * 部门业务层接口
 * @author 骄傲的大树
 *
 */
public interface IReturnOrderDetailBiz extends IBaseBiz<ReturnOrderDetail>{
	
	/**
	 * 入库
	 * @param uuid		订单明细id
	 * @param storeuuid	仓库id
	 * @param empuuid	用户id
	 */
	void doInStore(Long uuid, Long storeuuid, Long empuuid); 
	/**
	 * 出库
	 */
	void doOutStore(Long uuid,Long storeuuid, Long empuuid);
}
