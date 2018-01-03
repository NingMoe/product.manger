$(document).ready(function () {
    $("#balance-trace-node").addClass("active");
    $("#balance-trace-menu-node").addClass("active");
    $("#user-activity-trace-node").addClass("active");
});
$(function traceUserActivityPV() {
    let baseUrl = $("#baseUrl").val();
    let userId = null;
    let date = null;
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/trace/user/activity/pv",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "userId": userId,
            "date": date
        }), error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            let dates = data.data.data;
            let id = "#traceUserActivityChartPV";
            drawLineChar("24小时-用户活跃度统计", "(PV)", dates, "今日", "昨日", id);
        }
    })
});
$(function traceUserActivityUV() {
    let baseUrl = $("#baseUrl").val();
    let userId = null;
    let date = null;
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/trace/user/activity/uv",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "userId": userId,
            "date": date
        }),error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            let dates = data.data.data;
            let id = "#traceUserActivityChartUV";
            drawLineChar("24小时-用户活跃度统计", "(UV)", dates, "今日", "昨日", id);
        }
    })
});

function drawLineChar(firsttitle, secondtitle, dates, name1, name2, id) {

    let title = {
        text: firsttitle
    };
    let subtitle = {
        text: secondtitle
    };
    let xAxis = {
        categories: ["00","01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23","24"]
    };
    let yAxis = {
        title: {
            text: '活跃度'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    };

    let tooltip = {
        shared: false,
        useHTML: true,
        headerFormat: '<table>',
        pointFormat: '<tr><td>{point.x}点: </td>' +
        '<td style="text-align: right"><b>{point.y}</b></td></tr>',
        footerFormat: '</table>',
        valueDecimals: 0
    };

    let legend = {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    };

    let series =  [
        {
            name: name1,
            data: []
        },
        {
            name: name2,
            data: []
        }
    ];

    let colors = [
        '#ED7054',
        '#4096B5'
    ];


    for(let i=0;i<2;i++){
        let arr=dates[i];
        for(let j = 0; j < 24;j++)
        {
            series[i].data.push(arr[j]);
        }
    }
    let json = {};

    json.title = title;
    json.subtitle = subtitle;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.colors = colors;
    json.tooltip = tooltip;
    json.legend = legend;
    json.series = series;

    $(id).highcharts(json);
}