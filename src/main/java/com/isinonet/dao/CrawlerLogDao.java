package com.isinonet.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/19.
 */
@Repository
public interface CrawlerLogDao {

    List<Map<String, Object>> getCrawlerLog();

}
