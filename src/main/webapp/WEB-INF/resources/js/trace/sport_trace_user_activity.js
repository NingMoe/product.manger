$(document).ready(function () {
    $("#trace-sport-node").addClass("active");
    $("#trace-sport-menu-node").addClass("active");
    $("#user-activity-trace-sport-node").addClass("active");
});
$(function traceUserActivityPV() {
    let baseUrl = $("#baseUrl").val();
    let userId = null;
    let date = null;
    let appId = "147759445162119";
    $.ajax({
        type: "POST",
        url: baseUrl + "/trace/user/activity/pv",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "userId": userId,
            "date": date,
            "appId": appId
        }), error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            let datas = data.data.data;
            let series = [
                {name: "今日", data: datas[0]},
                {name: "昨日", data: datas[1]}
            ];
            drawMultiIndex24HoursChart("line", "trace-user-activity-chart-pv", "24小时-用户活跃度统计", "(PV)",
                "活跃度", series, 1);
        }
    })
});
$(function traceUserActivityUV() {
    let baseUrl = $("#baseUrl").val();
    let userId = null;
    let date = null;
    let appId = "147759445162119";
    $.ajax({
        type: "POST",
        url: baseUrl + "/trace/user/activity/uv",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "userId": userId,
            "date": date,
            "appId": appId
        }), error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            let datas = data.data.data;
            let series = [
                {name: "今日", data: datas[0]},
                {name: "昨日", data: datas[1]}
            ];
            drawMultiIndex24HoursChart("line", "trace-user-activity-chart-uv", "24小时-用户活跃度统计", "(UV)",
                "活跃度", series, 1);
        }
    })
});

