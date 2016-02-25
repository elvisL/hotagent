/**
 * 新增或修改代理商
 * Created by cwb on 2016/2/18.
 */
$("select[name=type]").blur(checkAreaAvaliable);
function checkAreaAvaliable() {
    var type = $("select[name=type]").val();
    if (type == 1) {
        var city = $("select[name=city]").val();
        $.ajax({
            url: "/checkCity",
            data: "city=" + encodeURI(city),
            dataType: "json",
            success: function (data) {
                if (data.code != 2000) {
                    layer.alert(data.msg);
                    $("select[name=type]").closest('.form-group').removeClass('has-success').addClass('has-error')
                    return false;
                } else {
                    $("select[name=type]").closest('.form-group').removeClass('has-error');
                    return true;
                }
            }
        })
    } else {
        $("select[name=type]").closest('.form-group').removeClass('has-error');
        return true;
    }
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
    debug: true,
    rules: {
        name: "required",
        username: {
            required: true,
            maxlength: 50
        },
        password: {
            required: true,
            minlength: 3
        },
        level: "required",
        province: "required",
        city: "required",
        district: "required",
        type: "required",
        balance: {
            required: true,
            number: true
        },
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
        expandable: "required",
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
        $("#editForm").submit();
    }
}