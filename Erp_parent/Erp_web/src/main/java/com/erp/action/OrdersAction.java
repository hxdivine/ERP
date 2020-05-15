package com.erp.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSON;
import com.erp.action.impl.IBaseAction;
import com.erp.biz.IOrdersBiz;
import com.erp.entity.Emp;
import com.erp.entity.OrderDetail;
import com.erp.entity.Orders;
import com.erp.exception.ErpException;
import com.opensymphony.xwork2.ModelDriven;
import com.redsun.bos.ws.Waybilldetail;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class OrdersAction extends BaseAction<Orders> implements ModelDriven<Orders>,IBaseAction{
	private IOrdersBiz ordersBiz;

	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		super.setBaseBiz(this.ordersBiz);
	}
	private Orders orders = new Orders();

	@Override
	public Orders getModel() {
		return orders;
	}
	//接收提交的json字符串数据
	private String json;
	
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	private String supplier;
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(orders.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(orders);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(orders.getUuid());
	}
	/**
	 * 新增订单
	 * @param jsonString
	 */
	public void add(){
		Emp longUser = getLoginUser();
		if(null == longUser){
			ajaxReturn(false,"亲，您没有登录用户");
			return;
		}
		List<OrderDetail> list = JSON.parseArray(json, OrderDetail.class);

		try {
			orders.setSupplieruuid(Long.parseLong(supplier));
			//订单创建者
			orders.setCreater(longUser.getUuid());
			//订单明细
			orders.setOrderDetails(list);
			
			ordersBiz.add(orders);
			ajaxReturn(true,"添加订单成功");
		} catch (Exception e) {
			ajaxReturn(false,"添加订单失败");
			e.printStackTrace();
		}
	}
	/**
	 * 采购订单审核
	 */
	public void doCheck(){
		Emp loginUser = getLoginUser();
		if(loginUser == null){
			ajaxReturn(false,"亲，您没有登录用户");
			return;
		}
		try {
			ordersBiz.doCheck(orders.getUuid(), loginUser.getUuid());
			ajaxReturn(true,"订单审核成功");
		} catch (ErpException e) {
			ajaxReturn(false,e.getMessage());
		}catch (UnauthorizedException e) {
			ajaxReturn(false,"没有权限访问");
		}catch (Exception e) {
			ajaxReturn(false,"订单审核失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 采购订单确认
	 */
	public void doStart(){
		Emp loginUser = getLoginUser();
		if(loginUser == null){
			ajaxReturn(false,"亲，您没有登录用户");
			return;
		}
		try {
			ordersBiz.doStart(orders.getUuid(), loginUser.getUuid());
			ajaxReturn(true,"订单确认成功");
		} catch (ErpException e) {
			ajaxReturn(false,e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			ajaxReturn(false,"订单确认失败");
			e.printStackTrace();
		}
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	/**
	 * 我的订单
	 */
	public void myListByPage(){
		Emp loginUser = getLoginUser();
		if(loginUser == null){
			ajaxReturn(false,"亲，您没有登录用户");
			return;
		}
		orders.setCreater(loginUser.getUuid());
		super.TolistByPage(getDetachedCriteria());
		System.out.println(orders.getType());
	}
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Orders.class);
		if(orders != null){
			//根据订单类型查询
			if(null != orders.getType() && orders.getType().trim().length() > 0 ){
				dc.add(Restrictions.eq("type", orders.getType()));
			}
			//根据订单状态查询
			if(null != orders.getState() && orders.getState().trim().length() > 0 ){
				dc.add(Restrictions.eq("state", orders.getState()));
			}
			if(null != orders.getCreater()){
				dc.add(Restrictions.eq("creater", orders.getCreater()));
			}
		}
		return dc;
	}
	
	/**
	 * 导出数据
	 */
	public void export(){
		String filename = "";
		if(Orders.TYPE_IN.equals(orders.getType())){
			filename = "采购订单";
		}
		if(Orders.TYPE_OUT.equals(orders.getType())){
			filename = "销售订单";
		}
		filename += "_" + orders.getUuid() + ".xls";
		//响应对象
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			//设置输出流格式
			response.setHeader("Content-Disposition", "attachment;filename=" + 
			new String(filename.getBytes(),"ISO-8859-1"));
			ordersBiz.export(response.getOutputStream(), orders.getUuid());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Long waybillsn;
	public Long getWaybillsn() {
		return waybillsn;
	}
	public void setWaybillsn(Long waybillsn) {
		this.waybillsn = waybillsn;
	}
	
	/**
	 * 根据运单号查询运单详情
	 */
	public void waybilldetailList(){
		List<Waybilldetail> waybilldetailList = ordersBiz.waybilldetailList(waybillsn);
		write(JSON.toJSONString(waybilldetailList));
	}
}
