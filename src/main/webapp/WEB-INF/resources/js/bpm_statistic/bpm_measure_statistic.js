$(document).ready(function () {
    $("#bpm-statistic-1").addClass("active");
    $("#bpm-statistic-2").addClass("active");
});

/**
 * 获取过去12天的血压计统计信息
 */
$(function getMeasureStatisticLast15Day() {
    const baseUrl = $("#baseUrl").val();
    phicommLoading.show();
    $.ajax({
        type: "POST",
        url: baseUrl + "/bpm/measure/statistic/dataByDay",
        dataType: "json",
        contentType: "application/json",
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
            let series = {name: "血压计测量数据", data: datas};
            let startDate = new Date();
            startDate.setDate(startDate.getDate() - 12);
            drawOneIndexDaysChart("column", "bpm-measure-day-chart", "血压计测量数据统计", "(最近12天)",
                "次数", series, startDate);
            phicommLoading.hide();
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
            let datas = [];
            for (let key in data.data) {
                if (data.data.hasOwnProperty(key)) {
                    labels.push(key);
                    datas.push(data.data[key]);
                }
            }
            let series = {name: "血压计测量数据", data: datas};
            let startDate = new Date();
            startDate.setMonth(startDate.getMonth() - 11);
            drawOneIndexMonthsChart("column", "bpm-measure-month-chart", "血压计测量数据", "(最近12个月)",
                "次数", series, startDate);
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
            let labels = ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'];
            let datas = [];
            //for (var key in data.data) {
            for (var i = 0; i < 24; i++) {
                if (data.data.hasOwnProperty(labels[i])) {
                    //labels.push(key);
                    datas.push(data.data[labels[i]]);
                }
            }
            let series = {name: "24小时分布", data: datas};
            drawOneIndex24HoursChart("column", "bpm-measure-hour-chart", "用户测量时间按小时分布", "(24小时)",
                "次数", series);
        }
    })
});

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
            $("#allNum").html(formatNum(data.data));
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
            $("#monthNum").html(formatNum(data.data.month));
            $("#todayNum").html(formatNum(data.data.today));
        }
    })
}

//开启定时
$(function time() {
    if (timeout4measureAll) return;
    measureAll();
    measureTodayOrMonth();
    setTimeout(time, 5000); //time是指本身,延时递归调用自己,100为间隔调用时间,单位毫秒
});
//----------------end---------------------------------------

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