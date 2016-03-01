/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.common.constant;

/**
 * Created by liual on 2015-09-21.
 */
public enum ResultCodeEnum {
    SUCCESS(2000, "请求成功"),
    USERNAME_NULL(4001, "用户名不能为空"),
    PASSWORD_NULL(4002, "密码不能为空"),
    USERNAME_OR_PASSWORD_WRONG(4003, "用户名或密码错误"),
    SYSTEM_BAD_REQUEST(5000, "系统请求失败"),
    DATA_BAD_PARSER(6000, "数据解析失败"),
    SIGN_ERROR(3000, "签名错误"),
    NO_SIGN(3001, "签名参数未传"),
    LOGINNAME_NOT_AVAILABLE(1000, "用户名已存在"),
    AUTHORITY_NULL(7000, "请设置权限"),
    SAVE_DATA_ERROR(4000, "数据保存出错"),
    IMPORT_ERROR(4001, "余额不足"),
    WITHDRAW_ERROR(4002, "提现次数已达上限"),
    DATA_NULL(5000, "没有传输数据"),
    NOT_IMG(6001,"不是图片"),
    HAS_SOLE_ALREADY(6002,"该地区已经有独家代理"),
    IS_NORMAL_AGENT_AREA(6003,"该地区只可设置普通代理");

    //todo 其他状态
    private int resultCode;
    private String resultMsg;

    ResultCodeEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

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
}
