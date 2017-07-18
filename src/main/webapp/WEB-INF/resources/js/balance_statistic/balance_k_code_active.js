$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});

/**
 * 获取激活信息（30天）
 */
$(function obtainActiveByDay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/day",
        dataType: "json",
        data: {
            "day": 30,
            "type": "lianbi"
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let labels = [];
            let dates = [];
            for (let key in data) {
                if (data.hasOwnProperty(key)) {
                    labels.push(key);
                    dates.push(data[key]);
                }
            }
            let lineChartCavas = $("#activeMonthChart").get(0).getContext("2d");
            let lineChart = new Chart(lineChartCavas);
            drawLinechart(labels, dates, lineChart);
        }
    })
});

/**
 * 获取过去12个月的激活信息
 */
$(function obtainActiveYearData() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/month",
        dataType: "json",
        data: {
            "month": 12,
            "type": "lianbi"
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let labels = [];
            let dates = [];
            for (let key in data) {
                if (data.hasOwnProperty(key)) {
                    labels.push(key);
                    dates.push(data[key]);
                }
            }
            let lineChartCavas = $("#activeYearChart").get(0).getContext("2d");
            let lineChart = new Chart(lineChartCavas);
            drawLinechart(labels, dates, lineChart);
        }
    })
});

/**
 * 绘制曲线图
 * @param labes 横坐标
 * @param datas 数据
 * @param chart 图表类型
 */
function drawLinechart(labes, datas, chart) {
    let areaChartData = {
        labels: labes,
        datasets: [
            {
                fillColor: "rgba(151,187,205,0.5)",
                strokeColor: "#3c8dbc",
                pointColor: "rgba(151,187,205,1)",
                pointStrokeColor: "#fff",
                data: datas
            }
        ]
    };
    const areaChartOptions = {
        //是否显示刻度
        showScale: true,
        //是否显示网格线
        scaleShowGridLines: true,
        //网格线的颜色
        scaleGridLineColor: "rgba(0,0,0,.05)",
        //网格线线宽
        scaleGridLineWidth: 1,
        //是否显示X轴
        scaleShowHorizontalLines: true,
        //坐标轴颜色
        scaleLineColor: "#000000",
        //坐标轴字体
        scaleFontFamily: " 'Arial' ,'Microsoft YaHei'",
        //是否显示Y轴
        scaleShowVerticalLines: true,
        //坐标轴字体颜色
        scaleFontColor: "#000000",
        //是否显示为曲线
        bezierCurve: true,
        //曲率
        bezierCurveTension: 0.3,
        //对否显示点
        pointDot: true,
        //句点的半径
        pointDotRadius: 2.5,
        //句点的线宽
        pointDotStrokeWidth: 1,
        // 句点的外围半径
        pointHitDetectionRadius: 20,
        //是否显示数据集
        datasetStroke: true,
        //数据集大小
        datasetStrokeWidth: 2,
        //是否用颜色填充数据集
        datasetFill: false,
        //临时说明
        legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++)" +
        "{%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label)" +
        "{%><%=datasets[i].label%><%}%></li><%}%></ul>",
        maintainAspectRatio: true,
        responsive: true
    };
    chart.Line(areaChartData, areaChartOptions);
}