$(function obtainMacInfoByDay() {
    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/day",
        dataType: "json",
        data: {
            "day": 15,
            "type": "mac"
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            var labels = [];
            var dates = [];
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    labels.push(key);
                    dates.push(data[key]);
                }
            }
            drawBarChart(labels, dates, new Chart($("#macMonthChart").get(0).getContext("2d")));
        }
    })
});
$(function obtainMacYearData() {
    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/month",
        dataType: "json",
        data: {
            "month": 12,
            "type": "mac"
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            var labels = [];
            var dates = [];
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    labels.push(key);
                    dates.push(data[key]);
                }
            }
            drawBarChart(labels, dates, new Chart($("#macYearChart").get(0).getContext("2d")));
        }
    })
});
function drawBarChart(labes, datas, chart) {
    var chartDataArea = {
        labels: labes,
        datasets: [
            {
                fillColor: "rgba(151,187,205,0.5)",
                strokeColor: "rgba(151,187,205,1)",
                data: datas
            }
        ]
    };
    var chartOption = {
        //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
        scaleBeginAtZero: true,
        //Boolean - 是否显示网格线
        scaleShowGridLines: false,
        //String - 网格颜色
        scaleGridLineColor: "#000000",
        //Number - 网格线宽度
        scaleGridLineWidth: 1,
        //Boolean - 是否显示水平线（不含x轴）
        scaleShowHorizontalLines: true,
        //Boolean - 是否显示垂直线（不含y轴）
        scaleShowVerticalLines: true,
        scaleLineColor: "#000000",
        //Boolean - If there is a stroke on each bar
        barShowStroke: true,
        //字体颜色
        scaleFontColor: "#000000",
        scaleFontFamily: " 'Arial' ,'Microsoft YaHei'",
        //字体大小
        /*            scaleFontSize:13,
         //字体
         scaleFontFamily : "'Microsoft Yahei'",*/
        //字体风格
        scaleFontStyle: "500",
        //Number - Pixel width of the bar stroke
        barStrokeWidth: 1,
        //Number - Spacing between each of the X value sets
        barValueSpacing: 5,
        //Number - Spacing between data sets within X values
        barDatasetSpacing: 1,
        //String - 示例模板
        legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++)" +
        "{%><li><span style=\"background-color:<%=datasets[i].fillColor%>\"></span><%if(datasets[i].label){%>" +
        "<%=datasets[i].label%><%}%></li><%}%></ul>",
        //Boolean - whether to make the chart responsive
        responsive: true,
        maintainAspectRatio: true,
        //是否显示动画
        animation: true,
        //Number - Number of animation steps
        animationSteps: 60,
        //String - Animation easing effect
        animationEasing: "easeOutQuart",
        showTooltips: false,
        onAnimationComplete: function () {
            var ctx = this.chart.ctx;
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
    var barChartOptions = chartOption;
    var barChartData = chartDataArea;
    barChartData.datasets[0].fillColor = "#4096B5";
    barChartData.datasets[0].strokeColor = "#4096B5";
    barChartData.datasets[0].pointColor = "#4096B5";
    barChartOptions.datasetFill = false;
    chart.Bar(chartDataArea, barChartOptions);
}