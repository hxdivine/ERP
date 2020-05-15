package com.erp.biz.impl;

import com.erp.biz.IRole_MenuBiz;
import com.erp.dao.IRole_MenuDao;
import com.erp.entity.Role_Menu;

public class Role_MenuBiz extends BaseBiz<Role_Menu> implements IRole_MenuBiz  {
	  //
	private IRole_MenuDao role_MenuDao;

	public void setRole_MenuDao(IRole_MenuDao role_MenuDao) {
		this.role_MenuDao = role_MenuDao;
		super.setBaseDao(this.role_MenuDao);
	}

}
