package com.erp.biz;


import java.io.OutputStream;
import java.util.List;

import com.erp.entity.Orders;
import com.redsun.bos.ws.Waybilldetail;

/**
 * 订单业务逻辑接口
 * @author 骄傲的大树
 *
 */
public interface IOrdersBiz extends IBaseBiz<Orders>{
	/**
	 * 审核
	 * @param uuid 订单id
	 * @param empUuid 审核员id
	 */
	void doCheck(Long uuid,Long empUuid);
	/**
	 * 确认
	 * @param uuid 订单id
	 * @param empUuid 采购员id
	 */
	void doStart(Long uuid,Long empUuid);
	/**
	 * 导出数据
	 * @param os
	 * @param dc
	 */
	void export(OutputStream os, Long uuid);
	/**
	 * 根据运单号查询运单详情
	 */
	public List<Waybilldetail> waybilldetailList(Long sn);
}
