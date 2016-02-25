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
 * bootstrap分页扩展
 * 将根据所传参数自动渲染输出bootstrap风格的分页按钮
 *
 * @param loadObj 待输出的dom元素
 * @param pageNo 页码,从1开始
 * @param totalPages 总页数
 * @param btnCount 需要显示的按钮数量,不包括上一页下一页首页和末页
 */
function paging(loadObj, pageNo, totalPages, btnCount) {
    this.currentBtnPage = 0;
    this.obj = loadObj;
    this.pageNo = pageNo;
    this.totalPages = totalPages;
    this.btnCount = btnCount;

    this.init = function (callback) {
        if (this.obj) {
            this.obj.html('');
            if (this.totalRecords == 0) {
                this.obj.html('');
                return;
            }
            if (this.pageNo >= totalPages) {
                this.pageNo = totalPages;
            }
            if (this.pageNo <= 1) {
                this.pageNo = 1;
            }


            if(this.pageNo > 1){
                //输出首页和上一页按钮
                this.obj.append('<li ' + (this.pageNo == 1 ? 'class="disabled"' : '') + '><a href="javascript:goTo(1,' + callback + ')" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
                this.obj.append('<li ' + (this.pageNo == 1 ? 'class="disabled"' : '') + '><a href="javascript:goTo(' +  (pageNo - 1) + ',' + callback + ')"><i class="fa fa-angle-left"></i></a></li>');
            }else{
                this.obj.append('<li ' + (this.pageNo == 1 ? 'class="disabled"' : '') + '><a href="#"><span aria-hidden="true">&laquo;</span></a></li>');
                this.obj.append('<li ' + (this.pageNo == 1 ? 'class="disabled"' : '') + '><a href="#"><i class="fa fa-angle-left"></i></a></li>');
            }

            var btnPageCount = totalPages % this.btnCount == 0 ? parseInt(totalPages / this.btnCount) : parseInt(totalPages / this.btnCount + 1);//按钮页数
            var btnStart = parseInt(this.btnCount / 2) + 1;//从哪个索引开始变换

            if (this.pageNo - this.currentBtnPage >= btnStart) {
                this.currentBtnPage++;
            }
            if (this.currentBtnPage + 1 > btnPageCount) {
                this.currentBtnPage = btnPageCount - 1;
            }
            if (totalPages <= this.btnCount) {
                this.btnCount = totalPages;
            }

            //输出中间八个按钮
            for (var i = this.currentBtnPage + 1; i <= this.btnCount + this.currentBtnPage; i++) {
                this.obj.append('<li ' + (this.pageNo == i ? 'class="active"' : '') + '><a href="javascript:goTo(' + i + ',' + callback + ')">' + i + '</a></li>');
            }

            //输出下一页和末页
            if(this.pageNo == totalPages){
                this.obj.append('<li ' + (pageNo == totalPages ? 'class="disabled"' : '') + '><a href="#"><i class="fa fa-angle-right"></i></a></li>');
                this.obj.append('<li ' + (pageNo == totalPages ? 'class="disabled"' : '') + '><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
            }else{
                this.obj.append('<li ' + (pageNo == totalPages ? 'class="disabled"' : '') + '><a href="javascript:goTo(' + (pageNo + 1) + ',' + callback + ')"><i class="fa fa-angle-right"></i></a></li>');
                this.obj.append('<li ' + (pageNo == totalPages ? 'class="disabled"' : '') + '><a href="javascript:goTo(' + totalPages + ',' + callback + ')" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
            }
        }
    };
}

function goTo(pageNo, callback) {
    callback(pageNo);
}