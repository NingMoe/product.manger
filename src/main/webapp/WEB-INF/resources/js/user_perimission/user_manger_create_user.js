$(document).ready(function () {
    $("#user-manger-node-1").addClass("active");
    $("#user-manger-node-2").addClass("active");
    $("#user-manger-node-3").addClass("active");
    $("#user-manger-node-li-1").addClass("active");
    $("#sex").select2({multiple: false});
    $("#role").select2({multiple: false});
    $("#submit").click(function () {
        var baseUrl = $("#baseUrl").val();
        var formData = new FormData($("#form")[0]);
        $.ajax({
            type: "POST",
            url: baseUrl + "/user/manger/create",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if(data.status === 0) {
                    alert("添加成功！");
                } else if(data.status === 7) {
                    alert("上传头像失败！");
                } else if(data.status === 15) {
                    alert("手机号已经存在！");
                } else if(data.status === 2) {
                    alert("数据格式错误！");
                }
            }
        });
    });
});