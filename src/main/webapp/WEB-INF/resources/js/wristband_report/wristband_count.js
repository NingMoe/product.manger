$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});

/**
 * 获取过去30天的mac统计信息
 */
$(function obtainMacInfoByDay() {
    const baseUrl = $("#baseUrl").val();
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
            let labels = [];
            let datas = [];
            for (let key in data.data) {
                if (data.data.hasOwnProperty(key)) {
                    labels.push(key);
                    datas.push(data.data[key]);
                }
            }
            let series = {name: "新增使用量", data: datas};
            let startDate = new Date();
            startDate.setDate(startDate.getDate() - 15);
            drawOneIndexDaysChart("column", "mac-month-chart", "体脂秤设备新增使用量", "(最近15天)", "台数", series, startDate);
        }
    })
});

/**
 * 获取过去12个月的mac统计信息
 */
$(function obtainMacYearData() {
    const baseUrl = $("#baseUrl").val();
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
            let labels = [];
            let datas = [];
            for (let key in data.data) {
                if (data.data.hasOwnProperty(key)) {
                    labels.push(key);
                    datas.push(data.data[key]);
                }
            }
            let series = {name: "新增使用量", data: datas};
            let startDate = new Date();
            startDate.setMonth(startDate.getMonth() - 11);
            drawOneIndexMonthsChart("column", "mac-year-chart", "体脂秤设备新增使用量", "(最近12个月)", "台数", series, startDate);
        }
    })
});

