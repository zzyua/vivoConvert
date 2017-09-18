package com.boot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.entity.Common;
import com.boot.util.FloderUtil;
import com.boot.util.MyWorkbookFactory;


@RestController
public class PoiController {
	
	@Value("${rootpath}")
	private String rootpath;
	

	@GetMapping(value = "/hello")
	public String say() {
		return "rootpath value =" +rootpath.replaceAll(":", "")+ Common.PREFIXPATH ;
	}
	
	

	@GetMapping(value = "/convert")
	public String doConvertExcel() {
		
		Long start = System.currentTimeMillis();
		List<String> files = FloderUtil.getFilesNames(rootpath); 
		if(files == null || files.size() < 1){
			return "指定文件夹中没有文件，请确认!";
		}else{
			for(String filePath :files ){
				System.out.println("开始处理："+filePath);
				 covertFiles(filePath);
				 System.out.println("处理结束："+filePath);
			}
		}
		Long end = System.currentTimeMillis();
		return "转换结束!  耗时： " + (end-start) +"毫秒";
	}
	
	
	/**
	 * 执行转化文件的方法
	 * @param filePath
	 */
	public void covertFiles(String filePath){
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

}
