package com.erp.biz.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;

import com.erp.biz.IGoodsTypeBiz;
import com.erp.dao.IGoodsTypeDao;
import com.erp.entity.GoodsType;

public class GoodsTypeBiz extends BaseBiz<GoodsType> implements IGoodsTypeBiz  {
	  //
	private IGoodsTypeDao goodsTypeDao;

	public void setGoodsTypeDao(IGoodsTypeDao goodsTypeDao) {
		this.goodsTypeDao = goodsTypeDao;
		super.setBaseDao(this.goodsTypeDao);
	}

	@Override
	public void export(OutputStream os,DetachedCriteria dc) {
		//获取供应商  客户信息
		List<GoodsType> list = goodsTypeDao.getList(dc);
		//创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		String sheetName = "商品类型";

		HSSFSheet sheet = wb.createSheet(sheetName);
		// 创建一行
		HSSFRow row = sheet.createRow(0);
		String[] header = { "商品类型编号", "商品类型名称" };
		int[] width = { 2000, 4000 };
		// 创建第一行表头信息
		HSSFCell cell = null;
		for (int i = 0; i < header.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(header[i]);
			sheet.setColumnWidth(i, width[i]);
		}
		// 导出内容
		int rowCount = 1;
		for (GoodsType goodsType : list) {
			row = sheet.createRow(rowCount);
			row.createCell(0).setCellValue(goodsType.getUuid()); // 编号
			row.createCell(1).setCellValue(goodsType.getName()); // 名称
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
