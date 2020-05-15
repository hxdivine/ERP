package com.erp.action;

import java.io.UnsupportedEncodingException;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.action.impl.IBaseAction;
import com.erp.biz.IEmp_RoleBiz;
import com.erp.entity.Emp_Role;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class Emp_RoleAction extends BaseAction<Emp_Role> implements ModelDriven<Emp_Role>,IBaseAction{
	private IEmp_RoleBiz emp_RoleBiz;

	public void setEmp_RoleBiz(IEmp_RoleBiz emp_RoleBiz) {
		this.emp_RoleBiz = emp_RoleBiz;
		super.setBaseBiz(this.emp_RoleBiz);
	}
	private Emp_Role emp_Role = new Emp_Role();

	@Override
	public Emp_Role getModel() {
		return emp_Role;
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(emp_Role.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(emp_Role);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(emp_Role.getUuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(emp_Role);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Emp_Role.class);
		return dc;
	}
}
