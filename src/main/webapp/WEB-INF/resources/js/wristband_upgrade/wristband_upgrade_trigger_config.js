$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#firmware-upgrade-wristband-li-node").addClass("active");
    $("#firmware-upgrade-wristband-menu-node").addClass("active");
    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/firmware/upgrade/config/get",
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
            url: baseUrl + "/firmware/upgrade/config/set",
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