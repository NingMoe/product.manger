$(document).ready(function () {
    $("#balance-trace-node").addClass("active");
    $("#balance-trace-menu-node").addClass("active");
    $("#user-activity-trace-node").addClass("active");
});
$(function statisticUser() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/user/analysis/all",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let labels = [];
            let dates = [];
            for (let key in data.data) {
                if (data.data.hasOwnProperty(key)) {
                    labels.push(key);
                    dates.push(data.data[key]);
                }
            }
            let barChartCavas = $("#traceUserActivityChartPV").get(0).getContext("2d");
            let barChart = new Chart(barChartCavas);
            drawPieChart(labels, dates, barChart);
        }
    })
});
$(function statisticUserByAge() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/user/age/v2",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let labels = [];
            let boyData = [];
            let girlData = [];
            let boyMap = data.data["boy"];
            let girlMap = data.data["girl"];
            for (let key in boyMap) {
                if (boyMap.hasOwnProperty(key)) {
                    labels.push(key);
                    boyData.push(boyMap[key]);
                }
            }
            for (let key in girlMap) {
                if (girlMap.hasOwnProperty(key)) {
                    girlData.push(girlMap[key]);
                }
            }
            let barChartCavas = $("#traceUserActivityChartUV").get(0).getContext("2d");
            let barChart = new Chart(barChartCavas);
            drawBarChart2(labels, girlData, boyData, barChart);
        }
    })
});

function drawBarChart2(labes, data0, data1, chart) {
    let chartDataArea = {
        labels: labes,
        datasets: [
            {
                fillColor: "#4096B5",
                strokeColor: "#4096B5",
                pointColor: "#4096B5",
                data: data1
            },
            {
                fillColor: "#b53780",
                strokeColor: "#b53780",
                pointColor: "#b53780",
                data: data0
            }

        ]
    };
    const chartOption = {
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
        datasetFill: false,
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

function drawPieChart(labes, datas, chart) {
    let pieData = [
        {
            value: datas[0],
            color: "#b53780",
            text: labes[0]

        },
        {
            value: datas[1],
            color: "#4096B5",
            text: labes[1]
        }
    ];
    const chartOption = {
        //Boolean - whether to make the chart responsive
        responsive: true,
        maintainAspectRatio: true,
        //是否显示动画
        animation: true,
    };
    chart.Pie(pieData, chartOption);
}