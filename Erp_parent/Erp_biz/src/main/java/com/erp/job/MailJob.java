package com.erp.job;

import com.erp.biz.IStoreDetailBiz;

/**
 * 自动发预警邮件
 * @author Administrator
 *
 */
public class MailJob {
	
	private IStoreDetailBiz storeDetailBiz;

	public void setStoreDetailBiz(IStoreDetailBiz storeDetailBiz) {
		this.storeDetailBiz = storeDetailBiz;
	}

	/**
	 * 发送预警邮件调用 的方法
	 */
	public void sendStorealertMail(){
		try {
			storeDetailBiz.sendStoreAlertMail();
			System.out.println("发送预警邮件!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
