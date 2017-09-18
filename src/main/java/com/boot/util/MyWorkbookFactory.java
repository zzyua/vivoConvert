package com.boot.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class MyWorkbookFactory {

	/**
	 * 生成简化的后的Sheet
	 * @param workbook
	 * @return
	 */
	public static Workbook createSheet(Workbook workbook) {

		boolean flag = true;
		int index = 0;
		int cellCounts = 0; // 记录地区数量
		Sheet sheet = workbook.getSheetAt(0);

//		Sheet sheet22 = workbook.getSheet("简化后");
//		if (sheet22 != null) {
//			workbook.removeSheetAt(1);
//			//workbook.removeSheetAt(2);
//		}

		Sheet sheet1 = workbook.createSheet("简化后");
		Row rowold = sheet.getRow(0); // 遍历行
		Row rownew = sheet1.createRow(0);
		// 1、创建简化后的Sheet的，生成第一行的数据
		while (flag) {
			Cell cellold = rowold.getCell(index);
			CellStyle style = cellold.getCellStyle();
			String value = PoiStrUtil.getEffectiveVal(cellold
					.getStringCellValue()); // 过滤不需要的数据
			if (!"".equals(value)) {
				Cell cellnew = rownew.createCell(cellCounts);
				cellnew.setCellStyle(style);
				cellnew.setCellValue(value);
				cellCounts++;
			}
			if ("合计".equals(value)) {
				flag = false;
			}
			index++;
		}
		
		// 2、将数据拷贝到【简化后】Sheet中
		int row_lastNum = sheet.getLastRowNum(); // 获取最后一行的下标
		int cellindex = 1 ; 
		
		for (int i = 1; i < row_lastNum - 1; i++) {
			
			String smartVal = sheet.getRow(i + 1).getCell(4).getStringCellValue();
			if("智能机".equals(smartVal)){  //过滤列值：【非智能机】
				Row row_sheet1 = sheet1.createRow(cellindex);
				
				Cell cell_sheet0 = sheet.getRow(i + 1).getCell(0);
				Cell cell1 = row_sheet1.createCell(0);
				cell1.setCellValue(cellindex); //设置序号
				
				cell_sheet0 = sheet.getRow(i + 1).getCell(1);
				cell1 = row_sheet1.createCell(1);
				PoiUtil.copyCell(workbook, cell_sheet0, cell1, true); // 复制品牌

				cell_sheet0 = sheet.getRow(i + 1).getCell(2);
				cell1 = row_sheet1.createCell(2);
				PoiUtil.copyCell(workbook, cell_sheet0, cell1, true); // 复制型号
				for (int j = 0; j < cellCounts - 3; j++) {
					cell_sheet0 = sheet.getRow(i + 1).getCell(7 + j * 2);
					cell1 = row_sheet1.createCell(3 + j);
					if (cell_sheet0 != null) {
						PoiUtil.copyCell(workbook, cell_sheet0, cell1, true); // 复制地区数量、合计
					}
				}
				cellindex ++ ; 
			}
		}
		// 开始操作转换后的数据
		doTopCopy(workbook); 
		copySheet(workbook);
		
		System.out.println("结束");
		return workbook;

	}

	/**
	 * 建第一行数据
	 * @param workbook
	 */
	public static void doTopCopy(Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(1);
		Row row = sheet.getRow(0);
		Sheet newSheet = workbook.createSheet("转换后数据");
		Row newRow = newSheet.createRow(0);

		for (int i = 0; i < 5; i++) {
			Cell cell = row.getCell(i);
			String targetVal = cell.getStringCellValue();
			CellStyle stype = cell.getCellStyle();

			Cell newCell = newRow.createCell(i);
			newCell.setCellStyle(stype);
			if (i == 3) {
				newCell.setCellValue("地区");
			} else if (i == 4) {
				newCell.setCellValue("数量");
			} else {
				newCell.setCellValue(targetVal);
			}

		}
	}
	
	/**
	 * 开始复制【转换后】sheet的剩余数据
	 * @param workbook
	 */
	public static void copySheet(Workbook workbook){
		Sheet sheet_src = workbook.getSheetAt(1);
		Row row = sheet_src.getRow(0); 
		Sheet sheet_dist = workbook.getSheetAt(2);
		
		int lastRowNum =  sheet_src.getLastRowNum(); //0开始计算，
		int lastCellNum = sheet_src.getRow(0).getLastCellNum();
		
		String region = "" ;
		int  index = 1 ; 
		for(int i =1 ; i < lastCellNum-3 ; i++ ){
			region = row.getCell(i+2).getStringCellValue();
			for(int j = 1 ; j <=lastRowNum ; j++ ){
				Row rowDetail =  sheet_src.getRow(j);
				Row row_dist =  sheet_dist.createRow(index);
				row_dist.createCell(0).setCellValue(index);
				row_dist.createCell(1).setCellValue( rowDetail.getCell(1).getStringCellValue());
	        	row_dist.createCell(2).setCellValue(rowDetail.getCell(2).getStringCellValue());
	        	row_dist.createCell(3).setCellValue(region);
	        	row_dist.createCell(4).setCellValue(rowDetail.getCell(i+2).getNumericCellValue());
				
				index ++ ; 
			}
		}
	}



}
