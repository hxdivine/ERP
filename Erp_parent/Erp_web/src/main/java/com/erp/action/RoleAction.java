package com.erp.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.alibaba.fastjson.JSON;
import com.erp.action.impl.IBaseAction;
import com.erp.biz.IRoleBiz;
import com.erp.entity.Role;
import com.erp.entity.Tree;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class RoleAction extends BaseAction<Role> implements ModelDriven<Role>,IBaseAction{
	private IRoleBiz roleBiz;

	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		super.setBaseBiz(this.roleBiz);
	}
	private Role role = new Role();

	@Override
	public Role getModel() {
		return role;
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(role.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(role);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(role.getUuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(role);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Role.class);
		return dc;
	}
	
	public void readRoleMenus(){
		List<Tree> menus = roleBiz.readRoleMenus(role.getUuid());
		String listString = JSON.toJSONString(menus);
		write(listString);
	}
	
	private String checkedStr;

	public String getCheckedStr() {
		return checkedStr;
	}
	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}
	
	/**
	 * 更新角色数据
	 */
	public void updateRoleMenus(){
		try {
			roleBiz.updateRoleMenus(role.getUuid(), checkedStr);
			ajaxReturn(true,"更新角色权限成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false,"更新角色权限失败");
		} 
		
	}
}
