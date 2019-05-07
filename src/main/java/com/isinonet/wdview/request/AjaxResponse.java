package com.isinonet.wdview.request;

/**
 * Created by wangmingming on 2019/4/29.
 */
public class AjaxResponse {

//    @JSONField
    private int code;

    private String msg;

    private Object data;

    public AjaxResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static AjaxResponse ok(String msg) {
        return new AjaxResponse(0, msg);
    }

    public static AjaxResponse fail(String msg) {
        return new AjaxResponse(-1, msg);
    }

    public AjaxResponse data(Object data) {
        this.data = data;
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
