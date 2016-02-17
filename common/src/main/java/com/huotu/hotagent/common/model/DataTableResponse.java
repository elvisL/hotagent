/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.common.model;

import lombok.Data;

/**
 * jquery datatables 数据返回
 * Created by allan on 2/15/16.
 */
@Data
public class DataTableResponse {
    private int draw;
    /**
     * 总记录条数
     */
    private long recordsTotal;
    /**
     * 还不知道干嘛的
     */
    private long recordsFiltered;
    /**
     * 数据,应是一个list或者set
     */
    private Object data;
}
