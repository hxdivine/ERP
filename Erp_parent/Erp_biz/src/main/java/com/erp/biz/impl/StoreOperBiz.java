package com.erp.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.biz.IStoreOperBiz;
import com.erp.dao.IEmpDao;
import com.erp.dao.IGoodsDao;
import com.erp.dao.IStoreDao;
import com.erp.dao.IStoreOperDao;
import com.erp.entity.StoreOper;

public class StoreOperBiz extends BaseBiz<StoreOper> implements IStoreOperBiz  {
	  //
	private IStoreOperDao storeOperDao;
	private IStoreDao storeDao;
	private IGoodsDao goodsDao;
	private IEmpDao empDao;
	public void setStoreOperDao(IStoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
		super.setBaseDao(this.storeOperDao);
	}
	
	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	/**
	 * 分页查询
	 */
	public List<StoreOper> findByPage(DetachedCriteria detachedCriteria,int firstResult,int maxResult) {
	
		List<StoreOper> list = storeOperDao.findByPage(detachedCriteria,firstResult,maxResult);	
		Map<Long,String> empNameMap = new HashMap<Long,String>();
		Map<Long,String> storeNameMap = new HashMap<Long,String>();
		Map<Long,String> goodsNameMap = new HashMap<Long,String>();
		for(StoreOper log : list){
			log.setEmpName(getEmpName(log.getEmpuuid(), empNameMap, empDao));
			log.setGoodsName(getGoodsName(log.getGoodsuuid(), goodsNameMap, goodsDao));
			log.setStoreName(getStoreName(log.getStoreuuid(), storeNameMap, storeDao));
		}
		return list;
	}
}
