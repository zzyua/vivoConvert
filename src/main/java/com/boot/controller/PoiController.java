package com.boot.controller;

import java.util.List;

import com.boot.util.PoiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.entity.Common;
import com.boot.local.RootPathThreadLocal;
import com.boot.util.FloderUtil;


@RestController
public class PoiController {
	
	@Value("${rootpath}")
	private String rootpath;


	@GetMapping(value = "/hello")
	public String say() {

//		if(1==1){
////			throw  new RestDymaicException(ResultEnum.TEST_ERROR);
//			int i = 1 / 0 ;
////			throw  new ResolverDymaicException(ResultEnum.TEST_ERROR);
//		}
		System.out.println("hellso");
		return "RootPathThreadLocal value ="+RootPathThreadLocal.getString()
				+ "  ; rootpath value =" +rootpath.replaceAll(":", "")+ Common.PREFIXPATH ;
	}
	
	@GetMapping(value = "/path")
	public String setRootPath(@RequestParam("path") String path) {
		RootPathThreadLocal.set(path);
		return  "rootpath value =" +RootPathThreadLocal.getString()+ Common.PREFIXPATH ;
		
	}
	
	

	/**
	 * 转换excel，行转列特殊转换
	 * @return
	 */
	@GetMapping(value = "/convert")
	public String doConvertExcel() {

		
		Long start = System.currentTimeMillis();
		List<String> files = FloderUtil.getFilesNames(RootPathThreadLocal.getString() , Common.PREFIXPATH);
		if(files == null || files.size() < 1){
			return "指定文件夹中没有文件，请确认!";
		}else{
			for(String filePath :files ){
				System.out.println("开始处理："+filePath);
				PoiUtil.covertFiles(filePath);
				 System.out.println("处理结束："+filePath);
			}
		}


		Long end = System.currentTimeMillis();
		return "转换结束!  耗时： " + (end-start) +"毫秒";
	}
	
	


}
