package com.huotu.hotagent.common.constant;

/**
 * Created by liual on 2015-09-21.
 */
public enum ResultCodeEnum {
    SUCCESS(200, "请求成功"),
    USERNAME_NULL(401,"用户名不能为空"),
    PASSWORD_NULL(402,"密码不能为空"),
    USERNAME_OR_PASSWORD_WRONG(403,"用户名或密码错误"),
    SYSTEM_BAD_REQUEST(500,"系统请求失败"),
    DATA_BAD_PARSER(600,"数据解析失败"),
    SIGN_ERROR(300,"签名错误"),
    NO_SIGN(301,"签名参数未传"),
    LOGINNAME_NOT_AVAILABLE(100,"用户名已存在"),
    AUTHORITY_NULL(700,"请设置权限"),
    SAVE_DATA_ERROR(400,"数据保存出错"),
    SETTLEMENTNO_NULL(251,"结算单编号为空"),
    DATA_NULL(500,"没有传输数据");

    //todo 其他状态
    private int resultCode;
    private String resultMsg;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    ResultCodeEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
