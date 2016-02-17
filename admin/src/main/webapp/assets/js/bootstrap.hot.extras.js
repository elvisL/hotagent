/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

/**
 * Created by allan on 2/17/16.
 */

/**
 * bootstrap分页初始化
 * @type {{init: paging.init}}
 */
var paging = {
    init: function (obj, pageIndex, pageSize, totalRecord, btnCount) {
        if (obj) {
            if (totalRecord == 0) {
                obj.html('');
                return;
            }
            var pageCount = totalCount % pageSize == 0 ? parseInt(totalCount / pageSize) : parseInt(totalCount / pageSize + 1);

            //输出首页和上一页按钮
            obj.append('<li ' + pageIndex == 1 ? 'class="disabled"' : '' + '><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
            obj.append('<li ' + pageIndex == 1 ? 'class="disabled"' : '' + '><a href="#"><i class="fa fa-angle-left"></i></a></li>');

            var index = pageCount % btnCount == 0 ? parseInt(pageCount / btnCount) : parseInt(pageCount / btnCount + 1);//按钮页数
            var btnStart = parseInt(index / 2) + 1;//从哪个索引开始变换


            //输出中间八个按钮
            for (var i = 1; i <= btnCount; i++) {
                var start = i + index;
                obj.append('<li><a ' + pageIndex == i ? 'class="active"' : '' + ' href="#">' + i + '</a></li>');
            }
        }
    }
};

function paging() {
    var currentBtnPage = 0;

    function init(obj, pageIndex, pageSize, totalRecord, btnCount) {
        if (obj) {
            if (totalRecord == 0) {
                obj.html('');
                return;
            }
            var pageCount = totalCount % pageSize == 0 ? parseInt(totalCount / pageSize) : parseInt(totalCount / pageSize + 1);

            //输出首页和上一页按钮
            obj.append('<li ' + pageIndex == 1 ? 'class="disabled"' : '' + '><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
            obj.append('<li ' + pageIndex == 1 ? 'class="disabled"' : '' + '><a href="#"><i class="fa fa-angle-left"></i></a></li>');

            var btnPageCount = pageCount % btnCount == 0 ? parseInt(pageCount / btnCount) : parseInt(pageCount / btnCount + 1);//按钮页数
            var btnStart = parseInt(index / 2) + 1;//从哪个索引开始变换

            if (pageIndex - currentBtnPage >= btnStart) {
                currentBtnPage++;
            }
            if (currentBtnPage + 1 > btnPageCount) {
                currentBtnPage = btnPageCount - 1;
            }

            //输出中间八个按钮
            for (var i = current; i <= btnCount; i++) {
                var start = i + currentBtnPage;
                obj.append('<li><a ' + pageIndex == i ? 'class="active"' : '' + ' href="#">' + i + '</a></li>');
            }
        }
    }
}