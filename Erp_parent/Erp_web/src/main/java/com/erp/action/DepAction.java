package com.erp.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.erp.action.impl.IBaseAction;
import com.erp.biz.IDepBiz;
import com.erp.entity.Dep;
import com.erp.entity.Supplier;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class DepAction extends BaseAction<Dep> implements ModelDriven<Dep>,IBaseAction{
	private IDepBiz depBiz;

	public void setDepBiz(IDepBiz depBiz) {
		this.depBiz = depBiz;
		super.setBaseBiz(this.depBiz);
	}
	private Dep dep = new Dep();

	@Override
	public Dep getModel() {
		return dep;
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(dep.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(dep);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(dep.getUuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(dep);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Dep.class);
		if(dep != null){
			if (dep.getName() != null && dep.getName().trim().length() > 0) {
				dc.add(Restrictions.like("name", dep.getName(), MatchMode.ANYWHERE));
			}
			if (dep.getTele() != null && dep.getTele().trim().length() > 0) {
				dc.add(Restrictions.like("tele", dep.getTele(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	/**
	 * 导出数据
	 */
	public void export(){
		String filename = "部门";
		filename += ".xls";
		//响应对象
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			//设置输出流格式
			response.setHeader("Content-Disposition", "attachment;filename=" + 
			new String(filename.getBytes(),"ISO-8859-1"));
			depBiz.export(response.getOutputStream(), getDetachedCriteria());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
