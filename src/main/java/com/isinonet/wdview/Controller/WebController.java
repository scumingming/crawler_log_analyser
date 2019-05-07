package com.isinonet.wdview.Controller;

import com.isinonet.wdview.Service.WebService;
import com.isinonet.wdview.request.AjaxResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */
@RestController
@RequestMapping("/web")
public class WebController extends BaseController  {

    @Resource
    WebService webService;

    @RequestMapping("/dataCount")
    public AjaxResponse dataCount(String keyWords, String startTime, String endTime) {

        int data = webService.dataCount(keyWords, startTime, endTime);

        return AjaxResponse.ok("success!").data(data);
    }

    @RequestMapping("/getDatas")
    public AjaxResponse getDatas(String keyWords, String startTime, String endTime) {

        List<Map<String, Object>> data = webService.getDatas(keyWords, startTime, endTime);

        return AjaxResponse.ok("success!").data(data);
    }


    @RequestMapping("/exports")
    public void exports(HttpServletResponse response, String ids, String path) {

        webService.exports(response, ids, path);

    }

//    @RequestMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] getImage(String rootPath, String imagePath) throws IOException {
////        String path =
//        File file = new File(rootPath + imagePath.substring(1, imagePath.length()));
//        FileInputStream inputStream = new FileInputStream(file);
//        byte[] bytes = new byte[inputStream.available()];
//        inputStream.read(bytes, 0, inputStream.available());
//        return bytes;
//    }



//    @RequestMapping(value = "/images", produces = {"application/octet-stream"})
//    @ResponseBody
//    public BufferedImage images(String rootPath, String imagePath) throws IOException {
//
//        return ImageIO.read(new FileInputStream(new File(rootPath + imagePath.substring(1, imagePath.length()))));
//
//    }


//    @RequestMapping(value = "/video", produces = {"application/octet-stream"})
//    public byte[] getVideo(String rootPath, String videoPath) throws IOException {
//        File file = new File(rootPath + videoPath.substring(1, videoPath.length()));
//        FileInputStream inputStream = new FileInputStream(file);
//        byte[] bytes = new byte[inputStream.available()];
//        inputStream.read(bytes, 0, inputStream.available());
//        return bytes;
//    }






}
