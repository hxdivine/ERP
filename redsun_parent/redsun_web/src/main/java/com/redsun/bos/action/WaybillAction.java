package com.redsun.bos.action;
import com.redsun.bos.biz.IWaybillBiz;
import com.redsun.bos.entity.Waybill;

/**
 * Action 
 * @author Administrator
 *
 */
public class WaybillAction extends BaseAction<Waybill> {

	private IWaybillBiz waybillBiz;

	public void setWaybillBiz(IWaybillBiz waybillBiz) {
		this.waybillBiz = waybillBiz;
		super.setBaseBiz(this.waybillBiz);
	}

}
