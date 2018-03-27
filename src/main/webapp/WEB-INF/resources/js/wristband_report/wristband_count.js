$(document).ready(function () {
    $("#trace-sport-node").addClass("active");
    $("#user-activity-trace-sport-node2").addClass("active");
});

/**
 * 获取不同类型激活的最近30天激活的K码数量
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/ActivationNumberDay",
        dataType: "json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (rtValue) {
            let dates = [];
            let lianbi = [];
            let wanjia = [];
            console.info(rtValue.data);
            for (var i = rtValue.data.length - 1; i >= 0; i--) {
                dates.push(rtValue.data[i]["date"]);
                lianbi.push(rtValue.data[i]["lianbi"]);
                wanjia.push(rtValue.data[i]["wanjia"]);

            }
            var json1 = drawChart();
            json1.title.text = '运动设备按类型统计K码新增激活量（最近30天）';
            json1.series[1].data = wanjia;
            json1.series[1].name = '万家金服K码激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = '联璧K码激活量';
            $("#S7KKeysCountsChart1type").highcharts(json1);
        }
    })
});



