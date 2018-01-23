$(document).ready(function () {
    $("#bpm-statistic-1").addClass("active");
    $("#bpm-statistic-2").addClass("active");
});


//最近15天 血压计销量
$(function bpmMeasureCountSaleByDay() {
    const baseUrl = $("#baseUrl").val();
    phicommLoading.show();
    $.ajax({
        type: "POST",
        url: baseUrl + "/bpm/statistic/sale/byDay",
        dataType: "json",
        data: {
            "day": 12
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
            let series = {name: "血压计使用量", data: datas};
            let startDate = new Date();
            startDate.setDate(startDate.getDate() - 11);
            drawOneIndexDaysChart("column", "bpm-sale-day-chart", "血压计使用量", "(最近12天)",
                "台数", series, startDate);
            phicommLoading.hide();
        }
    })
});

//最近12个月 血压计销量
$(function bpmMeasureCountSaleByMonth() {
    const baseUrl = $("#baseUrl").val();
    phicommLoading.show();
    $.ajax({
        type: "POST",
        url: baseUrl + "/bpm/statistic/sale/byMonth",
        dataType: "json",
        data: {
            "month": 12
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
            let series = {name: "血压计使用量", data: datas};
            let startDate = new Date();
            startDate.setMonth(startDate.getMonth() - 11);
            drawOneIndexMonthsChart("column", "bpm-sale-month-chart", "血压计使用量", "(最近12个月)",
                "台数", series, startDate);
            phicommLoading.hide();
        }
    })
});

//销售总量
$(function bpmCountSaleAll() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/bpm/statistic/sale/all",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            $("#saleNum").html(formatNum(data.data));
        }
    })
});

//血压计今天销售量
$(function bpmSaleCountAll() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/bpm/statistic/sale/today",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            $("#bindNum").html(formatNum(data.data));
        }
    })
});

/**
 * 格式化数字，千分位加逗号
 * @param originStr 原来的数字串
 */
function formatNum(originStr) {
    var str = originStr.toString();
    var newStr = "";
    var count = 0;
    for (var i = str.length - 1; i >= 0; i--) {
        if (count % 3 === 0 && count !== 0) {
            newStr = str.charAt(i) + "," + newStr;
        } else {
            newStr = str.charAt(i) + newStr;
        }
        count++;
    }
    console.log(str);
    return newStr;
}