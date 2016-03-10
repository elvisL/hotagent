/**
 * product-list page's js file.
 * Created by cwb on 2016/3/10.
 */
function editPrice(obj) {
    var productId = $(obj).attr("productId");
    layer.prompt({
        title:"请输入新价格",
        formType:0
    },function(value,index){
            $.ajax({
                url:contextUrl+"editProductPrice",
                data:{
                    id:productId,
                    basePrice:parseInt(value)
                },
                type:"post",
                success:function(data) {
                    if(data.code == 2001) {
                        location.reload();
                    }else {
                        layer.open({
                            content: data.msg
                        });
                    }
                }
            });
        }
    );
}
