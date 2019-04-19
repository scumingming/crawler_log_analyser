package com.isinonet.controller;

import com.isinonet.service.CrawlerLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/19.
 */
@RestController
@RequestMapping("log")
public class CrawlerLogController {

    private static Logger logger = LoggerFactory.getLogger(CrawlerLogController.class);

    @Resource
    CrawlerLogService crawlerLogService;


    @RequestMapping("/getCrawlerLog")
    public Map<String, Object> getCrawlerLog() {

        Map<String, Object> result = new HashMap<>();

        try {

//            List<Map<String, Object>> data =  crawlerLogService.getCrawlerLog();

//            result.put("data", data);
            result.put("msg", "query scuess");

        } catch (Exception e) {
            logger.error("getCrawlerLog error:", e);
        }


        return result;

    }

}
