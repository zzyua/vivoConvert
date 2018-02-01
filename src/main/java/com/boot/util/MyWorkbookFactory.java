package com.boot.util;

import com.boot.entity.Common;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * 用来创建Workbook的工厂类
 */
public class MyWorkbookFactory {

	/**
	 * 生成简化的后的Sheet
	 * @param workbook workbook
	 */
	public static void createSheet(Workbook workbook) {

		boolean flag = true;
		int index = 0;
		int effectiveCellCounts = 0; // 记录地区数量
		Sheet firstSheet = workbook.getSheetAt(0); //原始的sheet
		int SmartMachineIndex = -1 ; //智能机对应列
		int SerialNumberIndex = -1 ; //序号对应列数
		int brandCellIndex = -1 ; //品牌 对应列
		int modelCellIndex = -1 ; //型号 对应列
		int firstAreaCellIndex = -1 ; //第一次出现地区的列下表
		boolean getFirstAreaIndex = false ;
		String firstSheetOneRowCellTip = "" ;  // 记录原始sheet中第一行的单元格标题


		Sheet afterSimplifySheet = workbook.createSheet("简化后");
		Row firstSheetRowAtFirst = firstSheet.getRow(0); // 遍历行
		Row afterSimplifySheetRowAtFirst = afterSimplifySheet.createRow(0);
		// 1、创建简化后的Sheet的，生成第一行的数据
		while (flag) {
			Cell firstSheetCellTipName = firstSheetRowAtFirst.getCell(index);  // 原始sheet中第一行的单元格
			CellStyle style = firstSheetCellTipName.getCellStyle();
			firstSheetOneRowCellTip = firstSheetCellTipName.getStringCellValue();

			// 根据单元格名称，记录对应列  开始
			if(Common.SMARTMACHIN.equals(firstSheetOneRowCellTip)){
				SmartMachineIndex = index ;
			}
			if(Common.SERIALNUMBER.equals(firstSheetOneRowCellTip)){
				SerialNumberIndex = index ;
			}
			if(Common.BRAND.equals(firstSheetOneRowCellTip)){
				brandCellIndex = index ;
			}
			if(Common.MODEL.equals(firstSheetOneRowCellTip)){
				modelCellIndex = index ;
			}
			if(!getFirstAreaIndex &&  firstSheetOneRowCellTip.indexOf("]") > -1 ){
				firstAreaCellIndex = index ;
				getFirstAreaIndex = true ;
			}
			// end


			String value = PoiStrUtil.getEffectiveVal(firstSheetOneRowCellTip); // 过滤不需要的数据
			if (!"".equals(value)) {
				Cell cellnew = afterSimplifySheetRowAtFirst.createCell(effectiveCellCounts);
				cellnew.setCellStyle(style);
				cellnew.setCellValue(value);
				effectiveCellCounts++;
			}
			if ("合计".equals(value)) {
				flag = false;
			}
			index++;
		}

		System.out.println("effectiveCellCounts=========="+effectiveCellCounts);

		// 2、将数据拷贝到【简化后】Sheet中
		int firstSheetMaxRownum = firstSheet.getLastRowNum(); // 获取最后一行的下标
		int cellindex = 1 ;

		
		for (int i = 1; i < firstSheetMaxRownum - 1; i++) {

			String smartVal = firstSheet.getRow(i + 1).getCell(SmartMachineIndex).getStringCellValue();
			if(Common.SMARTMACHIN.equals(smartVal)){  //仅筛选出 机型是【智能机】的数据
				Row afterSimplifySheetRow = afterSimplifySheet.createRow(cellindex);
				
				Cell cell_sheet0 = firstSheet.getRow(i + 1).getCell(SerialNumberIndex);
				Cell cell1 = afterSimplifySheetRow.createCell(0);
				cell1.setCellValue(cellindex); //设置序号
				
				cell_sheet0 = firstSheet.getRow(i + 1).getCell(brandCellIndex);
				cell1 = afterSimplifySheetRow.createCell(1);
				PoiUtil.copyCell(workbook, cell_sheet0, cell1, true); // 复制品牌

				cell_sheet0 = firstSheet.getRow(i + 1).getCell(modelCellIndex);
				cell1 = afterSimplifySheetRow.createCell(2);
				PoiUtil.copyCell(workbook, cell_sheet0, cell1, true); // 复制型号
				for (int j = 0; j < effectiveCellCounts - 3; j++) {//3 代表 【序号】、【品牌】、【型号】三列
					cell_sheet0 = firstSheet.getRow(i + 1).getCell(firstAreaCellIndex + j * 2);
					cell1 = afterSimplifySheetRow.createCell(3 + j);
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
