package com.erp.biz.impl;

import com.erp.biz.IStoreBiz;
import com.erp.dao.IStoreDao;
import com.erp.entity.Store;

public class StoreBiz extends BaseBiz<Store> implements IStoreBiz  {

	private IStoreDao storeDao;
	
	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
		super.setBaseDao(this.storeDao);
	}

}
