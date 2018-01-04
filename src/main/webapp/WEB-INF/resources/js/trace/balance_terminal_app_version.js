$(document).ready(function () {
    $("#balance-trace-node").addClass("active");
    $("#balance-trace-menu-node").addClass("active");
    $("#terminal-activity-line-chart").addClass("active");
});

$(function drawLineChart() {
    let object = {};
    object.platform = 'android';
    object.dateType = 'app_version';
    let startTime = new Date();
    let endTime = new Date();
    startTime.setDate(startTime.getDate() - 31);
    endTime.setDate(endTime.getDate() - 1);
    object.startTime = startTime;
    object.endTime = endTime;
    console.info(JSON.stringify(object));
    networkRequest(object,"appVersion", "斐讯健康版本活跃情况(Android)", "数据来源：www.phicomm.com", "活跃量", startTime);
});

$(function drawLineChart() {
    let object = {};
    object.platform = 'android';
    object.dateType = 'channel';
    let startTime = new Date();
    let endTime = new Date();
    startTime.setDate(startTime.getDate() - 31);
    endTime.setDate(endTime.getDate() - 1);
    object.startTime = startTime;
    object.endTime = endTime;
    console.info(JSON.stringify(object));
    networkRequest(object,"appChanel", "斐讯健康渠道活跃情况(Android)", "数据来源：www.phicomm.com", "活跃量", startTime);
});

function networkRequest(param, divId, title, subtitle, yTitle, date) {
    let baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/terminal/line/chart/data",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(param)
        , error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            let mapData = data.data;
            console.info(mapData);
            draw(divId, title, subtitle, yTitle, date, mapData);
        }
    })
}

function draw(divId, title, subtitle, yTitle, date, data) {
    console.info(date.getFullYear(), date.getMonth(), date.getDate());
    Highcharts.chart(divId, {
        title: {
            text: title
        },
        subtitle: {
            text: subtitle
        },
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            title: {
                text: yTitle
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },
        plotOptions: {
            series: {
                label: {
                    connectorAllowed: false
                },
                pointStart: Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()),
                pointIntervalUnit: 'day'
            }
        },
        series: data,
        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom'
                    }
                }
            }]
        }
    });
}