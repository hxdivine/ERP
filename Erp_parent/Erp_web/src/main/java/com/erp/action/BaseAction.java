package com.erp.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.erp.biz.IBaseBiz;
import com.erp.entity.Dep;
import com.erp.entity.Emp;
import com.erp.entity.Goods;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 通用控制层
 * @author 骄傲的大树
 *
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport {
	private IBaseBiz<T> baseBiz;
	
	public void setBaseBiz(IBaseBiz<T> baseBiz) {
		this.baseBiz = baseBiz;
	}
	private int page;
	private int rows;

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	/**
	 * 修改
	 */
	public void Toupdate(T t){
		try {
			baseBiz.update(t);
			ajaxReturn(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改失败");
		}
	}
	/**
	 * 新增
	 * @param jsonString
	 */
	public void Toadd(T t){
		//{"success":true,"message":""}
		//返回前端的JSON数据
		try {
			baseBiz.add(t);
			ajaxReturn(true, "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "新增失败");
		}
	}
	/**
	 * 条件查询所有部门信息
	 * @throws UnsupportedEncodingException 
	 */
	public void TolistByPage(DetachedCriteria detachedCriteria) {
		
		int firstResult = (page - 1) * rows;
		
		DetachedCriteria dc = detachedCriteria;
		

		long total = baseBiz.findCount(dc);
		List<T> list = baseBiz.findByPage(dc,firstResult,rows);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total",total);
		map.put("rows",list);
		
		String listString = JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
		//return "DepGetList";
	}

	/**
	 * 查询所有部门信息
	 */
	public void list(){

		List<T> list = baseBiz.getList();
		
		String listString = JSON.toJSONString(list,SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
//		return "DepList";
	}
	/**
	 * 根据id获取
	 */
	public void Toget(long uuid) {
		T t1 = baseBiz.getById(uuid);
		String jsonString = JSON.toJSONStringWithDateFormat(t1, "yyyy-MM-dd");
		String jsonStringAfter = MapDate(jsonString);

		write(jsonStringAfter);

	}
	/**
	 * 删除
	 */
	public void Todelete(long uuid) {
		try {
			baseBiz.delete(uuid);
			ajaxReturn(true, "删除成功!");
		} catch (Exception e) {
			ajaxReturn(false, "删除失败!");
			e.printStackTrace();
		}
	}
	/**
	 * 写入json
	 * @param listString
	 */
	public void write(String listString ){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(listString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void ajaxReturn(boolean success, String message){
		//返回前端的JSON数据
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("success",success);
		rtn.put("message",message);
		write(JSON.toJSONString(rtn));
	}
	
	/**
	 * 拼接map
	 ** @param jsonString
	 * @return
	 */
	public String MapDate(String jsonString){
		Map<String,Object> map = JSON.parseObject(jsonString);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		for(String key : map.keySet()){
			if(map.get(key) instanceof Map){
				Map<String,Object> m1 = (Map<String,Object>)map.get(key);
				for(String key1 : m1.keySet()){
					dataMap.put( key + "." + key1, m1.get(key1));	
				}
			}else{
				dataMap.put(key, map.get(key));
			}			
		}
		return JSON.toJSONString(dataMap);
	}
	/**
	 * 获取登录用户
	 * @return
	 */
	public Emp getLoginUser(){
		Emp emp = (Emp)SecurityUtils.getSubject().getPrincipal();
		return emp;
	}
}
