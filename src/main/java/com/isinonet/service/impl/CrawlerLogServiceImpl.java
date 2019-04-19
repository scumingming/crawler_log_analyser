package com.isinonet.service.impl;

import com.isinonet.dao.CrawlerLogDao;
import com.isinonet.service.CrawlerLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/19.
 */
@Service("crawlerLogService")
public class CrawlerLogServiceImpl implements CrawlerLogService {

    @Resource
    CrawlerLogDao crawlerLogDao;

    @Override
    public List<Map<String, Object>> getCrawlerLog() {


        List<Map<String, Object>> data = crawlerLogDao.getCrawlerLog();

        return data;
    }
}
