/**
 * Created by yafei.hou on 2017/12/28.
 */
$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});

$(function getBalanceElectrodeDisplay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/measure/data/source",
        contentType: "application/json",
        dataType: "json",
        error: function (req, status, error) {
            alert("error: " + error);
        },
        success: function (rtValue) {
            let dates = [];
            let autoData = [];
            let claimData = [];
            let lockingData = [];
            console.info(rtValue.data);
            for (var i = rtValue.data.length - 1; i >= 0; i--) {
                dates.push(rtValue.data[i]["date"]);
                autoData.push(rtValue.data[i]["auto_claim"]);
                claimData.push(rtValue.data[i]["locking"]);
                lockingData.push(rtValue.data[i]["claim"]);
            }
            console.info("rtValue.status = " + rtValue.status);
            console.info("dates = " + dates.toString());
            console.info("electrode0Counts = " + autoData.toString());
            console.info("electrode4Counts = " + claimData.toString());
            console.info("electrode8Counts = " + lockingData.toString());

            drawBarChart(new Chart($("#balance_data_source").get(0).getContext("2d")), dates, autoData, claimData, lockingData);
        }
    });
});

/**
 *  绘制chart图形
 * @param chart        图形的类型，比如'bar','line','radar'等
 * @param labels      横坐标
 * @param autoDataSet    数据集0
 * @param claimDataSet    数据集1
 * @param lockingDataSet    数据集2
 * @todo 柱状图上方直接显示数据，需要后续添加
 */
function drawBarChart(chart, labels, autoDataSet, claimDataSet, lockingDataSet) {
    let chartDataArea = {
        //type:type,

        labels: labels,
        datasets: [
            {
                label: '自动认领',
                data: autoDataSet,
                fillColor: '#2a9b25'
            },
            {
                label: '手动认领',
                data: claimDataSet,
                fillColor: '#2aa59a'
            },
            {
                label: '锁定上称',
                data: lockingDataSet,
                fillColor: '#3199eb'
            }
        ]

    };

    const chartOption = {
        //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
        scaleBeginAtZero: true,
        //Boolean - 是否显示网格线
        scaleShowGridLines: false,
        //String - 网格颜色
        scaleGridLineColor: "#dcd8cf",
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
        datasetFill: false,
        //Number - Spacing between each of the X value sets
        barValueSpacing: 5,
        //Number - Spacing between data sets within X values
        barDatasetSpacing: 1,
        //String - 示例模板
        legendTemplate: '<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].fillColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>',
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
