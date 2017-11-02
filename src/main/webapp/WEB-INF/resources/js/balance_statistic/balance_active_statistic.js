$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});

$(function statisticBalanceActive() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/balance/active/query",
        contentType: "application/json",
        dataType: "json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let pvs = data.data.pvs;
            let uvs = data.data.uvs;
            let dates = data.data.dates;
            drawBarChart(dates, pvs, new Chart($("#balanceActiveStatisticChartPv").get(0).getContext("2d")));
            drawBarChart(dates, uvs, new Chart($("#balanceActiveStatisticChartUv").get(0).getContext("2d")));
        }
    })
});

/**
 * 绘制柱状图
 *
 * @param labes 横坐标
 * @param datas 数据
 * @param chart 图表类型
 */
function drawBarChart(labes, datas, chart) {
    let chartDataArea = {
        labels: labes,
        datasets: [
            {
                fillColor: "#4096B5",
                strokeColor: "#4096B5",
                pointColor: "#4096B5",
                data: datas
            }
        ]
    };
    const chartOption = {
        scaleBeginAtZero: true,
        scaleShowGridLines: false,
        scaleGridLineColor: "#000000",
        scaleGridLineWidth: 1,
        scaleShowHorizontalLines: true,
        scaleShowVerticalLines: true,
        scaleLineColor: "#000000",
        barShowStroke: true,
        scaleFontColor: "#000000",
        scaleFontFamily: " 'Arial' ,'Microsoft YaHei'",
        scaleFontStyle: "500",
        barStrokeWidth: 1,
        datasetFill: false,
        barValueSpacing: 5,
        barDatasetSpacing: 1,
        //String - 示例模板
        legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++)" +
        "{%><li><span style=\"background-color:<%=datasets[i].fillColor%>\"></span><%if(datasets[i].label){%>" +
        "<%=datasets[i].label%><%}%></li><%}%></ul>",
        responsive: true,
        maintainAspectRatio: true,
        //是否显示动画
        animation: true,
        animationSteps: 60,
        animationEasing: "easeOutQuart",
        showTooltips: false,
        onAnimationComplete: function () {
            let ctx = this.chart.ctx;
            ctx.font = this.scale.font;
            ctx.fillStyle = this.scale.textColor;
            ctx.textAlign = "center";
            ctx.textBaseline = "bottom";
            this.datasets.forEach(function (dataset) {
                dataset.bars.forEach(function (bar) {
                    ctx.fillText(bar.value, bar.x, bar.y);
                });
            });
        }
    };
    chart.Bar(chartDataArea, chartOption);
}