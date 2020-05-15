package com.erp.action;

import java.io.UnsupportedEncodingException;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.action.impl.IBaseAction;
import com.erp.biz.IRole_MenuBiz;
import com.erp.entity.Role_Menu;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class Role_MenuAction extends BaseAction<Role_Menu> implements ModelDriven<Role_Menu>,IBaseAction{
	private IRole_MenuBiz role_MenuBiz;

	public void setRole_MenuBiz(IRole_MenuBiz role_MenuBiz) {
		this.role_MenuBiz = role_MenuBiz;
		super.setBaseBiz(this.role_MenuBiz);
	}
	private Role_Menu role_Menu = new Role_Menu();

	@Override
	public Role_Menu getModel() {
		return role_Menu;
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(role_Menu.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(role_Menu);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(role_Menu.getUuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(role_Menu);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Role_Menu.class);
		return dc;
	}
}
