package com.boot.controller;

import com.boot.entity.Common;
import com.boot.util.Commom;
import com.boot.util.DeleteDir;
import com.boot.util.PoiUtil;
import com.boot.util.RandUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
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
     * @return
     */
    @GetMapping("/upload")
    public String upload() {
        return "/fileupload";
    }




    @PostMapping("/upload")
    @ResponseBody
    public String upload(MultipartHttpServletRequest request , HttpServletResponse response) {

        Iterator<String> fileNames =  request.getFileNames();

        //上传文件夹的路径  uploadFilePath + 时间戳
        String path = uploadFilePath + RandUtil.getFilePath();
        File mkdir = new File(path);
        //创建目录
        if(!mkdir.exists()){
            mkdir.mkdirs();
        }

        try {
            //1、上传文件
            List<Map<String,String>>  fileNameList =  uploadFiles(request, fileNames, path);

            //2、进行文件的转换
            fileNameList.stream().forEach(map -> {
                PoiUtil.covertFiles(path+File.separator+ map.get(Common.FILENAME));
            });

            //3、 下载文件
            downLoadFiles(response, path, fileNameList);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //4、 删除服务器中的文件
//            DeleteDir.deleteDirectory(path);
        }







        return "jello ";

    }

    /**
     * 下载服务文件方法
     * @param response
     * @param path
     * @param fileNameList
     */
    private void downLoadFiles(HttpServletResponse response, String path, List<Map<String, String>> fileNameList) {
        fileNameList.stream().forEach(map -> {
            String fileName = "";
            try {
                String   prefix =  new String(map.get(Common.PREFIX).getBytes(),"iso-8859-1") ;
                String  suffix  = map.get(Common.SUFFIX);
                fileName = prefix + suffix ;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" +fileName);

            response.setContentType("application/octet-stream;charset=utf-8");
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;

            OutputStream os = null;
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(path+File.separator+map.get(Common.FILENAME)));
                os = response.getOutputStream();
                bis = new BufferedInputStream(fileInputStream);

                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }

                //修正 Excel在“xxx.xlsx”中发现不可读取的内容。是否恢复此工作薄的内容？如果信任此工作簿的来源，请点击"是"
                response.setHeader("Content-Length", String.valueOf(fileInputStream.getChannel().size()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    /**
     *
     * @param request
     * @param fileNames
     * @param path
     * @return  fileShortNames 文件命列表 ： prfix.suffix
     * @throws Exception
     */
    private List<Map<String,String>> uploadFiles(MultipartHttpServletRequest request, Iterator<String> fileNames, String path)  throws  Exception{

        List<Map<String,String>> fileShortNames = new ArrayList<>();

        fileNames.forEachRemaining( name ->  {
            List<MultipartFile> fileList = request.getFiles(name);

            fileList.stream().forEach(multipartFie -> {
                String fileName = multipartFie.getOriginalFilename();

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
