package com.erp.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.biz.IStoreDetailBiz;
import com.erp.dao.IGoodsDao;
import com.erp.dao.IStoreDao;
import com.erp.dao.IStoreDetailDao;
import com.erp.entity.StoreAlert;
import com.erp.entity.StoreDetail;
import com.erp.exception.ErpException;
import com.erp.util.MailUtil;

public class StoreDetailBiz extends BaseBiz<StoreDetail> implements IStoreDetailBiz  {
	 
	private IStoreDetailDao storeDetailDao;
	private IGoodsDao goodsDao;
	private IStoreDao storeDao;
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void setStoreDetailDao(IStoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
		super.setBaseDao(this.storeDetailDao);
	}
	private MailUtil mailUtil;
	
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}
	/**发送邮件地址*/
	private String to;
	/**邮件主题*/
	private String subject;
	/**邮件正文*/
	private String text;
	public void setTo(String to) {
		this.to = to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 分页查询
	 */
	public List<StoreDetail> findByPage(DetachedCriteria detachedCriteria,int firstResult,int maxResult) {
	
		List<StoreDetail> list = storeDetailDao.findByPage(detachedCriteria,firstResult,maxResult);	
		
		Map<Long,String> goodsNameMap = new HashMap<Long,String>();
		Map<Long,String> storeNameMap = new HashMap<Long,String>();
		for(StoreDetail sd : list){
			sd.setGoodsName(getGoodsName(sd.getGoodsuuid(), goodsNameMap,goodsDao));
			sd.setStoreName(getStoreName(sd.getStoreuuid(), storeNameMap,storeDao));
		}
		return list;
	}

	@Override
	public List<StoreAlert> getStoreAlertList() {
		return storeDetailDao.getStoreAlertList();
	}

	@Override
	public void sendStoreAlertMail() throws MessagingException {
		//查看是否有存在库存预警的商品
		List<StoreAlert> storeAlertList = storeDetailDao.getStoreAlertList();
		int cnt = storeAlertList == null ? 0: storeAlertList.size();
		if(cnt > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			mailUtil.sendMail(to, subject.replace("[time]", sdf.format(new Date())), 
					text.replace("[count]", String.valueOf(cnt)));
		}else{
			throw new ErpException("没有需要预警的商品!");
		}
	}

	@Override
	public List<StoreDetail> getStoreNum(Long goodsuuid) {
		return storeDetailDao.getStoreNum(goodsuuid);
	}
}
