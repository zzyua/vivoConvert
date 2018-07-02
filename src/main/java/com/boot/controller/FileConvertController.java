package com.boot.controller;

import com.boot.entity.Common;
import com.boot.exception.RestDymaicException;
import com.boot.exception.Result;
import com.boot.exception.ResultEnum;
import com.boot.exception.ResultUtil;
import com.boot.util.DeleteDir;
import com.boot.util.PoiUtil;
import com.boot.util.RandUtil;
import com.boot.util.ZipUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class FileConvertController {

    private final  Logger logger = LoggerFactory.getLogger(FileConvertController.class);

    @Value("${uploadFilePath}")
    private String uploadFilePath;


    /**
     * 打开单个文件上传页面
     * @return 上传文件页面
     */
    @GetMapping("/upload")
    public String upload() {
        return "fileupload";
    }




    @PostMapping("/upload.json")
    @ResponseBody
    public Result upload(MultipartHttpServletRequest request , HttpServletResponse response) throws  Exception{

        Iterator<String> fileNames =  request.getFileNames();



        String folderName = RandUtil.getFilePath();
        //上传文件夹的路径  uploadFilePath + 时间戳
        String path = uploadFilePath + folderName;
        File mkdir = new File(path);
        //创建目录
        if(!mkdir.exists()){
            mkdir.mkdirs();
        }
        try {
            //1、上传文件
            List<Map<String,String>>  fileNameList =  uploadFiles(request, fileNames, path);

            //2、进行文件的转换
            fileNameList.forEach(map ->  PoiUtil.covertFiles(path+File.separator+ map.get(Common.FILENAME))  );

            //3、压缩文件夹 以及 下载zip文件
            response.setContentType("application/zip");// 定义输出类型
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + folderName+".zip");
            ZipUtils.toZip(path , response.getOutputStream(),false);
        }catch (RestDymaicException e){
           throw  e ;
        }catch (Exception e) {
            e.printStackTrace();
            throw new RestDymaicException(ResultEnum.CONVERTFILE_ERROR);
        }finally {
            //4、 删除服务器中的文件
//            DeleteDir.deleteDirectory(path);
        }
        return ResultUtil.success();
    }





    /**
     *
     * @param request requset
     * @param fileNames fileNames
     * @param path path
     * @return  fileShortNames 文件命列表 ： prfix.suffix
     */
    private List<Map<String,String>> uploadFiles(MultipartHttpServletRequest request, Iterator<String> fileNames, String path)  {

        List<Map<String,String>> fileShortNames = new ArrayList<>();

        fileNames.forEachRemaining( name ->  {
            List<MultipartFile> fileList = request.getFiles(name);

            fileList.forEach(multipartFie -> {
                String fileName = multipartFie.getOriginalFilename();
                if(StringUtils.isEmpty(fileName)){
                    throw  new RestDymaicException(ResultEnum.CONVERTFILESISEMPTY);
                }

                System.out.println("fileName: "+fileName);
                Map<String,String > map = new HashMap<>();
                map.put(Common.PREFIX,fileName.substring(0, fileName.lastIndexOf("."))) ;
                map.put(Common.SUFFIX,fileName.substring(fileName.lastIndexOf("."), fileName.length()));
                map.put(Common.FILENAME, fileName) ;
                fileShortNames.add(map);

                //文件名
                String filePath = path + File.separator + fileName ;
                File file = new File(filePath);
                if(!file.exists()){
                    try {
                        //将文件拷贝到服务器
                        multipartFie.transferTo(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        });

        return  fileShortNames ;
    }


    public static void main(String[] args){
        String a = "数据ss.ks.kks.sls";
        System.out.println(a.substring(0, a.lastIndexOf(".")));
        System.out.println(a.substring(a.lastIndexOf("."), a.length()));
    }


}
