package com.erp.biz.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;

import com.erp.biz.IGoodsBiz;
import com.erp.dao.IGoodsDao;
import com.erp.entity.Goods;


public class GoodsBiz extends BaseBiz<Goods> implements IGoodsBiz  {
	  //
	private IGoodsDao goodsDao;

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
		super.setBaseDao(this.goodsDao);
	}
	@Override
	public void export(OutputStream os,DetachedCriteria dc) {
		//获取供应商  客户信息
		List<Goods> list = goodsDao.getList(dc);
		//创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		String sheetName = "商品";

		HSSFSheet sheet = wb.createSheet(sheetName);
		// 创建一行
		HSSFRow row = sheet.createRow(0);
		String[] header = { "商品编号", "名称", "产地","厂家","计量单位","进货价格","销售价格","商品类型" };
		int[] width = { 2000, 3000, 4000,4000,2000,2000,2000,4000 };
		// 创建第一行表头信息
		HSSFCell cell = null;
		for (int i = 0; i < header.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(header[i]);
			sheet.setColumnWidth(i, width[i]);
		}
		// 导出内容
		int rowCount = 1;
		for (Goods goods : list) {
			row = sheet.createRow(rowCount);
			row.createCell(0).setCellValue(goods.getUuid()); // 部门编号
			row.createCell(1).setCellValue(goods.getName()); // 部门名称
			row.createCell(2).setCellValue(goods.getOrigin()); // 部门联系电话
			row.createCell(3).setCellValue(goods.getProducer()); 
			row.createCell(4).setCellValue(goods.getUnit()); 
			row.createCell(5).setCellValue(goods.getInprice()); 
			row.createCell(6).setCellValue(goods.getOutprice()); 
			row.createCell(7).setCellValue(goods.getGoodsType().getName()); 
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
