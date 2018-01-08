/**
 * @author yafei.hou
 * Created by yafei.hou on 2017/12/27.
 */
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
            "day": 30,
            "type": "mac"
        },
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
            console.log("data:" + data.data);
            console.log("labels:" + labels);
            console.log("dates:" + dates);
            var jsons = column();
            jsons.title.text = 'S7体脂称每天新增使用量(最近30天)';
            jsons.series[0].data = dates;
            jsons.xAxis.categories = labels;
            jsons.legend.enabled = false;
            $("#S7UsageCountschart1").highcharts(jsons);
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
            console.log("data:" + data.data);
            console.log("labels:" + labels);
            console.log("datas:" + datas);
            var json = column();
            json.title.text = 'S7体脂称每月新增使用量';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7UsageCountschart2").highcharts(json);
        }
    })
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
            var json = column();
            json.title.text = 'S7体脂称各地区新增使用量（最近30天）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7SalesLocationchart3").highcharts(json);
        }
    })
});

/**
 * 柱状图
 * @returns {{}}
 */
function column() {
    var chart = {
        type: 'column'
    };
    var title = {
        text: null
    };
    var subtitle = {
        text: 'Phicomm-AI'
    };
    var xAxis = {
        categories: null,
        crosshair: true
    };
    var yAxis = {
        min: 0,
        title: {
            text: '数量 (台)'
        }
    };
    var tooltip = {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
        '<td style="padding:0"><b>{point.y} </b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true
    };
    var plotOptions = {
        column: {
            pointPadding: 0.1,
            borderWidth: 0
        }
    };
    var credits = {
        enabled: false
    };
    var legend = {
        borderWidth: 1,
        backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
        shadow: true
    };
    var series = [{
        name: 'S7新增使用量',
        data: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
        dataLabels: {
            enabled: true
        }
    }];
    var navigation = {
        buttonOptions: {
            enabled: true
        }
    };
    var json = {};
    json.chart = chart;
    json.title = title;
    json.subtitle = subtitle;
    json.tooltip = tooltip;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.legend = legend;
    json.series = series;
    json.plotOptions = plotOptions;
    json.credits = credits;
    json.navigation = navigation;
    return json;


}


/**
 * 获取不同合作商激活的最近30天激活的K码数量
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/s7/reports/ActivationStatisticDay",
        dataType: "json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (rtValue) {
            let dates = [];
            let lianbi = [];
            let wanjia = [];
            console.info(rtValue.data);
            for (var i = rtValue.data.length - 1; i >= 0; i--) {
                dates.push(rtValue.data[i]["date"]);
                lianbi.push(rtValue.data[i]["lianbi"]);
                wanjia.push(rtValue.data[i]["wanjia"]);

            }
            var json1 = drawChart();
            json1.title.text = 'S7K码新增激活量（最近30天）';
            json1.series[1].data = wanjia;
            json1.series[1].name = '万家金服K码激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = '联璧K码激活量';
            $("#S7KKeysCountsChart1").highcharts(json1);
        }
    })
});

/**
 * 获取不同合作商激活的每个月激活的K码数量
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/s7/reports/ActivationCountMonth",
        dataType: "json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (rtValue) {
            let dates = [];
            let lianbi = [];
            let wanjia = [];
            let sum = [];
            console.info(rtValue.data);
            for (var i = rtValue.data.length - 1; i >= 0; i--) {
                dates.push(rtValue.data[i]["month"]);
                lianbi.push(rtValue.data[i]["lianbi"]);
                wanjia.push(rtValue.data[i]["wanjia"]);
                sum.push(rtValue.data[i]["lianbi"] + rtValue.data[i]["wanjia"]);
            }
            var plotOptions = {
                series: {
                    animation: {
                        duration: 3000
                    }
                }
            };
            var json1 = column();
            json1.title.text = 'S7K码新增激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = '联璧K码激活量';
            json1.series[0].type = 'spline';
            json1.plotOptions = plotOptions;
            var chart = new Highcharts.Chart("S7KKeysCountsChart2", json1);
            chart.addSeries({
                data: wanjia, name: '万家金服K码激活量', dataLabels: {
                    enabled: true,
                    shadow: false,
                    allowOverlap: true
                },
                type: 'spline'
            });

            var json2 = column();
            json2.title.text = 'S7K码新增激活量';
            json2.series[0].data = sum;
            json2.series[0].name = 'K码激活量';
            json2.xAxis.categories = dates;
            json2.series[0].type = 'spline';
            json2.plotOptions = plotOptions;
            new Highcharts.Chart("S7KKeysCountsChart3", json2);
        }
    })
});

/**
 * 激活量和使用量对比
 */
