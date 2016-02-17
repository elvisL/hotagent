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
 * jquery datatables 请求参数
 * Created by allan on 2/15/16.
 */
@Data
public class DataTableRequest {
    /**
     * 数据索引,通过该值得到pageIndex
     */
    private int start;

    private int draw;

    /**
     * 当前table所显示的记录条数,即pageSize
     */
    private int length;
}
