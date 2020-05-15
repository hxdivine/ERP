package com.erp.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSON;
import com.erp.action.impl.IBaseAction;
import com.erp.biz.IStoreDetailBiz;
import com.erp.entity.StoreAlert;
import com.erp.entity.StoreDetail;
import com.erp.exception.ErpException;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class StoreDetailAction extends BaseAction<StoreDetail> implements ModelDriven<StoreDetail>,IBaseAction{
	private IStoreDetailBiz storeDetailBiz;

	public void setStoreDetailBiz(IStoreDetailBiz storeDetailBiz) {
		this.storeDetailBiz = storeDetailBiz;
		super.setBaseBiz(this.storeDetailBiz);
	}
	private StoreDetail storeDetail = new StoreDetail();

	@Override
	public StoreDetail getModel() {
		return storeDetail;
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(storeDetail.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(storeDetail);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(storeDetail.getUuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(storeDetail);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc=DetachedCriteria.forClass(StoreDetail.class);
		if(storeDetail != null){
			//根据商品编号查询
			if(null != storeDetail .getGoodsuuid()){
				dc.add(Restrictions.eq("goodsuuid", storeDetail .getGoodsuuid()));
			}
			//根据仓库编号查询
			if(null != storeDetail .getStoreuuid()){
				dc.add(Restrictions.eq("storeuuid", storeDetail .getStoreuuid()));
			}
		}
		return dc;
	}
	/**
	 * 获取库存预警列表
	 */
	public void storeAlertList(){
		List<StoreAlert> list = storeDetailBiz.getStoreAlertList();
		String jsonString = JSON.toJSONString(list);
		write(jsonString);
	}
	/**
	 * 发送预警邮件
	 */
	public void sendStoreAlertMail(){
		try {
			storeDetailBiz.sendStoreAlertMail();
			ajaxReturn(true, "发送预警邮件成功");
		} catch (MessagingException e) {
			ajaxReturn(false, "构建预警邮件失败");
			e.printStackTrace();
		}catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			ajaxReturn(false, "发送预警邮件失败");
			e.printStackTrace();
		}
	}
	
	private Long goodsuuid;
		
	public void setGoodsuuid(Long goodsuuid) {
		this.goodsuuid = goodsuuid;
	}
	//根据商品ID查询商品库存
	public void getStoreNum() {
		List<StoreDetail> list = storeDetailBiz.getStoreNum(goodsuuid);
		Long num = list.get(0).getNum();
		write(JSON.toJSONString(num));
	}
}