$(function () {
    var S7UsageCountsEveryDay = {};
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/day",
        dataType: "json",
        data: {
            "day": 30,
            "type": "mac"
        },
        async: false,
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            S7UsageCountsEveryDay = data;
            console.log("data:" + data.data);
        }
    });

    $.ajax({
        type: "POST",
        url: baseUrl + "/s7/reports/ActivationAllCountDay",
        dataType: "json",
        data: {
            "days": 30
        },
        async: false,
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (rtValue) {
            let datesTemp = [];
            let usageTemp = [];
            let kKeysTemp = [];
            let dates = [];
            let usage = [];
            let kKeys = [];
            let usagePercentage = [];
            console.info(rtValue.data);
            for (var i = rtValue.data.length - 1; i >= 0; i--) {
                datesTemp.push(rtValue.data[i]["date"]);
                kKeysTemp.push(rtValue.data[i]["counts"]);
            }
            console.info("datesTemp:\n" + datesTemp);
            console.info("kKeysTemp:\n" + kKeysTemp);
            for (let key in S7UsageCountsEveryDay.data) {
                if (S7UsageCountsEveryDay.data.hasOwnProperty(key)) {
                    dates.push(key);
                    usageTemp.push(S7UsageCountsEveryDay.data[key]);
                }
            }
            console.info("dates:\n" + dates);
            console.info("usageTemp:\n" + usageTemp);
            for (var k = 0; k < dates.length; k++) {
                usage[k] = usageTemp[k];
                for (var j = 0; j < datesTemp.length; j++) {
                    if (dates[k] === datesTemp[j]) {
                        kKeys[k] = kKeysTemp[j];
                        if (usage[k] === 0 || kKeys === 0) {
                            usagePercentage[k] = 0;
                        } else {
                            usagePercentage[k] = ((usage[k] / kKeys[k]) * 100);
                        }
                        break;
                    } else {
                        kKeys[k] = 0;
                        usagePercentage[k] = 0;
                    }
                }
            }
            console.log("---");
            console.log(usagePercentage);
            var json1 = column();
            json1.title.text = 'S7K码新增激活量与实际注册使用量对比';
            json1.xAxis.categories = dates;
            json1.series[0].data = usage;
            json1.series[0].name = 'S7注册使用量';
            json1.series[0].type = 'column';
            var chart = new Highcharts.Chart("S7KKeysCountsChart4", json1);
            chart.addSeries({
                data: kKeys, name: 'K码激活量',
                dataLabels: {
                    enabled: true,
                    shadow: false
                },
                type: 'column'
            });
            chart.addAxis({ // Secondary yAxis
                id: 'percentage',
                title: {
                    text: '使用量占比'
                },
                labels: {
                    format: '{value:.3f}%'
                },
                opposite: true
            });
            chart.addSeries({
                data: usagePercentage, name: '使用量占比',
                dataLabels: {
                    enabled: true,
                    shadow: false,
                    format: '{y:.3f}%'
                },
                yAxis: 'percentage',
                type: 'spline',
                tooltip: {
                    valueSuffix: '%'
                }
            });
        }
    })
});


/**
 * 堆叠柱状图
 * @param chartId
 */
