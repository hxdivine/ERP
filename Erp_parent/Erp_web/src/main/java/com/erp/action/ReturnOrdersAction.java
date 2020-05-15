package com.erp.action;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.UnauthorizedException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSON;
import com.erp.action.impl.IBaseAction;
import com.erp.biz.IReturnOrdersBiz;
import com.erp.entity.Emp;
import com.erp.entity.ReturnOrderDetail;
import com.erp.entity.ReturnOrders;
import com.erp.exception.ErpException;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class ReturnOrdersAction extends BaseAction<ReturnOrders> implements ModelDriven<ReturnOrders>,IBaseAction{
	private IReturnOrdersBiz returnOrdersBiz;

	public void setReturnOrdersBiz(IReturnOrdersBiz returnOrdersBiz) {
		this.returnOrdersBiz = returnOrdersBiz;
		super.setBaseBiz(this.returnOrdersBiz);
	}
	private ReturnOrders returnOrders = new ReturnOrders();

	@Override
	public ReturnOrders getModel() {
		return returnOrders;
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(returnOrders.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(returnOrders);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(returnOrders.getUuid());
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(ReturnOrders.class);
		if(returnOrders != null){
			//根据订单类型查询
			if(null != returnOrders.getType() && returnOrders.getType().trim().length() > 0 ){
				dc.add(Restrictions.eq("type", returnOrders.getType()));
			}
			//根据订单状态查询
			if(null != returnOrders.getState() && returnOrders.getState().trim().length() > 0 ){
				dc.add(Restrictions.eq("state", returnOrders.getState()));
			}
			if(null != returnOrders.getCreater()){
				dc.add(Restrictions.eq("creater", returnOrders.getCreater()));
			}
		}
		return dc;
	}
	
	private Date startDate;
	private Date endDate;

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	// 接收订单明细的json格式的字符,数组形式的json字符串,里面的元素应该是每个订单明细
	private String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	/**
	 * 添加订单
	 */
	public void add1() {
		try {
			// 订单创建者
			returnOrders.setCreater(getLoginUser().getUuid());
			List<ReturnOrderDetail> returnOrderDetails = JSON.parseArray(json, ReturnOrderDetail.class);
			// 订单明细
			returnOrders.setReturnOrderDetails(returnOrderDetails);
			returnOrdersBiz.add1(returnOrders);
			ajaxReturn(true, "添加订单成功");
		} catch (Exception e) {
			ajaxReturn(false, "添加订单失败");
			e.printStackTrace();
		}
	}

	/**
	 * 新增
	 * 
	 * @param jsonString
	 */
	public void add() {
		// 获取主题

		Emp loginUser = getLoginUser();
		if (null == loginUser) {
			// 用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}

		try {
			// 有权限,开始操作

			// 1.退货订单明细
			List<ReturnOrderDetail> detailList = JSON.parseArray(json, ReturnOrderDetail.class);
			returnOrders.setReturnOrderDetails(detailList);
			// 2.下单员
			returnOrders.setCreater(loginUser.getUuid());
			returnOrdersBiz.add(returnOrders);
			ajaxReturn(true, "添加订单成功");
		} catch (Exception e) {
			ajaxReturn(false, "添加订单失败");
			e.printStackTrace();
		}

	}

	/**
	 * 我的订单
	 */
	public void myListByPage() {
		Emp loginUser = getLoginUser();
		// 登陆用户的编号查询
		returnOrders.setCreater(loginUser.getUuid());

		super.TolistByPage(getDetachedCriteria());
	}

	/**
	 * 退货订单审核
	 */
	public void doCheck() {
		// 获取当前登陆用户
		Emp loginUser = getLoginUser();
		if (null == loginUser) {
			// 用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}
		try {
			// 调用审核业务
			returnOrdersBiz.doCheck(returnOrders.getUuid(), getLoginUser().getUuid());
			ajaxReturn(true, "审核成功");
		} catch (UnauthorizedException u) {
			ajaxReturn(false, "权限不足");
		} catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			ajaxReturn(false, "审核失败");
			e.printStackTrace();
		}
	}

	public void returnOrdersReport() {
		List returnOrdersList = returnOrdersBiz.returnOrdersReport(startDate, endDate);
		write(JSON.toJSONString(returnOrdersList));
	}
		
}
