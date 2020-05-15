package com.erp.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.erp.action.impl.IBaseAction;
import com.erp.biz.IGoodsTypeBiz;
import com.erp.entity.GoodsType;
import com.opensymphony.xwork2.ModelDriven;

public class GoodsTypeAction extends BaseAction<GoodsType> implements IBaseAction, ModelDriven<GoodsType> {
	private IGoodsTypeBiz goodsTypeBiz;
	
	public void setGoodsTypeBiz(IGoodsTypeBiz goodsTypeBiz) {
		this.goodsTypeBiz = goodsTypeBiz;
		super.setBaseBiz(goodsTypeBiz);
	}

	private GoodsType goodstype = new GoodsType();

	@Override
	public GoodsType getModel() {
		return goodstype;
	}
	@Override
	public void listByPage() {
		super.TolistByPage(getDetachedCriteria());
	}

	@Override
	public void get() {
		super.Toget(goodstype.getUuid());
	}

	@Override
	public void update() {
		super.Toupdate(goodstype);
	}

	@Override
	public void delete() {
		super.Todelete(goodstype.getUuid());
	}

	@Override
	public void add() {
		super.Toadd(goodstype);
	}

	@Override
	public DetachedCriteria getDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsType.class);
		return dc;
	}

	/**
	 * 导出数据
	 */
	public void export(){
		String filename = "商品类型";
		filename += ".xls";
		//响应对象
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			//设置输出流格式
			response.setHeader("Content-Disposition", "attachment;filename=" + 
			new String(filename.getBytes(),"ISO-8859-1"));
			goodsTypeBiz.export(response.getOutputStream(), getDetachedCriteria());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
