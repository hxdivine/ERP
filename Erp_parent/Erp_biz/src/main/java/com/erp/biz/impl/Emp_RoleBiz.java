package com.erp.biz.impl;

import com.erp.biz.IEmp_RoleBiz;
import com.erp.dao.IEmp_RoleDao;
import com.erp.entity.Emp_Role;

public class Emp_RoleBiz extends BaseBiz<Emp_Role> implements IEmp_RoleBiz  {

	private IEmp_RoleDao emp_RoleDao;

	public void setEmp_RoleDao(IEmp_RoleDao emp_RoleDao) {
		this.emp_RoleDao = emp_RoleDao;
		super.setBaseDao(this.emp_RoleDao);
	}



}
