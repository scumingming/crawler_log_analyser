package com.isinonet.wdview.Controller;

import com.isinonet.wdview.request.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by wangmingming on 2019/5/5.
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(value = {Throwable.class})
    public AjaxResponse exception(Exception e) {

        logger.error("系统异常:", e);
        return AjaxResponse.fail("系统异常");
    }

}
