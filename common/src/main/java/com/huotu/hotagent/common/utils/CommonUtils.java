package com.huotu.hotagent.common.utils;

/**
 * 常用算法
 * Created by cwb on 2016/2/25.
 */
public class CommonUtils {

    /**
     * 计算分页button的起始显示页码
     * @param currentPage
     * @param pageBtnNum
     * @param totalPages
     * @return
     */
    public static int calculateStartPageNo(int currentPage, int pageBtnNum, int totalPages) {
        if(pageBtnNum == totalPages) {
            return 1;
        }
        return currentPage - pageBtnNum + 1 < 1 ? 1 : currentPage - pageBtnNum + 1;
    }
}
