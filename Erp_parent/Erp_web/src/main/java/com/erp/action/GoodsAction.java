package com.erp.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.alibaba.fastjson.JSON;
import com.erp.action.impl.IBaseAction;
import com.erp.biz.IGoodsBiz;
import com.erp.entity.Goods;
import com.opensymphony.xwork2.ModelDriven;

public class GoodsAction extends BaseAction<Goods> implements ModelDriven<Goods>,IBaseAction{
	private IGoodsBiz goodsBiz;

	public void setGoodsBiz(IGoodsBiz goodsBiz) {
		this.goodsBiz = goodsBiz;
		super.setBaseBiz(goodsBiz);
	}
	private Goods goods = new Goods();

	public Goods getModel() {
		return goods;
	}

	@Override
	public void listByPage() {
		super.TolistByPage(getDetachedCriteria());
	}

	@Override
	public void update() {
		super.Toupdate(goods);
	}

	@Override
	public void delete() {
		super.Todelete(goods.getUuid());
	}

	@Override
	public void add() {
		super.Toadd(goods);
	}

	@Override
	public DetachedCriteria getDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(Goods.class);
		return dc;
	}

	@Override
	public void get() {
		super.Toget(goods.getUuid());

	}
	/**
	 * 导出数据
	 */
	public void export(){
		String filename = "商品";
		filename += ".xls";
		//响应对象
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			//设置输出流格式
			response.setHeader("Content-Disposition", "attachment;filename=" + 
			new String(filename.getBytes(),"ISO-8859-1"));
			goodsBiz.export(response.getOutputStream(), getDetachedCriteria());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
