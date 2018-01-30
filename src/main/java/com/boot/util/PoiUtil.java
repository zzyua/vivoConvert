package com.boot.util;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;

import com.boot.entity.DateFormatPoi;
import com.boot.entity.Sales_volume;


public class PoiUtil {


	/**
	 * 执行转化文件的方法
	 * @param filePath
	 */
	public static void covertFiles(String filePath){
		InputStream ins = null;
		Workbook wb = null;
		FileOutputStream fileOut = null;

		if (filePath != null || !"".equals(filePath)) {
			try {
				ins = new FileInputStream(new File(filePath));
				wb = WorkbookFactory.create(ins);
				Sheet sheetc = wb.getSheet("转换后数据");
				if(sheetc != null ){
					return ;
				}
				MyWorkbookFactory.createSheet(wb);

				fileOut = new FileOutputStream(filePath);
				wb.write(fileOut);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if(ins != null )
						ins.close();
					if(wb != null)
						wb.close();
					if(fileOut != null)
						fileOut.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 解析xml, 获取excel中的数据
	 * @param sheet
	 * @return
	 */
	public static List<Sales_volume>  getAlllist(Sheet sheet){
		List<Sales_volume> listEntits = new ArrayList<Sales_volume>();
		Map<Integer , String> dateMap = PoiUtil.getDateMap(sheet);
		DecimalFormat df = new DecimalFormat("0");   //将数字格式化
		CellType  type = null ; 
		int rowNum = sheet.getLastRowNum(); //取得最大的有效行数
		String regioname = "";
		String classType = "";
		
		for(int i = 0 ; i < rowNum ; i++){
			Row row = sheet.getRow(i);
		
			//1、进行classcation的判断
			Cell typeCell = row.getCell(1);
			if(typeCell != null   &&   typeCell.getCellTypeEnum() ==  CellType.STRING  ){
				classType = typeCell.getStringCellValue();
				if("店总".equals(classType )){
					classType = "all";
				}else if( "vivo总计".equals(classType )){
					classType = "vivo";
				}else if( "oppo总计".equals(classType )){
					classType = "oppo";
				}
			}

	
			//2、获取  地区在19个设定好的行中的数据
			regioname = PoiUtil.getStringVal(row.getCell(0));
			if(regioname != null){
				//3、将获取的数据放进List集合中
				int cellNum = row.getLastCellNum(); //取得最大有效列数
				for(int j = 2; j < cellNum ; j++){
					Cell cell = row.getCell(j);
					if(cell != null){
						Integer amount  = Double.valueOf(cell.getNumericCellValue()).intValue();
						Sales_volume salesEntity = new Sales_volume();
						salesEntity.setClasscation(classType);
						salesEntity.setRegioname(regioname);
						salesEntity.setDate(dateMap.get(j));
						salesEntity.setAmount(amount);
						salesEntity.setDatatime(DateFormatPoi.Format.getDateTimeFormate().format(new Date()));
						listEntits.add(salesEntity);
					}
				}
			}

		}
		return listEntits ;

	}
	
	
	public static String getStringVal(Cell cell){

		String regin = "慈溪湖州嘉兴丽水宁波宁海平阳衢州瑞安绍兴市区台州桐庐温岭温州萧山义乌余杭舟山";
		String result = null;
		if( null == cell || null == cell.getStringCellValue()){
			
		}else{
			result = cell.getStringCellValue(); 
			if(regin.indexOf(result) <  0 ){
				result = null;
			}
		}
			return  result ;
	}
	
	
	/**
	 * 解析Sheet ,获取sheet中的日期 
	 * @param sheet
	 * @return 日期的map结构。
	 */
	public static Map<Integer , String> getDateMap(Sheet sheet){
		Map<Integer , String> dateMap = new HashedMap<Integer , String>();
		Row row = sheet.getRow(0) ;
		int cellNum = row.getLastCellNum(); //取得最大有效列数
		for( int i = 2 ; i < cellNum ; i++ ){
		 	String dateVal = PoiUtil.getDate(row.getCell(i));
			dateMap.put(i, dateVal);
		}
		return dateMap ; 
	}
	
	
	
	/**
	 * 将excel中的Numberic类型的日期格式转换为yyyy-MM的String类型日期
	 * @param Cell
	 * @return String：yyyy-MM
	 */
	public  static String getDate(Cell cell){
        String result ="";
        if(cell == null){
            return "";
        }
        CellType type = cell.getCellTypeEnum();
        if(type == CellType.NUMERIC){
        	//使用枚举单例模式来获取 SimpleDateFormat 实例
        	SimpleDateFormat format = DateFormatPoi.Format.getFormat();
        	result =  format.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
        }if(type ==CellType.STRING ){
        	result= cell.getStringCellValue();
        }
        return result;
    }
	
	
	/**
	 * 根据excel文档，获名为取“XXXX财年”的Sheet
	 * @return
	 */
	public static   Sheet getAnnualStatisSheet(Workbook book) throws Exception {
		Sheet sheet = null ; 
		boolean  notFind = true ; //定义开关
		int sheetIndex = 0 ;
		while(notFind){
			Sheet sheetc = book.getSheetAt(sheetIndex);
			sheetIndex++;
			int result = sheetc.getSheetName().indexOf("财年");
			if(result > 0 ){
				notFind = false ; 
				sheet = sheetc ;
			}
		}
		return sheet ;
	}

	/** 
     * 复制单元格 
     *  
     * @param srcCell 
     * @param distCell 
     * @param copyValueFlag 
     *            true则连同cell的内容一起复制 
     */  
    public static void copyCell(Workbook wb,Cell srcCell, Cell distCell,  
            boolean copyValueFlag) {  
    	
        // 不同数据类型处理  
        @SuppressWarnings("deprecation")
		CellType srcCellType = srcCell.getCellTypeEnum() ; //     srcCell.getCellType();  
        distCell.setCellType(srcCellType);  
        if (copyValueFlag) {  
            if (srcCellType == CellType.NUMERIC) {  
                if (HSSFDateUtil.isCellDateFormatted(srcCell)) {  
                    distCell.setCellValue(srcCell.getDateCellValue());  
                } else {  
                    distCell.setCellValue(srcCell.getNumericCellValue());  
                }  
            } else if (srcCellType == CellType.STRING ) {  
                distCell.setCellValue(srcCell.getRichStringCellValue());  
            } else if (srcCellType == CellType.BLANK) {  
                // nothing21  
            } else if (srcCellType ==CellType.BOOLEAN) {  
                distCell.setCellValue(srcCell.getBooleanCellValue());  
            } else if (srcCellType ==CellType.ERROR) {  
                distCell.setCellErrorValue(srcCell.getErrorCellValue());  
            } else if (srcCellType ==CellType.FORMULA) {  
                distCell.setCellFormula(srcCell.getCellFormula());  
            } else { // nothing29  
            	distCell.setCellType(CellType._NONE);
            }  
        }  
        
    }  
    
    
    
    /** 
     * 复制一个单元格样式到目的单元格样式 
     * @param fromStyle 
     * @param toStyle 
     */  
    public static void copyCellStyle(CellStyle fromStyle,  
            CellStyle toStyle) {  
    	
    	HorizontalAlignment  alignment = fromStyle.getAlignmentEnum();
    	toStyle.setAlignment(alignment);
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());  
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());  
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());  
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());  
        //背景和前景  
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());  
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());  
        toStyle.setDataFormat(fromStyle.getDataFormat());  
        toStyle.setHidden(fromStyle.getHidden());  
        toStyle.setIndention(fromStyle.getIndention());//首行缩进  
        toStyle.setLocked(fromStyle.getLocked());  
        toStyle.setRotation(fromStyle.getRotation());//旋转  
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignmentEnum());
//        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());  
        toStyle.setWrapText(fromStyle.getWrapText());  
          
    }  



}
