$(document).ready(function () {
    $("#bpm-statistic-1").addClass("active");
    $("#bpm-statistic-2").addClass("active");
});

/**
 * 获取过去15天的血压计统计信息
 */
$(function getMeasureStatisticLast15Day() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/bpm/measure/statistic/dataByDay",
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
            drawBarChart(labels, dates, new Chart($("#bpmMeasureDayChart").get(0).getContext("2d")));
        }
    })
});

/**
 * 获取过去12个月的血压计信息
 */
$(function getMeasureStatisticLast12Month() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/bpm/measure/statistic/dataByMonth",
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
            drawBarChart(labels, dates, new Chart($("#bpmMeasureMonthChart").get(0).getContext("2d")));
        }
    })
});


/**
 * 用户测量数据时间分布
 */
$(function getMeasureStatisticHour() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/bpm/measure/statistic/dataByHour",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let labels = ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23'];
            let dates = [];
            //for (var key in data.data) {
            for (var i=0;i<24;i++ ) {
                if (data.data.hasOwnProperty(labels[i])) {
                    //labels.push(key);
                    dates.push(data.data[labels[i]]);
                }
            }
            drawBarChart(labels, dates, new Chart($("#bpmMeasureHourChart").get(0).getContext("2d")));
        }
    })
});

/**
 * 绘制柱状图
 * @param labes 横坐标
 * @param datas 数据
 * @param chart 图表类型
 */
function drawBarChart(labes, datas, chart) {
    let chartDataArea = {
        labels: labes,
        datasets: [
            {
                fillColor: "#63b542",
                strokeColor: "#72b553",
                pointColor: "#4096B5",
                data: datas
            }
        ]
    };
    const chartOption = {
        //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
        scaleBeginAtZero: true,
        //Boolean - 是否显示网格线
        scaleShowGridLines: false,
        //String - 网格颜色
        scaleGridLineColor: "#aaa69d",
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

//------------------start ------------------------------
//血压计测量数据总量
var timeout4measureAll = false; //启动及关闭按钮

function measureAll() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "GET",
        url: baseUrl + "/bpm/statistic/measure/all",
        dataType: "json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
            timeout4measureAll = true;
        }, success: function (data) {
            console.log(data.data);
            $("#allNum").html(data.data);
        }
    })
}

//获取当天、当月的测量数据的总数量
function measureTodayOrMonth() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/bpm/statistic/measure/todayOrMonth",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
            timeout4measureAll = true;
        }, success: function (data) {
            console.log(data.data);
            $("#monthNum").html(data.data.month);
            $("#todayNum").html(data.data.today);
        }
    })
}

//开启定时
$(function time()
{
    if(timeout4measureAll) return;
    measureAll();
    measureTodayOrMonth();
    setTimeout(time,5000); //time是指本身,延时递归调用自己,100为间隔调用时间,单位毫秒
});
//----------------end---------------------------------------