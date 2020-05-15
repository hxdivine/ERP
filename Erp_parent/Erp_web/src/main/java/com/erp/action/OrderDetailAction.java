package com.erp.action;

import java.io.UnsupportedEncodingException;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.action.impl.IBaseAction;
import com.erp.biz.IOrderDetailBiz;
import com.erp.entity.Emp;
import com.erp.entity.OrderDetail;
import com.erp.exception.ErpException;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class OrderDetailAction extends BaseAction<OrderDetail> implements ModelDriven<OrderDetail>,IBaseAction{
	private IOrderDetailBiz orderDetailBiz;

	public void setOrderDetailBiz(IOrderDetailBiz orderDetailBiz) {
		this.orderDetailBiz = orderDetailBiz;
		super.setBaseBiz(this.orderDetailBiz);
	}
	private OrderDetail orderDetail = new OrderDetail();

	@Override
	public OrderDetail getModel() {
		return orderDetail;
	}
	
	
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(orderDetail.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(orderDetail);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(orderDetail.getUuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(orderDetail);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	/**
	 * 入库
	 */
	public void doInStore(){
		Emp loginUser = getLoginUser();
		if(null == loginUser){
			ajaxReturn(false,"亲，您没有登录用户");
			return;
		}
		try{
			orderDetailBiz.doInStore(orderDetail.getUuid(), orderDetail.getStoreuuid(), loginUser.getUuid());;
			ajaxReturn(true, "入库成功");
		}catch (ErpException e){
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			ajaxReturn(false, "入库失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 出库
	 */
	public void doOutStore(){
		Emp loginUser = getLoginUser();
		if(null == loginUser){
			ajaxReturn(false,"亲，您没有登录用户");
			return;
		}
		try{
			orderDetailBiz.doOutStore(orderDetail.getUuid(), orderDetail.getStoreuuid(), loginUser.getUuid());;
			ajaxReturn(true, "出库成功");
		}catch (ErpException e){
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			ajaxReturn(false, "出库失败");
			e.printStackTrace();
		}
	}
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(OrderDetail.class);

		return dc;
	}
}
