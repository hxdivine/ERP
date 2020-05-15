package com.redsun.bos.ws.impl;

import java.util.List;

import com.redsun.bos.biz.IWaybillBiz;
import com.redsun.bos.biz.IWaybilldetailBiz;
import com.redsun.bos.entity.Waybill;
import com.redsun.bos.entity.Waybilldetail;
import com.redsun.bos.ws.IWaybillWs;

public class WaybillWs implements IWaybillWs {
	
	private IWaybillBiz waybillBiz;
	private IWaybilldetailBiz waybilldetailBiz;

	public void setWaybillBiz(IWaybillBiz waybillBiz) {
		this.waybillBiz = waybillBiz;
	}

	public void setWaybilldetailBiz(IWaybilldetailBiz waybilldetailBiz) {
		this.waybilldetailBiz = waybilldetailBiz;
	}

	/**
	 * 查询运单详情
	 * @param id
	 * @return
	 */
	public List<Waybilldetail> waybilldetailList(Long sn) {
		//构建查询条件
		Waybilldetail waybilldetail = new Waybilldetail();
		waybilldetail.setSn(sn);
		return waybilldetailBiz.getList(waybilldetail, null, null);
	}

	@Override
	public Long addWaybill(Long id, String toAddress, String addressee, String tele, String info) {
		Waybill waybill = new Waybill();
		waybill.setToaddress(toAddress);
		waybill.setInfo(info);
		waybill.setState("0");
		waybill.setTele(tele);
		waybill.setAddressee(addressee);
		waybill.setUserid(id);
		waybillBiz.add(waybill);
		return waybill.getSn();
	}

}
