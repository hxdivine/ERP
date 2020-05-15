package com.erp.action;

import java.io.UnsupportedEncodingException;

import org.hibernate.criterion.DetachedCriteria;

import com.erp.action.impl.IBaseAction;
import com.erp.biz.IReturnOrderDetailBiz;
import com.erp.entity.Emp;
import com.erp.entity.ReturnOrderDetail;
import com.erp.exception.ErpException;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class ReturnOrderDetailAction extends BaseAction<ReturnOrderDetail> implements ModelDriven<ReturnOrderDetail>,IBaseAction{
	private IReturnOrderDetailBiz returnOrderDetailBiz;

	public void setReturnOrderDetailBiz(IReturnOrderDetailBiz returnOrderDetailBiz) {
		this.returnOrderDetailBiz = returnOrderDetailBiz;
		super.setBaseBiz(this.returnOrderDetailBiz);
	}
	private ReturnOrderDetail returnOrderDetail = new ReturnOrderDetail();

	@Override
	public ReturnOrderDetail getModel() {
		return returnOrderDetail;
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(returnOrderDetail.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(returnOrderDetail);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(returnOrderDetail.getUuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(returnOrderDetail);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(ReturnOrderDetail.class);
		return dc;
	}
	
	
	private Long storeuuid;
	
	public Long getStoreuuid() {
		return storeuuid;
	}

	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}
	
	/**
	 * 入库
	 */
	public void doInStore(){
		Emp loginUser = getLoginUser();
		if(null == loginUser){
			//用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}
		try {
			//调用明细入库业务
			returnOrderDetailBiz.doInStore(returnOrderDetail.getUuid(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "入库成功");
		}catch (ErpException e){
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			ajaxReturn(false, "入库失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 入库
	 */
	public void doOutStore(){
		Emp loginUser = getLoginUser();
		if(null == loginUser){
			//用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}
		try {
			//调用明细出库业务
			returnOrderDetailBiz.doOutStore(returnOrderDetail.getUuid(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "出库成功");
		}catch (ErpException e){
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			ajaxReturn(false, "出库失败");
			e.printStackTrace();
		}
	}

}
