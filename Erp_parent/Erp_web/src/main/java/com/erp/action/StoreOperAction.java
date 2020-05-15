package com.erp.action;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.erp.action.impl.IBaseAction;
import com.erp.biz.IStoreOperBiz;
import com.erp.entity.StoreOper;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class StoreOperAction extends BaseAction<StoreOper> implements ModelDriven<StoreOper>,IBaseAction{
	private IStoreOperBiz storeOperBiz;

	public void setStoreOperBiz(IStoreOperBiz storeOperBiz) {
		this.storeOperBiz = storeOperBiz;
		super.setBaseBiz(this.storeOperBiz);
	}
	private StoreOper storeOper = new StoreOper();

	@Override
	public StoreOper getModel() {
		return storeOper;
	}
	private Date operEnd;
	
	public Date getOperEnd() {
		return operEnd;
	}
	public void setOperEnd(Date operEnd) {
		this.operEnd = operEnd;
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(storeOper.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(storeOper);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(storeOper.getUuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(storeOper);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(StoreOper.class);
		if(storeOper != null){
			//根据商品类型查询
			if(null != storeOper.getType() && storeOper.getType().trim().length() > 0){
				dc.add(Restrictions.eq("type", storeOper.getType()));
			}
			//根据商品
			if(null != storeOper.getGoodsuuid()){
				dc.add(Restrictions.eq("goodsuuid", storeOper.getGoodsuuid()));
			}
			//根据员工
			if(null != storeOper.getEmpuuid() ){
				dc.add(Restrictions.eq("empuuid", storeOper.getEmpuuid()));
			}
			//根据仓库
			if(null != storeOper.getStoreuuid()){
				dc.add(Restrictions.eq("storeuuid", storeOper.getStoreuuid()));
			}
			//根据操作时间开始
			if(null != storeOper.getOpertime()){
				Calendar car = Calendar.getInstance();
				car.setTime(storeOper.getOpertime());
				car.set(Calendar.HOUR, 0);
				car.set(Calendar.MINUTE, 0);
				car.set(Calendar.SECOND, 0);
				car.set(Calendar.MILLISECOND, 0);
				dc.add(Restrictions.ge("opertime", car.getTime()));
			}
		}
		//根据操作时间结束
		if(operEnd != null){
			Calendar car = Calendar.getInstance();
			car.setTime(operEnd);
			car.set(Calendar.HOUR, 23);
			car.set(Calendar.MINUTE, 59);
			car.set(Calendar.SECOND, 59);
			car.set(Calendar.MILLISECOND, 999);
			dc.add(Restrictions.le("opertime", car.getTime()));
		}
		return dc;
	}
}
