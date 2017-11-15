$(document).ready(function () {
    $("#user-manger-node-1").addClass("active");
    $("#user-manger-node-2").addClass("active");
    $("#user-manger-node-3").addClass("active");
    $("#user-manger-node-li-1").addClass("active");
    $("#sex").select2({multiple: false});
    $("#role").select2({multiple: false});
    var baseUrl = $("#baseUrl").val();
    var phoneNumber = $("#phoneNumber").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/user/manger/detail",
        dataType: "json",
        data: {
            "phoneNumber": phoneNumber
        }, error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            console.log(JSON.stringify(data));
            $("#email").val(data.data.email);
            $("#username").val(data.data.username);
            $("#sex").val(data.data.sex === 0 ? "girl" : "boy").trigger("change");
            $("#role").val(data.data.role).trigger("change");
        }
    });
    $("#submit").click(function () {
        var formData = new FormData($("#form")[0]);
        $.ajax({
            type: "POST",
            url: baseUrl + "/user/manger/modify",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if(data.status === 0) {
                    alert("修改成功！");
                    window.location.href = baseUrl + "/user/manger/page/list";
                } else if(data.status === 2) {
                    alert("数据格式异常！");
                } else if(data.status === 7) {
                    alert("上传文件失败！");
                } else if(data.status === 16) {
                    alert("您的权限不够，请联系管理员！");
                } else if(data.status === 17) {
                    alert("用户不见了！");
                }
            }
        });
    });
});