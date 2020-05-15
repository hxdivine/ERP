package com.redsun.bos.biz.impl;
import com.redsun.bos.biz.IWaybillBiz;
import com.redsun.bos.dao.IWaybillDao;
import com.redsun.bos.entity.Waybill;
/**
 * 业务逻辑类
 * @author Administrator
 *
 */
public class WaybillBiz extends BaseBiz<Waybill> implements IWaybillBiz {

	private IWaybillDao waybillDao;
	
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
		super.setBaseDao(this.waybillDao);
	}
	
}
