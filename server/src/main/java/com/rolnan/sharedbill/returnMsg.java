package com.rolnan.sharedbill;

public class returnMsg {
    public Integer code;
    public String msg;
    public Object data;
    returnMsg(Integer code, String msg, Object data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
}
