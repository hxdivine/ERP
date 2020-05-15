package com.erp.biz.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;

import com.erp.biz.ISupplierBiz;
import com.erp.dao.ISupplierDao;
import com.erp.entity.Supplier;
import com.erp.exception.ErpException;

public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz  {
	  //
	private ISupplierDao supplierDao;

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		super.setBaseDao(this.supplierDao);
	}

	@Override
	public void export(OutputStream os,DetachedCriteria dc) {
		//获取供应商  客户信息
		List<Supplier> list = supplierDao.getList(dc);
		//创建一个工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		
		String sheetName = "";
		if(list.size() > 0){
			if(Supplier.TYPE_CUSTOMER.equals(list.get(0).getType())){
				sheetName = "客户";
			}
			if(Supplier.TYPE_SUPPLIER.equals(list.get(0).getType())){
				sheetName = "供应商";
			}
			//创建一个工作表
			HSSFSheet sheet = wb.createSheet(sheetName);
			//创建一行,行的索引是从0开始, 写标题
			HSSFRow row = sheet.createRow(0);
			String[] header = {"名称","地址","联系人","电话","Email"};
			int[] width = {5000,8000,4000,8000,10000};
			HSSFCell cell = null;
			for(int i = 0; i < header.length; i++){
				cell = row.createCell(i);
				cell.setCellValue(header[i]);
				//设置列宽
				sheet.setColumnWidth(i, width[i]);
			}
			//导出的内容
			int rowCount = 1;
			for(Supplier supplier : list){
				row = sheet.createRow(rowCount);
				row.createCell(0).setCellValue(supplier.getName());//名称
				row.createCell(1).setCellValue(supplier.getAddress());//地址
				row.createCell(2).setCellValue(supplier.getContact());//联系人
				row.createCell(3).setCellValue(supplier.getTele());//电话
				row.createCell(4).setCellValue(supplier.getEmail());//Email
				rowCount++;
			}
		}else{
			throw new ErpException("不能导入空数据");
		}
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void doImport(InputStream is) {
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheetAt(0);
			String type = "";
			if("供应商".equals(sheet.getSheetName())){
				type = Supplier.TYPE_SUPPLIER;
			}else if("客户".equals(sheet.getSheetName())){
				type = Supplier.TYPE_CUSTOMER;
			}else{
				throw new ErpException("工作表名不正确");
			}
			//读取数据
			//最后一行行号
			int lastRow = sheet.getLastRowNum();
			Supplier supplier = null;
			List<Supplier> list = null;
			for(int i = 1; i <= lastRow; i++){
				supplier = new Supplier();
				supplier.setName(sheet.getRow(i).getCell(0).getStringCellValue());
				//判断是否存在客户 或供应商
				list = supplierDao.findByName(supplier.getName());
				if(list.size() > 0){
					supplier = list.get(0);
				}
				supplier.setAddress(sheet.getRow(i).getCell(1).getStringCellValue());
				supplier.setContact(sheet.getRow(i).getCell(2).getStringCellValue());
				supplier.setTele(sheet.getRow(i).getCell(3).getStringCellValue());
				supplier.setEmail(sheet.getRow(i).getCell(4).getStringCellValue());
				//判断是否存在客户 或供应商
				list = supplierDao.findByName(supplier.getName());
				if(list.size() == 0){
					supplier.setType(type);
					//添加数据
					supplierDao.add(supplier);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
