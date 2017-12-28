package com.boot.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boot.entity.Common;

public class FloderUtil {

	private static final Logger log = LoggerFactory.getLogger(FloderUtil.class);
	
	/**
	 * 批量转换Excel文件，行转列
	 * @param rootpath
	 * @return
	 */
	public static List<String> getFilesNames(String rootpath ,String subPath ){
		rootpath = rootpath.replaceAll(":", "")+ subPath ; 
		log.info("convert filepath : {} " , rootpath);
		List<String> files = new ArrayList<String>();
		String path =rootpath; // 路径
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return files;
		}
		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			if (fs.isDirectory()) {
				//System.out.println(fs.getName() + " [目录]");
			} else {
				//System.out.println(fs.getName());
				files.add(rootpath+"\\"+fs.getName());
			}
		}
		return files;
	}
	
	/**
	 * 自动生成移动或电信的结果文件夹
	 * @param rootPath
	 * @param resultType mobile:移动  ; telecom:电信 ; 其余文件夹根据xls命名
	 */
	public static String createResultFloder(String rootPath ,String resultType) {
		if(Common.MOBILETYPE.equals(resultType)) {
			rootPath = rootPath.replaceAll(":", "") +Common.MOBILERESULTFOLDER;
		}else if(Common.TELECOMTYPE.equals(resultType)){
			rootPath = rootPath.replaceAll(":", "") +Common.TELECOMRESULTFOLDER;
		}else {
			rootPath = rootPath.replaceAll(":", "")+resultType;
		}
		File file = new File(rootPath);
		if(!file.exists()) {
			 file.mkdir();
			 log.info("文件夹--> {},不存在，自动创建该文件夹",rootPath);
		}
		return rootPath ;
	}
	
	
	/**
	 * 创建Txt文件名字
	 * @param rootPath
	 * @param filePathName
	 * @return
	 */
	public static File createTxtFile(String filePathName) {
		File file = new File(filePathName);
		if(!file.exists()) {
			try {
				 file.createNewFile();
			} catch (IOException e) {
				file = null ;
				log.error("创建文件:{}失败了",file);
				e.printStackTrace();
			}
		}
		return file;
	}
	
	
}











