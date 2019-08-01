package com.zhoug.widget.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 描述：文件工具类
 * zhougan
 * 2019/3/16
 **/
public class FileUtil {
    private static final String TAG = "FileUtil";
    /**图片*/
    public static final int TYPE_IMAGE=1;
    /**视频*/
    public static final int TYPE_VIDEO=2;
    /**音频*/
    public static final int TYPE_AUDIO=3;
    /**未知*/
    public static final int TYPE_UNKNOWN=-1;

    /**
     * 通过文件后缀/路劲获取文件类型
     * @param suffix
     * @return {@link #TYPE_IMAGE,#TYPE_VIDEO,#TYPE_AUDIO,#TYPE_UNKNOWN}
     */
    public static int getFileType(String suffix){
        if(StringUtil.isEmpty(suffix))
            return TYPE_UNKNOWN;
        //获取后缀
        if(suffix.contains(".")){
            int index = suffix.lastIndexOf(".");
            suffix = suffix.substring(index+1);
        }

        if(suffix.equalsIgnoreCase("JPG")||
                suffix.equalsIgnoreCase("JPEG")||
                suffix.equalsIgnoreCase("PNG")||
                suffix.equalsIgnoreCase("WEBP")||
                suffix.equalsIgnoreCase("GIF ")||
                suffix.equalsIgnoreCase("BMP ")
                ){
            return TYPE_IMAGE;
        }
        if(suffix.equalsIgnoreCase("WEBM")||
                suffix.equalsIgnoreCase("3GP")||
                suffix.equalsIgnoreCase("MP4")||
                suffix.equalsIgnoreCase("MKV")
                ){
            return TYPE_VIDEO;
        }
        if(suffix.equalsIgnoreCase("AAC")||
                suffix.equalsIgnoreCase("OGG")||
                suffix.equalsIgnoreCase("MP3")||
                suffix.equalsIgnoreCase("amr")||
                suffix.equalsIgnoreCase("WAV")
                ){
            return TYPE_AUDIO;
        }

        return TYPE_UNKNOWN;

    }


    /**
     * 获取文件名(包括后缀名)
     * @param file
     * @return
     */
    public static String getNameWithSuffix(File file){
        if(file==null){
            return null;
        }
        return file.getName();
    }

    /**
     * 获取文件名(包括后缀名)
     * @param path
     * @return
     */
    public static String getNameWithSuffix(String path){
        if(path==null || path.length()==0){
            return null;
        }
        return getNameWithSuffix(new File(path));

    }


    /**
     * 获取文件名(不包括后缀)
     * @param path
     * @return
     */
    public static String getNameWithOutSuffix(String path){
        String nameWithType = getNameWithSuffix(path);
        if(nameWithType!=null){
            int endIndex = nameWithType.lastIndexOf(".");
            if(endIndex>0 && endIndex<nameWithType.length()){
                return nameWithType.substring(0,endIndex);
            }else{
                return nameWithType;
            }
        }else{
            return null;
        }
    }

    /**
     * 获取文件名(不包括后缀)
     * @param file
     * @return
     */
    public static String getNameWithOutSuffix(File file){
       return file==null ? null : getNameWithOutSuffix(file.getAbsolutePath());
    }

    /**
     * 获取文件的后缀
     * @param path
     * @return
     */
    public static String getSuffix(String path){
        String nameWithType = getNameWithSuffix(path);
        if(nameWithType==null){
            return null;
        }else{
            int index = nameWithType.lastIndexOf(".");
            if(index>0 && index<nameWithType.length()-1){
                return nameWithType.substring(index+1);
            }else{
                return null;//无后缀
            }
        }

    }

    /**
     * 获取文件的后缀
     * @param file
     * @return
     */
    public static String getSuffix(File file){
        return file==null ? null : getSuffix(file.getAbsolutePath());
    }

