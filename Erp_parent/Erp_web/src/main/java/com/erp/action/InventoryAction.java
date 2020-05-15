package com.erp.action;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.action.impl.IBaseAction;
import com.erp.biz.IInventoryBiz;
import com.erp.entity.Inventory;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 客户表
 * @author 骄傲的大树
 *
 */
public class InventoryAction extends BaseAction<Inventory> implements ModelDriven<Inventory>,IBaseAction {

	private Inventory inventory = new Inventory();
	
	public Inventory getModel() {
		return inventory;
	}
	private IInventoryBiz inventoryBiz;

	
	public void setInventoryBiz(IInventoryBiz inventoryBiz) {
		this.inventoryBiz = inventoryBiz;
		super.setBaseBiz(inventoryBiz);
	}
	
	/**
	 * 条件查询所有部门信息
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	/**
	 * 点击修改时回显数据
	 */
	public void get(){
		super.Toget(inventory.getUuid());
	}
	/**
	 * 修改方法
	 */
	public void update(){
		super.Toupdate(inventory);
	}
	/**
	 * 删除方法
	 */
	public void delete(){
		super.Todelete(inventory.getUuid());
	}
	
	public void add(){
		super.Toadd(inventory);
	}
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Inventory.class);
		return dc;
	}

}
