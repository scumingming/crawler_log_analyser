package com.isinonet.wdview.Service;



import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by zouchen on 2019/4/22.
 */

public interface WebService  extends InsertService{


    int dataCount(String keyWords, String startTime, String endTime) ;

    List<Map<String, Object>> getDatas(String keyWords, String startTime, String endTime);

    List<Map<String, Object>> getDataByIds(String ids) ;

    void exports(HttpServletResponse response, String ids, String saveRootpath) ;

    int insertBatch(String filePath, List<Map<String, Object>> list) ;

    int insert(String filePath, Map<String, Object> map) ;

}
