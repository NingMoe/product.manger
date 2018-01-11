$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-3").addClass("active");
});

/**
 * 获取位置统计信息（30天），并作图。
 */
$(function obtainLocationInfoByDay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/location/day",
        dataType: "json",
        data: {
            "day": 30,
            "type": "balance",
            "pageSize": 15
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
            let title = "体脂秤使用地区分布";
            let series = {name: title, data: datas};
            drawOneIndexCategoryChart("column", "location-month-chart", title, "(月)", "台数", labels, series);
        }
    })
});

/**
 * 获取过去12个月的位置统计信息，并作图。
 */
$(function obtainLocationYearData() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/location/month",
        dataType: "json",
        data: {
            "month": 12,
            "type": "balance",
            "pageSize": 15
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
            let title = "体脂秤使用地区分布";
            let series = {name: title, data: datas};
            drawOneIndexCategoryChart("column", "location-year-chart", title, "(年)", "台数", labels, series);
        }
    })
});
