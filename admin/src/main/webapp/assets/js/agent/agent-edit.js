/**
 * 新增或修改代理商
 * Created by cwb on 2016/2/18.
 */
$(function () {
   $("input[name=prices]").each(function(){
       var a = $(this);
       $.each(selectedPrices,function(n,value){
           if(value == a.attr("pdid")) {
               a.click();
           }else{
               a.click();
               a.click();
           }
       })
   });
});
$("#qualify").live("change",function() {
    imgUpload();
});
function imgUpload() {
    var qualifyUri = $("#qualifyUri").val();
    $.ajaxFileUpload
    ({
        url: url + 'uploadImg',
        fileElementId: 'qualify',
        dataType: 'json',
        data:{
            qualifyUri:qualifyUri
        },
        type:'post',
        success:function(result){
            if(result.code==2000){
                $("#qualifyUri").val(result.data[0])
                $("#previewImg").attr("src",result.data[1]);
            }else {
                alert(result.msg);
            }
        }
    });
}
$("select[name=type]").change(checkAreaAvaliable);
$("select[name=city]").change(checkAreaAvaliable);
function checkAreaAvaliable() {
    var type = $("select[name=type]").val();
    var city = $("select[name=city]").val();
    var avaliable = true;
    $.ajax({
        url: url + "checkCity",
        data: "city=" + encodeURI(city)+"&&agentId="+agentId,
        dataType: "json",
        async:false,
        success: function (data) {
            if (data.code == 2000) {
                $("select[name=type]").closest('.form-group').removeClass('has-error');
                avaliable = true;
            } else if(data.code == 6002) {
                layer.alert(data.msg);
                $("select[name=type]").closest('.form-group').removeClass('has-success').addClass('has-error')
                avaliable = false;
            }else if(data.code == 6003) {
                if(type==1) {
                    layer.alert(data.msg);
                    $("select[name=type]").closest('.form-group').removeClass('has-success').addClass('has-error')
                    avaliable = false;
                }else {
                    $("select[name=type]").closest('.form-group').removeClass('has-error');
                    avaliable = true;
                }
            }
        }

    });
    return avaliable;
}
$("#editForm").validate({
    ignoreTitle: true,
    ignore:"",
    highlight: function (element) {
        $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
    },
    success: function (element) {
        $(element).closest('.form-group').removeClass('has-error');
        $(element).remove();
    },
    onfocusout: function (element) {
        $(element).valid();
    },
    debug: false,
    rules: {
        name: "required",
        password: {
            required: true,
            minlength: 3
        },
        province: "required",
        city: "required",
        district: "required",
        type: "required",
        contacts: {
            required: true,
            maxlength: 50
        },
        phoneNo: {
            required: true,
            mobile: true
        },
        address: {
            required: true,
            maxlength: 100
        },
        mail: {
            required: true,
            email: true
        },
        qq: {
            required: true,
            number: true
        },
        qualifyUri: {
            required:function() {
                return $("#qualifyUri").val() == '';
            }
        }
    },
    messages:{
        qualifyUri:{
            required:"请上传图片"
        }
    },
    errorPlacement:function(error,element) {
        if(element.attr("name")=="qualifyUri") {
            error.insertAfter(".input-append");
        }else {
            error.insertAfter(element);
        }
    }


});
function submit() {
    var av = checkAreaAvaliable();
    if(av) {
        $("input[name=prices]").each(function() {
            var a = $(this);
            var pdid = a.attr("pdid");
            a.attr("value",pdid+"|"+$("#price"+pdid).val());
        });
        $("#editForm").submit();
    }
}
function showPrice(obj) {
    var checkbox = $(obj);
    var pdid = checkbox.attr("pdid");
    if(checkbox.is(":checked")) {
        $("#price"+pdid).show();
    }else {
        $("#price"+pdid).hide();
    }
}