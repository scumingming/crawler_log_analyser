package com.isinonet.wdview.Service;


import com.isinonet.wdview.request.BaseRequest;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/4/29.
 */
public interface TwitterService extends InsertService{



    int insert(String filePath, Map<String, Object> map) ;


    /**
     * 新增关键字的帖子个数
     * @param request
     * @return
     */
    int dataCount(BaseRequest request) ;


    /**
     * 帖子列表
     * @param request
     * @return
     */
    List<Map<String, Object>> dataList(BaseRequest request) ;




    /**
     * 帖子作者简介
     * @param userId
     * @return
     */
    Map<String, Object> getAboutData(String userId) ;

    /**
     * 帖子评论
     * @param tweetId
     * @return
     */
    List<Map<String, Object>> getCommentData(String tweetId);
    /**
     * 作者粉丝
     * @param userId
     * @return
     */
    List<Map<String, Object>> getFollowerData(String userId);

    /**
     * 作者关注列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> getFollowingData(String userId);

    List<Map<String, Object>> getDataByIds(String ids);

    List<Map<String, Object>> getCommentById(String id);


    void exports(HttpServletResponse response, String ids, String saveRootpath) ;


}
