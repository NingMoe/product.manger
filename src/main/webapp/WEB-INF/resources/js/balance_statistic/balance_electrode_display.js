/**
 * Created by song02.cao on 2017/11/21.
 */
$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});

$(function getBalanceElectrodeDisplay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type:"POST",
        url:baseUrl + "/balance/statistic/balance/electrode/display",
        contentType:"application/json",
        dataType:"json",
        error:function (req, status, error) {
            alert("getBalanceElectrodeDisplay error: " + error);
        },
        success:function (rtValue) {
            let dates = rtValue.data.dates;
            let electrode0Counts = rtValue.data.electrode0Counts;
            let electrode4Counts = rtValue.data.electrode4Counts;
            let electrode8Counts = rtValue.data.electrode8Counts;
            console.info("rtValue.status = " + rtValue.status);
            console.info("dates = " + dates.toString())
            console.info("electrode0Counts = " + electrode0Counts.toString())
            console.info("electrode4Counts = " + electrode4Counts.toString())
            console.info("electrode8Counts = " + electrode8Counts.toString())
            var ctx = $("#balance-electrode-display")
            drawBarChart(ctx,'bar',dates,electrode0Counts,electrode4Counts,electrode8Counts);
        }
    });
});

/**
 *  绘制chart图形
 * @param ctx         需要绘制的canvas
 * @param type        图形的类型，比如'bar','line','radar'等
 * @param labels      横坐标
 * @param dataset0    数据集0
 * @param dataset1    数据集1
 * @param dataset2    数据集2
 * @todo 柱状图上方直接显示数据，需要后续添加
 */
function drawBarChart(ctx, type, labels,dataset0, dataset1, dataset2) {
    var myChart = new Chart(ctx, {
        type:type,
        data: {
            labels:labels,
            datasets:[
                {
                    label:'电极0',
                    data:dataset0,
                    backgroundColor:'#666600'
                },
                {
                    label:'电极4',
                    data:dataset1,
                    backgroundColor:'#99FF99'
                },
                {
                    label:'电极8',
                    data:dataset2,
                    backgroundColor:'#4096B5'
                }
            ],
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });
}
