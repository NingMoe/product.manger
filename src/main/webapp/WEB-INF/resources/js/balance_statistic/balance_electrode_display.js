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
        type: "POST",
        url: baseUrl + "/balance/statistic/balance/electrode/display",
        contentType: "application/json",
        dataType: "json",
        error: function (req, status, error) {
            alert("getBalanceElectrodeDisplay error: " + error);
        },
        success: function (rtValue) {
            let dates = rtValue.data.dates;
            let electrode0Counts = rtValue.data.electrode0Counts;
            let electrode4Counts = rtValue.data.electrode4Counts;
            let electrode8Counts = rtValue.data.electrode8Counts;
            let series = [
                {name: "0电极", data: electrode0Counts},
                {name: "4电极", data: electrode4Counts},
                {name: "8电极", data: electrode8Counts}
            ];
            let startDate = new Date();
            startDate.setDate(startDate.getDate() - 20);
            drawMultiIndexDaysChart("line", "balance-electrode-display", "体脂秤电极使用分布统计", "(20天)",
                null, series, startDate, 1);
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
function drawBarChart(ctx, type, labels, dataset0, dataset1, dataset2) {
    var myChart = new Chart(ctx, {
        type: type,
        data: {
            labels: labels,
            datasets: [
                {
                    label: '电极0',
                    data: dataset0,
                    backgroundColor: '#666600'
                },
                {
                    label: '电极4',
                    data: dataset1,
                    backgroundColor: '#99FF99'
                },
                {
                    label: '电极8',
                    data: dataset2,
                    backgroundColor: '#4096B5'
                }
            ],
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}
