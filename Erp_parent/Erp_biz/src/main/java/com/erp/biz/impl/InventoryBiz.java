package com.erp.biz.impl;

import com.erp.biz.IInventoryBiz;
import com.erp.dao.IInventoryDao;
import com.erp.entity.Inventory;

public class InventoryBiz extends BaseBiz<Inventory> implements IInventoryBiz {
	private IInventoryDao inventoryDao;

	public void setInventoryDao(IInventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
		super.setBaseDao(this.inventoryDao);
	}
	
	
}
