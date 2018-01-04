$(document).ready(function () {
    $("#balance-trace-node").addClass("active");
    $("#balance-trace-menu-node").addClass("active");
    $("#terminal-activity-line-chart").addClass("active");
});

/**
 * 绘制版本分布图
 */
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
    networkRequest(object, "appVersion", "斐讯健康版本活跃情况(Android)", "数据来源：www.phicomm.com", "活跃量", startTime);
});

/**
 * 绘制渠道分布图
 */
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
    networkRequest(object, "appChanel", "斐讯健康渠道活跃情况(Android)", "数据来源：www.phicomm.com", "活跃量", startTime);
});

/**
 * 因为用的都是同一个url，所以摘出来
 * @param param 请求参数
 * @param divId 组件id
 * @param title 图标题
 * @param subtitle 副标题
 * @param yTitle y轴代表内容
 * @param date x轴开始的时间
 */
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

/**
 * 只管画图
 * @param divId 组件id
 * @param title 标题
 * @param subtitle 副标题
 * @param yTitle y轴标题
 * @param date 开始时间
 * @param data 图数据
 */
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