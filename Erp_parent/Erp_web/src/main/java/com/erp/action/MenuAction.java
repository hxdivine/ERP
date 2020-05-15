package com.erp.action;

import java.io.UnsupportedEncodingException;

import org.hibernate.criterion.DetachedCriteria;

import com.alibaba.fastjson.JSON;
import com.erp.action.impl.IBaseAction;
import com.erp.biz.IEmpBiz;
import com.erp.biz.IMenuBiz;
import com.erp.entity.Emp;
import com.erp.entity.Menu;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class MenuAction extends BaseAction<Menu> implements ModelDriven<Menu>,IBaseAction{
	private IMenuBiz menuBiz;
	private IEmpBiz empBiz;
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		super.setBaseBiz(this.menuBiz);
	}
	private Menu menu = new Menu();
	/**
	 * 获取菜单数据
	 */
	public void getMenuTree(){
		//通过获取主菜单 获取所有子菜单
		//Menu menu = menuBiz.getById("0");
		Emp emp = getLoginUser();
		if(emp != null){
			Menu menu = empBiz.readMenuByEmpuuid(emp.getUuid());
			write(JSON.toJSONString(menu));
		}
	}
	@Override
	public Menu getModel() {
		return menu;
	}
	/**
	 * 删除
	 */
	public void delete(){
		//super.Todelete(menu.getMenuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(menu);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		//super.Toget(menu.getMenuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(menu);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Menu.class);

		return dc;
	}
}
