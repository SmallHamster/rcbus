package com.leoman.common.controller.common;

import com.leoman.image.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
@Controller
@RequestMapping("/gen")
public class GeneralController extends CommonController{

    @Autowired
    private UploadImageService imageService;

    /**
     * 上传单张图片
     * @param request
     * @param response
     * @param file
     */
    @RequestMapping("/save/image")
    public void saveImage(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(required=false) MultipartFile file){
        try {
            System.out.println("------------进入上传图片方法------------");
            /*ImageBo imageBo = defaultUploadImage.uploadImage(file.getOriginalFilename(),file.getInputStream(),512000);
            Image image = new Image();
            image.setPath(imageBo.getPath());
            image.setAttribute(imageBo.getAttribute());
            image.setThumbs(imageBo.getThumbs());
            imageService.create(image);
            image.setPath(Configure.getUploadUrl()+image.getPath());
            WebUtil.print(response, new Result(true).data(image).msg("上传图片成功!"));*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
