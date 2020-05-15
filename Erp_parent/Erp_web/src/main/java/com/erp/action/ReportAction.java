package com.erp.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.erp.biz.IReportBiz;

public class ReportAction {
	private IReportBiz reportBiz;

	public void setReportBiz(IReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}
	
	private Date startDate;
	
	private Date endDate;
	
	private int year;
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 销售统计报表
	 */
	public void OrdersReport(){
		List list = reportBiz.OrdersReport(startDate,endDate);
		String jsonString = JSON.toJSONString(list);
		write(jsonString);
	}
	
	/**
	 * 销售统计报表
	 */
	public void TrendReport(){
		List list = reportBiz.trendReport(year);
		String jsonString = JSON.toJSONString(list);
		write(jsonString);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
