package com.isinonet.wdview.Controller;

import com.isinonet.wdview.Service.ConfigService;
import com.isinonet.wdview.request.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangmingming on 2019/4/29.
 */
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController {

    @Autowired
    ConfigService configService;

    @RequestMapping("/getKeyWords")
    private AjaxResponse getKeyWords() {
        List<String> data = configService.getKeyWords();
        return AjaxResponse.ok("success").data(data);

    }

    @RequestMapping("/addKeyWord")
    private AjaxResponse addKeyWord(String keyWord) {

        configService.addKeyWord(keyWord);

        return AjaxResponse.ok("success!");
    }


    @RequestMapping("/deleteKeyWord")
    private AjaxResponse deleteKeyWord(String keyWord) {

        configService.deleteKeyWord(keyWord);

        return AjaxResponse.ok("success!");
    }

}
