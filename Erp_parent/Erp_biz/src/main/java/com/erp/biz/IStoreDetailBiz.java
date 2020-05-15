package com.erp.biz;


import java.util.List;

import javax.mail.MessagingException;

import com.erp.entity.StoreAlert;
import com.erp.entity.StoreDetail;

/**
 * 部门业务层接口
 * @author 骄傲的大树
 *
 */
public interface IStoreDetailBiz extends IBaseBiz<StoreDetail>{
	/**
	 * 获取库存预警列表
	 * @return
	 */
	List<StoreAlert> getStoreAlertList();
	/**
	 * 发送库存预警邮件
	 * @throws MessagingException
	 */
	void sendStoreAlertMail() throws MessagingException;
	/**
	 * 根据商品ID查询商品库存
	 * @param goodsuuid
	 * @return
	 */
	List<StoreDetail> getStoreNum(Long goodsuuid);
}
