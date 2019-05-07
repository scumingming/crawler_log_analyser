package com.isinonet.wdview.Task;

import com.alibaba.fastjson.JSON;
import com.isinonet.wdview.Service.FacebookService;
import com.isinonet.wdview.Service.InsertService;
//import com.isinonet.wdview.Service.TwitterService;
import com.isinonet.wdview.Service.TwitterService;
import com.isinonet.wdview.Service.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */
@Service
public class FileHanlder {

    private static Logger logger = LoggerFactory.getLogger(FileHanlder.class);

    @Resource
    private TwitterService twitterService;

    @Resource
    private WebService webService;

    @Resource
    FacebookService facebookService;

    public void handleTwitterFile(String filePath) {
        System.out.println(filePath);
        readFile(filePath, twitterService);

    }

    public void handleFacebookFile(String filePath) {
        System.out.println(filePath);
        readFile(filePath, facebookService);

    }


    public void handleWebFile(String filePath) {
        System.out.println(filePath);
        readFile(filePath, webService);

    }


    public void readFile(String filePath, InsertService insertService) {
        logger.info("readFile: " + filePath);
        FileReader fileReader ;
        BufferedReader bufferedReader ;
        try{
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);
//            List<Map<String, Object>> list = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Map<String, Object> map = JSON.parseObject(line, Map.class);
                insertService.insert(filePath, map);
//                list.add(map);
//                if(list.size() >= 50) {
//                    insertService.insert(list);
//                    list.clear();
//                }
            }

//            if(list.size() > 0) {
//                insertService.insert(list);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
