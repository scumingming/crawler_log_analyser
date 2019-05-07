package com.isinonet.wdview.Controller;

import com.isinonet.wdview.Service.FacebookService;
import com.isinonet.wdview.request.AjaxResponse;
import com.isinonet.wdview.request.BaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by wangmingming on 2019/5/7.
 */
@RestController
@RequestMapping("/facebook")
public class FacebookController extends BaseController {

    @Autowired
    FacebookService facebookService;

    @RequestMapping(value = "/dataCount")
    public AjaxResponse dataCount(BaseRequest request) {

        int data = facebookService.dataCount(request);
        return AjaxResponse.ok("success").data(data);
    }

    @RequestMapping(value = "/getDatas")
    public AjaxResponse dataList(BaseRequest request) {
        List<Map<String, Object>> data = facebookService.dataList(request);
        return AjaxResponse.ok("success").data(data);
    }

    @RequestMapping(value = "/getComments")
    public AjaxResponse getComments(String postId) {
        List<Map<String, Object>> data = facebookService.getComments(postId);
        return AjaxResponse.ok("success").data(data);
    }


    @RequestMapping(value = "/getIntroduction")
    public AjaxResponse getIntroduction(String userId) {
        Map<String, Object> data = facebookService.getIntroduction(userId);
        return AjaxResponse.ok("success").data(data);
    }

    @RequestMapping(value = "/getFriends")
    public AjaxResponse getFriends(String userId) {
        List<Map<String, Object>> data = facebookService.getFriends(userId);
        return AjaxResponse.ok("success").data(data);
    }

    @RequestMapping("/exports")
    public void exports(HttpServletResponse response, String ids, String path) {

        facebookService.exports(response, ids, path);

    }


}
