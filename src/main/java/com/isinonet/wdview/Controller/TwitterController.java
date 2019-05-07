package com.isinonet.wdview.Controller;

import com.isinonet.wdview.Service.TwitterService;
import com.isinonet.wdview.request.AjaxResponse;
import com.isinonet.wdview.request.BaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/5/5.
 */
@RestController
@RequestMapping("/twitter")
public class TwitterController extends BaseController  {

    @Autowired
    TwitterService twitterService;

    @RequestMapping(value = "/dataCount")
    public AjaxResponse dataCount(BaseRequest request) {

        int data = twitterService.dataCount(request);
        return AjaxResponse.ok("success").data(data);
    }

    @RequestMapping(value = "/getDatas")
    public AjaxResponse dataList(BaseRequest request) {
        List<Map<String, Object>> data = twitterService.dataList(request);
        return AjaxResponse.ok("success").data(data);
    }

//    @RequestMapping(value = "/getData")
//    public AjaxResponse getData(String tweetId) {
//
////        twitterService
//        return AjaxResponse.ok("success").data(null);
//    }

    @RequestMapping(value = "/getAboutData")
    public AjaxResponse getAboutData(String userId) {

        Map<String, Object> data = twitterService.getAboutData(userId);
        return AjaxResponse.ok("success").data(data);
    }

    @RequestMapping(value = "/getCommentData")
    public AjaxResponse getCommentData(String tweetId) {
        List<Map<String, Object>> data = twitterService.getCommentData(tweetId);
        return AjaxResponse.ok("success").data(data);
    }

    @RequestMapping(value = "/getFollowerData")
    public AjaxResponse getFollowerData(String userId) {
        List<Map<String, Object>> data = twitterService.getFollowerData(userId);
        return AjaxResponse.ok("success").data(data);
    }

    @RequestMapping(value = "/getFollowingData")
    public AjaxResponse getFollowingData(String userId) {
        List<Map<String, Object>> data = twitterService.getFollowingData(userId);
        return AjaxResponse.ok("success").data(data);
    }


    @RequestMapping("/exports")
    public void exports(HttpServletResponse response,String ids, String path) {

        twitterService.exports(response, ids, path);

    }



}
