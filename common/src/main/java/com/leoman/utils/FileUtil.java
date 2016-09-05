package com.leoman.utils;

import com.leoman.entity.Configue;
import com.leoman.entity.FileBo;
import com.leoman.image.entity.Image;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangbin on 2014/12/6.
 */
public class FileUtil {

    public static void compactImage(File srcFile, String destDir, String destImage) throws IOException {
        Thumbnails.of(srcFile).scale(1f).outputQuality(0.5).toFile(new File(destDir, destImage));
    }

    public static void thumbImage(File srcFile, String destDir, String destImage, int width, int height) throws IOException {
        BufferedImage srcImage = ImageIO.read(srcFile);
        Thumbnails.of(srcImage)
                .size(width, height)
                .outputQuality(1)
                .keepAspectRatio(false)
                .toFile(new File(destDir, destImage));
    }


    public static void changeImgPath(Image image) {
        if (image == null) {
            return;
        }
        String path = image.getPath();
        if (path.contains(Configue.getUploadUrl())) {
            return;
        }
        image.setPath(Configue.getUploadUrl() + image.getPath());
    }

    public static Map<String, Long> getImgInfo(String imgPath) {
        File file = new File(imgPath);
        return getImgInfo(file);
    }


    public static Map<String, Long> getImgInfo(File imgFile) {
        Map<String, Long> map = new HashMap<String, Long>(3);
        try {
            FileInputStream fis = new FileInputStream(imgFile);
            BufferedImage buff = ImageIO.read(imgFile);
            map.put("width", buff.getWidth() * 1L);
            map.put("height", buff.getHeight() * 1L);
            map.put("size", imgFile.length());
            fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("所给的图片文件" + imgFile.getPath() + "不存在！计算图片尺寸大小信息失败！");
            map = null;
        } catch (IOException e) {
            System.err.println("计算图片" + imgFile.getPath() + "尺寸大小信息失败！");
            map = null;
        }
        return map;
    }

    /**
     * @param fileIn     要被copy的文件
     * @param fileOutPut 将文件copy到那个目录下面
     * @throws Exception
     */
    public static void copyFile(File fileIn, File fileOutPut) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(fileIn);
        FileOutputStream fileOutputStream = new FileOutputStream(fileOutPut);
        byte[] by = new byte[1024];
        int len;
        while ((len = fileInputStream.read(by)) != -1) {
            fileOutputStream.write(by, 0, len);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static File fileIsExist(String proId,String salemanId) {
        String path = ConfigUtil.getString("upload.path") + KdxgUtils.getFileName(proId, salemanId);
        File file = new File(path);
        if(file.exists()) {
            return file;
        }
        return null;
    }

    public static String getFileExt(String fileName){

        int index=fileName.lastIndexOf('.');
        if(index==-1){
            return "";
        }
        String ext=fileName.substring(index);
        return ext;
    }


    public static boolean isImage(String fileName){
        return fileName.matches("(?i).+?\\.(jpg|gif|bmp|jpeg|png)");
    }

    public static String  readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));

            StringBuffer sb = new StringBuffer();

            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r'&&((char) tempchar) != '\n') {
                    sb.append(String.valueOf((char) tempchar));
                }
            }
            reader.close();
            return sb.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

    public static FileBo save(MultipartFile file, String fileName) throws IOException{
        FileBo fileBo = new FileBo();
        InputStream inputStream = file.getInputStream();
        String origFileName = file.getOriginalFilename(); //原始名称,如aa.jpg
        String ext =  FileUtil.getFileExt(origFileName); //后缀，如jpg
        String uploadPath = UploadUtil.getImagesUpladPath(); //生成日期目录 image/2014/7/21/
        String foreName = uploadPath+fileName;   //文件名称 image/2014/7/21/221144144554
        String desFilePathName = uploadPath+fileName+ext;//完整文件名称 image/2014/7/21/221144144554.jpg
        File theFile = new File(Configue.getUploadPath(),desFilePathName); //生成的文件对象
        fileBo.setName(fileName);
        fileBo.setForeName(foreName);
        fileBo.setExt(ext);
        fileBo.setPath(desFilePathName);
        fileBo.setFile(theFile);
        FileUtils.copyInputStreamToFile(inputStream,theFile);
        return fileBo;
    }

    public static FileBo save(MultipartFile file) throws IOException{
        return save(file,String.valueOf(System.currentTimeMillis()));
    }
}
