$(document).ready(function () {
    $("#picture_upload").addClass("active");
    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/watchplate/upload/config/get",
        dataType: "json",
        error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            $("#configuation").val(data.description);
        }
    });
    $("#submit").click(function () {
        $.ajax({
            type: "POST",
            url: baseUrl + "/watchplate/upload/config/set",
            dataType: "json",
            data: {
                configuation: $("#configuation").val()
            },
            error: function (req, status, err) {
                console.log('Failed reason: ' + err);
            }, success: function (data) {
                if(data.status === 0) {
                    alert("已经更新的回调的配置");
                } else {
                    alert("失败!");
                }
            }
        });
    });
});