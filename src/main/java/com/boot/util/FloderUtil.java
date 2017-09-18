package com.boot.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boot.entity.Common;

public class FloderUtil {

	private static final Logger log = LoggerFactory.getLogger(FloderUtil.class);
	
	public static List<String> getFilesNames(String rootpath ){
		rootpath = rootpath.replaceAll(":", "")+ Common.PREFIXPATH ; 
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
}
