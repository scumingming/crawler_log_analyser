package com.isinonet.wdview.Controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by wangmingming on 2019/4/30.
 */
@Controller
@RequestMapping("/static")
public class StaticController extends BaseController  {


    @RequestMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<FileSystemResource> getImage(String rootPath, String imagePath) throws IOException {

        String filePath;
        if (imagePath.startsWith("./")) {
            filePath = rootPath + imagePath.substring(1, imagePath.length());
        } else if(imagePath.startsWith("/")) {
            filePath = rootPath + imagePath;
        } else {
            filePath = rootPath + "/" + imagePath;
        }

        File file = new File(filePath);

        if (file.exists()) {
            return ResponseEntity
                    .ok(new FileSystemResource(file));
        }
        return ResponseEntity.notFound().build();
    }

//    @RequestMapping(value = "/image2", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] getImage2(String rootPath, String imagePath) throws IOException {
////        String path =
//        File file = new File(rootPath + imagePath.substring(1, imagePath.length()));
//        FileInputStream inputStream = new FileInputStream(file);
//        byte[] bytes = new byte[inputStream.available()];
//        inputStream.read(bytes, 0, inputStream.available());
//        return bytes;
//    }

    @RequestMapping(value = "/video", produces ={"application/octet-stream"})
    public ResponseEntity<FileSystemResource> getVideo(String rootPath, String videoPath) throws IOException {

        String filePath;
        if (videoPath.startsWith("./")) {
            filePath = rootPath + videoPath.substring(1, videoPath.length());
        } else if(videoPath.startsWith("/")) {
            filePath = rootPath + videoPath;
        } else {
            filePath = rootPath + "/" + videoPath;
        }


        File file = new File(filePath);

        if (file.exists()) {
            return ResponseEntity
                    .ok(new FileSystemResource(file));
        }
        return ResponseEntity.notFound().build();
    }

}