function drawChart() {
    var chart = {
        type: 'column'
    };
    var title = {
        text: '堆叠柱形图'
    };
    var xAxis = {
        categories: ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas']
    };
    var yAxis = {
        min: 0,
        title: {
            text: 'S7K码激活数量(台)'
        },
        stackLabels: {
            style: {
                fontWeight: 'bold',
                color: 'black'
            },
            enabled: true,
            allowOverlap: true
        }
    };
    var legend = {
        backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
        borderWidth: 1,
        shadow: false
    };
    var tooltip = {
        formatter: function () {
            return '<b>' + this.x + '</b><br/>' +
                this.series.name + ': ' + this.y + '<br/>' +
                '总激活量: ' + this.point.stackTotal;
        }
    };
    var plotOptions = {
        column: {
            stacking: 'normal'
        }
    };
    var credits = {
        enabled: false
    };
    var series = [
        {
            name: 'Jane',
            data: [2, 2, 3, 2, 1],
            dataLabels: {
                style: {
                    fontWeight: 'bold',
                    color: 'black'
                },
                enabled: true,
                shadow: false
            }
        }, {
            dataLabels: {
                style: {
                    fontWeight: 'bold',
                    color: 'black'
                },
                enabled: true,
                shadow: false
            }
        }];
    var navigation = {
        buttonOptions: {
            enabled: true
        }
    };
    var json = {};
    json.chart = chart;
    json.title = title;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.legend = legend;
    json.tooltip = tooltip;
    json.plotOptions = plotOptions;
    json.credits = credits;
    json.series = series;
    json.navigation = navigation;
    return json;
}

/**
 * 读取输入的激活数量统计数据
 */
function addedKKeys() {
    var date = document.getElementById("activationDate").value;
    var lianbi = document.getElementById("lianbi").value;
    var wanjia = document.getElementById("wanjia").value;
    if (lianbi === "0" && wanjia === "0") {
        return alert("数量不应该是0");
    }
    console.log("date:" + date);
    console.log("lianbi = " + lianbi + ",wanjia=" + wanjia);
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/s7/reports/addActivationData",
        dataType: "json",
        data: {
            "date": date,
            "lianbi": lianbi,
            "wanjia": wanjia

        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function () {
            document.getElementById("lianbi").value = 0;
            document.getElementById("wanjia").value = 0;
            return alert("数据存储成功");
        }
    });
}


/**
 * 获取所选日期的报告
 */
function getReport() {
    var date = document.getElementById("reportDate").value;
    updateElementsValuesByName("selectedDate", date);
    //当前日期下的S7使用量和截止当前日期的总使用量
    getThisDateUsage(date);
    //当前日期下S7的激活量和截止当前日期的总使用量
    getThisDateActivation(date);
    //获取当前日期下的活跃用户数量
    getThisDateActiveUser(date);

}

/**
 * 用于通过元素名称修改元素名称
 */
function updateElementsValuesByName(elementNames, value) {
    var eleDate = document.getElementsByName(elementNames);
    for (var i = 0; i < eleDate.length; i++) {
        eleDate[i].innerHTML = value;
    }
}

/**
 * 当前日期下的S7使用量和总使用量
 * @param thisDate 日期 格式为yyyy-mm-dd
 */
function getThisDateUsage(thisDate) {

}

/**
 * 当前日期下S7的激活量
 * @param date 日期 格式为yyyy-mm-dd
 */
function getThisDateActivation(date) {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/s7/reports/ActivationCountByMates",
        dataType: "json",
        data: {
            "date": date
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            if (data.data != null) {
                updateElementsValuesByName("s7ActivationToday", data.data[0]["lianbi"] + data.data[0]["wanjia"]);
                updateElementsValuesByName("s7LianbiKKeysCountToday", data.data[0]["lianbi"]);
                updateElementsValuesByName("s7WanjiaKKeysCountToday", data.data[0]["wanjia"]);
            }
        }
    });

    $.ajax({
        type: "POST",
        url: baseUrl + "/s7/reports/ActivationAllCountsEveryMate",
        dataType: "json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            updateElementsValuesByName("s7ActivationAll", data.data[0]["lianbi"] + data.data[0]["wanjia"]);
            updateElementsValuesByName("s7LianbiKKeysCountAll", data.data[0]["lianbi"]);
            updateElementsValuesByName("s7WanjiaKKeysCountAll", data.data[0]["wanjia"]);
        }
    });
}
/**
 * 获取当前日期下的活跃用户数量
 * @param date 日期 格式为yyyy-mm-dd
 */
function getThisDateActiveUser(date) {
    let userId = null;
    let baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/trace/user/activity/uv",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "userId": userId,
            "date": date
        }), error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            let result = data.data.data;
            var activeUser = null;
            console.log("--------result-------" + result);
            for (var i = 0; i < 24; i++) {
                activeUser += result[1][i];
            }
            updateElementsValuesByName("s7ActiveToday", activeUser);
        }
    })
}