/**
 * Created by song02.cao on 2017/11/20.
 */
$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});


$(function getBalance24HourDisplay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/balance/24hour/display",
        contentType: "application/json",
        dataType: "json",
        error: function (req, status, error) {
            alert("getBalance24HourDisplay error: " + error);
        },
        success: function (rtValue) {
            let hours = rtValue.data.hours;
            let datas = rtValue.data.counts;
            let series = {name: "24小时数据", data: datas};
            drawOneIndex24HoursChart("column", "balance-24hour-display", "体脂秤测量次数统计分布", "(24小时)",
                null, series);
        }
    });
});

