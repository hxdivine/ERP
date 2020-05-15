package com.erp.biz.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;

import com.erp.biz.IDepBiz;
import com.erp.dao.IDepDao;
import com.erp.entity.Dep;
import com.erp.entity.Supplier;
import com.erp.exception.ErpException;

public class DepBiz extends BaseBiz<Dep> implements IDepBiz  {
	private IDepDao depDao;

	public void setDepDao(IDepDao depDao) {
		this.depDao = depDao;
		super.setBaseDao(this.depDao);
	}
	
	@Override
	public void export(OutputStream os,DetachedCriteria dc) {
		//获取供应商  客户信息
		List<Dep> list = depDao.getList(dc);
		//创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		String sheetName = "部门";

		HSSFSheet sheet = wb.createSheet(sheetName);
		// 创建一行
		HSSFRow row = sheet.createRow(0);
		String[] header = { "部门编号", "部门名称", "部门联系电话" };
		int[] width = { 2000, 8000, 4000 };
		// 创建第一行表头信息
		HSSFCell cell = null;
		for (int i = 0; i < header.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(header[i]);
			sheet.setColumnWidth(i, width[i]);
		}
		// 导出内容
		int rowCount = 1;
		for (Dep dep : list) {
			row = sheet.createRow(rowCount);
			row.createCell(0).setCellValue(dep.getUuid()); // 部门编号
			row.createCell(1).setCellValue(dep.getName()); // 部门名称
			row.createCell(2).setCellValue(dep.getTele()); // 部门联系电话

			rowCount++;
		}

		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
