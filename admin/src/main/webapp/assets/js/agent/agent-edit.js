/**
 * 新增或修改代理商
 * Created by cwb on 2016/2/18.
 */
$("#qualify").live("change",function() {
    imgUpload();
});
function imgUpload() {
    var srcPath = $("#previewImg").attr("src");
    $.ajaxFileUpload
    ({
        url: 'uploadImg',
        fileElementId: 'qualify',
        dataType: 'json',
        data:{
            srcPath:srcPath
        },
        type:'post',
        success:function(result){
            if(result.code==2000){
                $("#previewImg").attr("src",result.data);
            }else {
                alert(result.msg);
            }
        }
    });
}