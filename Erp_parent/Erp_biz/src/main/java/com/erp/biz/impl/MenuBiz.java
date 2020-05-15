package com.erp.biz.impl;

import com.erp.biz.IMenuBiz;
import com.erp.dao.IMenuDao;
import com.erp.entity.Menu;

public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz  {
	  //
	private IMenuDao menuDao;

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		super.setBaseDao(this.menuDao);
	}


}
