package com.erp.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.erp.action.impl.IBaseAction;
import com.erp.biz.IStoreBiz;
import com.erp.entity.Emp;
import com.erp.entity.Store;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 仓库表
 * @author 骄傲的大树
 *
 */
public class StoreAction extends BaseAction<Store> implements ModelDriven<Store>,IBaseAction {

	private Store store=new Store();
	
	public Store getModel() {
		return store;
	}
	private IStoreBiz storeBiz;

	
	public void setStoreBiz(IStoreBiz storeBiz) {
		this.storeBiz = storeBiz;
		super.setBaseBiz(storeBiz);
	}
	
	public void myList(){
		Emp loginUser = getLoginUser();
		if(loginUser == null){
			ajaxReturn(false,"亲，您没有登录用户");
			return;
		}
		store.setEmpuuid(loginUser.getUuid());
		List<Store> list = storeBiz.getList(getDetachedCriteria());
		String listString = JSON.toJSONString(list,SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
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
		super.Toget(store.getUuid());
	}
	/**
	 * 修改方法
	 */
	public void update(){
		super.Toupdate(store);
	}
	/**
	 * 删除方法
	 */
	public void delete(){
		super.Todelete(store.getUuid());
	}
	
	public void add(){
		super.Toadd(store);
	}
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Store.class);
		//根据员工编号查询
		if(store != null){
			if(null != store.getName() && store.getName().trim().length() > 0){
				dc.add(Restrictions.like("name", store.getName()));
			}
			if(null != store.getEmpuuid()){
				dc.add(Restrictions.eq("empuuid", store.getEmpuuid()));
			}
		}
		return dc;
	}

}
