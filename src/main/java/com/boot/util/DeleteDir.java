package com.boot.util;

import java.io.File;

/**
 * 删除本地文件夹或者文件
 * @author Administrator
 *
 */
public class DeleteDir {


    // 验证字符串是否为正确路径名的正则表达式
    private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
    // 通过 sPath.matches(matches) 方法的返回值判断是否正确
    // dirPath 为路径字符串

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dirPath){
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!dirPath.endsWith(File.separator)) {
            dirPath = dirPath + File.separator;
        }
        File dirFile = new File(dirPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    /**
     * 删除文件夹方法
     * @return 删除结果
     */
    public static  boolean  deleteDir(File dir) {
        //判断文件夹是否存在
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();

    }




    public static void main(String[] args) {

        DeleteDir dd = new DeleteDir();
        File file = new File("D:\\DevepTools\\apache-tomcat-6.0.35\\work\\Catalina\\localhost\\v515");
        boolean result = dd.deleteDir(file);

        if(result)
            System.out.println("删除文件夹V515成功" );
        else
            System.out.println("删除失败");

    }
}