    /**
     * 创建文件夹
     * @param file
     * @return 是否创建成功
     */
    public static boolean createDirectory(File file){
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return true;//文件夹存在
        }
    }

    /**
     * 创建文件夹
     * @param absolutePath
     * @return 是否创建成功
     */
    public static boolean createDirectory(String absolutePath){
        return createDirectory(new File(absolutePath));
    }



    /**
     * 创建文件
     * @param file
     * @return 是否创建成功
     */
    public static boolean createFile(File file){
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            boolean mkdirs = parentFile.mkdirs();
            if(mkdirs){
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 创建文件
     * @param path
     * @return 是否创建成功
     */
    public static boolean createFile(String path){
       return createFile(new File(path));
    }


    public static boolean sdCardExist(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取外部储存根目录下的文件
     * @param folder 文件夹
     * @param file 文件
     * @return
     */
    public static File getExternalFile(String folder,String file){
        if(sdCardExist()){
            File root = Environment.getExternalStorageDirectory();
            File file1=new File(root,folder);
            if(!file1.exists()){
                boolean mkdirs = file1.mkdirs();
                if(mkdirs){
                    return new File(file1, file);
                }else{
                    return null;
                }
            }else{
                return new File(file1, file);
            }
        }else{
            return null;
        }

    }

    /**
     * 获取外部储存根目录下的文件
     * @param path 文件路径
     * @return
     */
    public static File getExternalFile(String path){
        if(sdCardExist()){
            File root = Environment.getExternalStorageDirectory();
            File file=new File(root,path);
            if(!file.getParentFile().exists()){
                boolean mkdirs = file.getParentFile().mkdirs();
                if(mkdirs){
                    return new File(path);
                }
            }else{
                return new File(path);
            }
        }
        return null;
    }

    /**
     * 获取外部储存文件路径 根目录下的folder文件夹
     * @param folder
     * @return folder的路径
     */
    public static String getExternalFolderPath(String folder){
        if(folder==null){
            return null;
        }
        if(sdCardExist()){
            File root = Environment.getExternalStorageDirectory();
            File file=new File(root,folder);
            //文件不存在则创建文件
            if(!file.exists()){
                boolean mkdirs = file.mkdirs();
                if(mkdirs){
                    return file.getAbsolutePath();
                }else{
                    return null;
                }
            }else{
                return file.getAbsolutePath();
            }

        }else{
            return null;
        }
    }


    /**
     * 在外部sdcard根目录上创建文件夹
     * @param folder
     * @return
     */
    public static File createExternalFolder(String folder){
        if(sdCardExist()){
            File root = Environment.getExternalStorageDirectory();
            File file=new File(root,folder);
            if(!file.exists()){
                boolean mkdirs = file.mkdirs();
                if(mkdirs){
                    return file;
                }else{
                    return null;
                }
            }else{
               return file;
            }
        }else{
            return null;
        }
    }

    /**
     * 获取外部公用文件夹下的文件
     * @param type 文件夹类型 例：Environment.DIRECTORY_PICTURES
     * @param fileName 文件名
     * @return
     */
    public static File getExternalPublicFile(String type,String fileName){
        if(sdCardExist()){
            File root = Environment.getExternalStoragePublicDirectory(type);
            if(!root.exists()){
                boolean mkdirs = root.mkdirs();
                if(mkdirs){
                    return new File(root,fileName);
                }else{
                    return null;
                }
            }else{
                return new File(root,fileName);
            }

        }else{
            return null;
        }
    }

    public static File getExternalPublicFolder(String type){
        if(sdCardExist()){
            File root = Environment.getExternalStoragePublicDirectory(type);
            if(!root.exists()){
                root.mkdirs();
            }
            return root;
        }else{
            return null;
        }
    }


    /**
     * 获取文件的mimeType 用于设置intent.setDataAndType(uri,type) 中的type;
     * @param path
     * @return 中的type
     */
    public static String getMIMEType(String path){
        String mimeType="*/*";
        String fileType = getSuffix(path);//文件后缀
        if(fileType!=null){
            for(int i=0;i<MIMEType.length;i++){
                if(fileType.equalsIgnoreCase(MIMEType[i][0])){
                    mimeType=MIMEType[i][1];
                    break;
                }
            }
        }
        return  mimeType;
    }

    private static final String[][] MIMEType={
            {"jpg","image/*"},
            {"jpeg","image/*"},
            {"png","image/*"},
            {"bmp","image/*"},
            {"gif","image/*"},

            {"mp4","video/*"},
            {"3gp","video/*"},

            {"mp3","video/*"},

            {"txt","text/plain"},
            {"doc","application/msword"},
            {"docx","application/msword"},
            {"pdf","application/pdf"},

    };

    /**
     * 获取外部缓存
     * @param context
     * @param folder
     * @return
     */
    public static File getExternalCacheFolder(Context context,String folder){
        File cacheDir = context.getExternalCacheDir();
        File file=cacheDir;
        if(!StringUtil.isEmpty(folder)){
             file=new File(cacheDir,folder);
        }
        if( file !=null){
            if(!file.exists()){
                if(file.mkdirs()){
                    return file;
                }
            }else{
                return file;
            }
        }
        return null;
    }

    /**
     * 获取文件夹下面所有文件的大小
     * @param file
     * @return
     */
    public static  long  getFileSize(File file){
        if(!file.exists()){
            return 0;
        }
        long size=0;
        if(file.isFile()){
            return file.length();
        }else{
            File[] files = file.listFiles();
            for(File file1: files){
                size+= getFileSize(file1);
            }
        }
//        Log.d(TAG, "getFileSize: size="+size);
        return size;
    }



    /**
     * 把字节大小转化为 B K M
     * @param size
     * @return
     */
    public static String getFormatFileSize(long size){
//        Log.d(TAG, "getFormatSize: size="+size);
        double kb = size / 1024;
        if (kb < 1) {
            return size+"B";
        }

        double mb = kb / 1024;
        if (mb < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kb));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gb = mb / 1024;
        if (gb < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(mb));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double tb = gb / 1024;
        if (tb < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gb));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(tb);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 获取app外部和内部缓存大小
     * @param context
     * @return
     */
    public static String getAppCacheSize(Context context){
        long size=0;
        //外部存储的缓存
        if(sdCardExist()){
            File externalCacheDir = context.getExternalCacheDir();
            if(externalCacheDir!=null && externalCacheDir.exists()){
                size+= FileUtil.getFileSize(externalCacheDir);
            }
        }
        //内部存储的缓存
        File cacheDir = context.getCacheDir();
        if(cacheDir!=null && cacheDir.exists()){
            size+= FileUtil.getFileSize(cacheDir);
        }
        String formatSize = FileUtil.getFormatFileSize(size);
        Log.d(TAG, "getAppCacheSize: size="+size);

        Log.d(TAG, "getAppCacheSize: formatSize="+formatSize);
        return formatSize;
    }

    /**
     * 删除文件/或文件夹下所有文件
     * @param file
     */
    public static void deleteFile(File file){
        if(!file.exists()){
            return;
        }
        if(file.isFile()){
//            Log.d(TAG, "deleteFile: ");
            boolean delete = file.delete();
        }else{
            File[] files = file.listFiles();
            for(File file1:files){
                deleteFile(file1);
            }
        }
    }

    /**
     * 清除app的缓存[外部存储的缓存:context.getExternalCacheDir+内部存储的缓存:context.getCacheDir()]
     * @param context
     */
    public static void deleteAppCache(Context context){
        //外部存储的缓存
        if(sdCardExist()){
            File externalCacheDir = context.getExternalCacheDir();
            if(externalCacheDir!=null && externalCacheDir.exists()){
                deleteFile(externalCacheDir);
            }
        }
        //内部存储的缓存
        File cacheDir = context.getCacheDir();
        if(cacheDir!=null && cacheDir.exists()){
            deleteFile(cacheDir);
        }
    }

}
