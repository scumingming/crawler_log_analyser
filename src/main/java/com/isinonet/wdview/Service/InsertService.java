package com.isinonet.wdview.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */
public interface InsertService {


    int insertBatch(String filePath, List<Map<String, Object>> list);

    int insert(String filePath, Map<String, Object> obj);

}
