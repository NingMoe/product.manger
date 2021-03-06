$(document).ready(function () {
    $("#balance-trace-node").addClass("active");
    $("#balance-trace-menu-node").addClass("active");
    $("#terminal-activity-line-chart").addClass("active");
});

/**
 * 绘制balance信息
 */
$(function drawBalanceLineChart() {
    drawLineChart("balance", "app_version", "balanceAppVersion", "斐讯健康版本活跃情况(Android)");
    drawLineChart("balance", "channel", "balanceAppChanel", "斐讯健康渠道活跃情况(Android)");
});

/**
 * 绘制运动信息
 */
$(function drawLinkLineChart() {
    drawLineChart("147759445162119", "app_version", "LinkAppVersion", "斐讯运动版本活跃情况(Android)");
    drawLineChart("147759445162119", "channel", "LinkAppChanel", "斐讯运动渠道活跃情况(Android)");
});

/**
 * 绘制版本分布图
 * @param appId app类型
 * @param dataType 数据类型
 * @param divId 组件id
 * @param title 标题
 */
function drawLineChart(appId, dataType, divId, title) {
    let object = {};
    object.platform = 'android';
    object.dateType = dataType;
    object.appId = appId;
    let startTime = new Date();
    let endTime = new Date();
    startTime.setDate(startTime.getDate() - 31);
    endTime.setDate(endTime.getDate() - 1);
    object.startTime = startTime.format("yyyy-MM-dd");
    object.endTime = endTime.format("yyyy-MM-dd");
    networkRequest(object, divId, title, "数据来源：www.phicomm.com", "活跃量", startTime);
}

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
 * 时间格式化，主要是date转json的时候出错，少了8小时，所以这里提前格式化一次
 * @param fmt 时间格式
 * @returns {*} 时间
 * @constructor null
 */
Date.prototype.format = function (fmt) {
    let o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

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