package com.erp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.erp.action.impl.IBaseAction;
import com.erp.biz.ISupplierBiz;
import com.erp.entity.Supplier;
import com.erp.exception.ErpException;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 部门 action
 * @author 骄傲的大树
 *
 */
public class SupplierAction extends BaseAction<Supplier> implements ModelDriven<Supplier>,IBaseAction{
	private ISupplierBiz supplierBiz;

	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		super.setBaseBiz(this.supplierBiz);
	}
	private Supplier supplier = new Supplier();

	@Override
	public Supplier getModel() {
		return supplier;
	}
	//自动补全，由easyui的combogrid自动传值
	private String q;
	
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	private File file; //上传的文件
	private String fileFilename; //上传的文件名称
	private String fileContentType; //上传的文件类型
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFilename() {
		return fileFilename;
	}
	public void setFileFilename(String fileFilename) {
		this.fileFilename = fileFilename;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public void list(){
		supplier.setName(q);
		List<Supplier> list = supplierBiz.getList(getDetachedCriteria());
		
		String listString = JSON.toJSONString(list,SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
	}
	/**
	 * 删除
	 */
	public void delete(){
		super.Todelete(supplier.getUuid());
	}
	/**
	 * 修改
	 */
	public void update(){
		super.Toupdate(supplier);
	}
	/**
	 * 通过编辑查询对象
	 */
	public void get(){
		super.Toget(supplier.getUuid());
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void add(){
		super.Toadd(supplier);
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void listByPage() {
		
		super.TolistByPage(getDetachedCriteria());
	}
	
	
	public DetachedCriteria getDetachedCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(Supplier.class);
		if(supplier != null){
			if (supplier.getName() != null && supplier.getName().trim().length() > 0) {
				dc.add(Restrictions.like("name", supplier.getName(), MatchMode.ANYWHERE));
			}
			if (supplier.getAddress() != null && supplier.getAddress().trim().length() > 0) {
				dc.add(Restrictions.like("address", supplier.getAddress(), MatchMode.ANYWHERE));
			}
			if (supplier.getTele() != null && supplier.getTele().trim().length() > 0) {
				dc.add(Restrictions.like("tele", supplier.getTele(), MatchMode.ANYWHERE));
			}
			if (supplier.getContact() != null && supplier.getContact().trim().length() > 0) {
				dc.add(Restrictions.like("contact", supplier.getContact(), MatchMode.ANYWHERE));
			}
			if (supplier.getEmail() != null && supplier.getEmail().trim().length() > 0) {
				dc.add(Restrictions.like("email", supplier.getEmail(), MatchMode.ANYWHERE));
			}
			if(null != supplier.getType()){
				dc.add(Restrictions.eq("type", supplier.getType()));
			}
		}
		return dc;
	}
	/**
	 * 导出数据
	 */
	public void export(){
		String filename = "";
		if(Supplier.TYPE_CUSTOMER.equals(supplier.getType())){
			filename = "客户";
		}else if(Supplier.TYPE_SUPPLIER.equals(supplier.getType())){
			filename = "供应商";
		}
		filename += ".xls";
		//响应对象
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			//设置输出流格式
			response.setHeader("Content-Disposition", "attachment;filename=" + 
			new String(filename.getBytes(),"ISO-8859-1"));
			supplierBiz.export(response.getOutputStream(), getDetachedCriteria());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导入数据
	 */
	public void doImport(){
		//文件类型判断
		if(!"application/vnd.ms-excel".equals(fileContentType)){
			ajaxReturn(false, "上传的文件必须为excel文件");
			return;
		}
		try {
			supplierBiz.doImport(new FileInputStream(file));
			ajaxReturn(true, "上传的文件成功");
		} catch (ErpException e){
			ajaxReturn(false, e.getMessage());
		} catch (IOException e) {
			ajaxReturn(false, "上传的文件失败");
			e.printStackTrace();
		}
	}
}
