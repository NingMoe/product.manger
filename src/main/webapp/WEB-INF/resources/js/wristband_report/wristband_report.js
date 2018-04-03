/**
 * @author xiang.zhang
 * Created by xiang.zhang on 2018/02/24.
 */
$(document).ready(function () {
    $("#trace-sport-node").addClass("active");
    $("#trace-sport-menu-node").addClass("active");
    $("#user-activity-trace-sport-node1").addClass("active");
});


/**
 * 获取过去30天的运动设备统计信息
 */
$(function obtainMacInfoByDay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/number/day",
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
            jsons.title.text = '手环手表每天新增使用量(最近30天)';
            jsons.series[0].data = dates;
            jsons.xAxis.categories = labels;
            jsons.legend.enabled = false;
            $("#S7UsageCountschart1").highcharts(jsons);
        }
    })
});
/**
 * 获取过去12个月的w1的使用的统计信息
 */
$(function obtainMacYearData() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/number/month",
        dataType: "json",
        data: {
            "month": 12,
            "type": "w1"
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
            json.title.text = 'w1每月新增使用量(最近12个月）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7UsageCountschart2").highcharts(json);
            var sum = 0;
            for (var i = 0; i < datas.length; i++) {
                sum += datas[i];
            }
            updateElementsValuesByName("s7DataUsageCountAll", formatNum(sum));
        }
    })
});
/**
 * 获取过去12个月的w1p的使用的统计信息
 */
$(function obtainMacYearData() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/number/month",
        dataType: "json",
        data: {
            "month": 12,
            "type": "w1p"
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
            json.title.text = 'w1p每月新增使用量(最近12个月）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7UsageCountschart2a").highcharts(json);
            var sum = 0;
            for (var i = 0; i < datas.length; i++) {
                sum += datas[i];
            }
            updateElementsValuesByName("s7DataUsageCountAll", formatNum(sum));
        }
    })
});
/**
 * 获取过去12个月的w2的使用的统计信息
 */
$(function obtainMacYearData() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/number/month",
        dataType: "json",
        data: {
            "month": 12,
            "type": "w2"
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
            json.title.text = 'w2每月新增使用量(最近12个月）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7UsageCountschart2b").highcharts(json);
            var sum = 0;
            for (var i = 0; i < datas.length; i++) {
                sum += datas[i];
            }
            updateElementsValuesByName("s7DataUsageCountAll", formatNum(sum));
        }
    })
});

/**
 * 获取过去12个月的w2p的使用的统计信息
 */
$(function obtainMacYearData() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/number/month",
        dataType: "json",
        data: {
            "month": 12,
            "type": "w2p"
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
            json.title.text = 'w2p每月新增使用量(最近12个月）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7UsageCountschart2c").highcharts(json);
            var sum = 0;
            for (var i = 0; i < datas.length; i++) {
                sum += datas[i];
            }
            updateElementsValuesByName("s7DataUsageCountAll", formatNum(sum));
        }
    })
});


/**
 * 获取过去12个月的运动设备的使用的统计信息
 */
$(function obtainMacYearData() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/total/number/month",
        dataType: "json",
        data: {
            "month": 12,
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
            json.title.text = '运动设备每月新增使用量(最近12个月）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7UsageCountschart2total").highcharts(json);
            var sum = 0;
            for (var i = 0; i < datas.length; i++) {
                sum += datas[i];
            }
            updateElementsValuesByName("s7DataUsageCountAll", formatNum(sum));
        }
    })
});

/**
 * 获取运动产品地区统计信息（30天），并作图。
 */
$(function obtainLocationInfoByDay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/total/location/day",
        dataType: "json",
        data: {
            "day": 30,
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
            json.title.text = '运动设备各地区新增使用量（最近30天）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7SalesLocationchart3total").highcharts(json);
        }
    })
});

/**
 * 获取w1地区统计信息（30天），并作图。
 */
$(function obtainLocationInfoByDay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/location/day",
        dataType: "json",
        data: {
            "day": 30,
            "type": "w1",
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
            json.title.text = 'w1各地区新增使用量（最近30天）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7SalesLocationchart3").highcharts(json);
        }
    })
});

/**
 * 获取w1p地区统计信息（30天），并作图。
 */
$(function obtainLocationInfoByDay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/location/day",
        dataType: "json",
        data: {
            "day": 30,
            "type": "w1p",
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
            json.title.text = 'w1p各地区新增使用量（最近30天）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7SalesLocationchart4").highcharts(json);
        }
    })
});
/**
 * 获取w2地区统计信息（30天），并作图。
 */
$(function obtainLocationInfoByDay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/location/day",
        dataType: "json",
        data: {
            "day": 30,
            "type": "w2",
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
            json.title.text = 'w2各地区新增使用量（最近30天）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7SalesLocationchart5").highcharts(json);
        }
    })
});
/**
 * 获取w2p地区统计信息（30天），并作图。
 */
$(function obtainLocationInfoByDay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/location/day",
        dataType: "json",
        data: {
            "day": 30,
            "type": "w2p",
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
            json.title.text = 'w2p各地区新增使用量（最近30天）';
            json.series[0].data = datas;
            json.xAxis.categories = labels;
            json.legend.enabled = false;
            $("#S7SalesLocationchart5a").highcharts(json);
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
        name: '手环手表新增使用量',
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
 * 获取不同合作商激活的所有设备的最近30天激活的K码数量
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/ActivationNumberDay",
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
            json1.title.text = '手环手表K码新增激活量（最近30天）';
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
 * 获取不同合作商激活的最近30天激活的K码数量(W1）
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/W1/ActivationNumberDay",
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
            json1.title.text = 'W1的K码新增激活量（最近30天）';
            json1.series[1].data = wanjia;
            json1.series[1].name = 'w1万家金服K码激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = 'w1联璧K码激活量';
            $("#S7KKeysCountsChart1typeOfW1").highcharts(json1);
        }
    })
});
/**
 * 获取不同合作商激活的最近30天激活的K码数量(W1P）
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/W1P/ActivationNumberDay",
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
            json1.title.text = 'W1P的K码新增激活量（最近30天）';
            json1.series[1].data = wanjia;
            json1.series[1].name = 'w1p万家金服K码激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = 'w1p联璧K码激活量';
            $("#S7KKeysCountsChart1typeOfW1P").highcharts(json1);
        }
    })
});

/**
 * 获取不同合作商激活的最近30天激活的K码数量(W2）
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/W2/ActivationNumberDay",
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
            json1.title.text = 'W2 K码新增激活量（最近30天）';
            json1.series[1].data = wanjia;
            json1.series[1].name = 'w2万家金服K码激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = 'w2联璧K码激活量';
            $("#S7KKeysCountsChart1typeOfW2").highcharts(json1);
        }
    })
});

/**
 * 获取不同合作商激活的最近30天激活的K码数量(W2P）
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/W2P/ActivationNumberDay",
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
            json1.title.text = 'W2P K码新增激活量（最近30天）';
            json1.series[1].data = wanjia;
            json1.series[1].name = 'w2p万家金服K码激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = 'w2p联璧K码激活量';
            $("#S7KKeysCountsChart1typeOfW2P").highcharts(json1);
        }
    })
});


/**
 * 获取所有运动设备在不同合作商激活的每个月激活的K码数量
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/GetActivationNumberMonth",
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
            json1.title.text = '手环手表K码新增激活量';
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
            json2.title.text = '手环手表K码新增激活量';
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
 * 获取W1在不同合作商激活的每个月激活的K码数量
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/W1/GetActivationNumberMonth",
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
            json1.title.text = 'W1 K码新增激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = '联璧K码激活量';
            json1.series[0].type = 'spline';
            json1.plotOptions = plotOptions;
            var chart = new Highcharts.Chart("S7KKeysCountsChart2W1", json1);
            chart.addSeries({
                data: wanjia, name: '万家金服K码激活量', dataLabels: {
                    enabled: true,
                    shadow: false,
                    allowOverlap: true
                },
                type: 'spline'
            });

            var json2 = column();
            json2.title.text = 'W1 K码新增激活量';
            json2.series[0].data = sum;
            json2.series[0].name = 'K码总激活量';
            json2.xAxis.categories = dates;
            json2.series[0].type = 'spline';
            json2.plotOptions = plotOptions;
            new Highcharts.Chart("S7KKeysCountsChart3W1", json2);
        }
    })
});


/**
 * 获取W1P在不同合作商激活的每个月激活的K码数量
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/W1P/GetActivationNumberMonth",
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
            json1.title.text = 'W1P K码新增激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = '联璧K码激活量';
            json1.series[0].type = 'spline';
            json1.plotOptions = plotOptions;
            var chart = new Highcharts.Chart("S7KKeysCountsChart2W1P", json1);
            chart.addSeries({
                data: wanjia, name: '万家金服K码激活量', dataLabels: {
                    enabled: true,
                    shadow: false,
                    allowOverlap: true
                },
                type: 'spline'
            });

            var json2 = column();
            json2.title.text = 'W1P K码新增激活量';
            json2.series[0].data = sum;
            json2.series[0].name = 'K码总激活量';
            json2.xAxis.categories = dates;
            json2.series[0].type = 'spline';
            json2.plotOptions = plotOptions;
            new Highcharts.Chart("S7KKeysCountsChart3W1P", json2);
        }
    })
});

/**
 * 获取W2在不同合作商激活的每个月激活的K码数量
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/W2/GetActivationNumberMonth",
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
            json1.title.text = 'W2 K码新增激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = '联璧K码激活量';
            json1.series[0].type = 'spline';
            json1.plotOptions = plotOptions;
            var chart = new Highcharts.Chart("S7KKeysCountsChart2W2", json1);
            chart.addSeries({
                data: wanjia, name: '万家金服K码激活量', dataLabels: {
                    enabled: true,
                    shadow: false,
                    allowOverlap: true
                },
                type: 'spline'
            });

            var json2 = column();
            json2.title.text = 'W2 K码新增激活量';
            json2.series[0].data = sum;
            json2.series[0].name = 'K码总激活量';
            json2.xAxis.categories = dates;
            json2.series[0].type = 'spline';
            json2.plotOptions = plotOptions;
            new Highcharts.Chart("S7KKeysCountsChart3W2", json2);
        }
    })
});


/**
 * 获取W2P在不同合作商激活的每个月激活的K码数量
 */
$(function () {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/W2P/GetActivationNumberMonth",
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
            json1.title.text = 'W2P K码新增激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = '联璧K码激活量';
            json1.series[0].type = 'spline';
            json1.plotOptions = plotOptions;
            var chart = new Highcharts.Chart("S7KKeysCountsChart2W2P", json1);
            chart.addSeries({
                data: wanjia, name: '万家金服K码激活量', dataLabels: {
                    enabled: true,
                    shadow: false,
                    allowOverlap: true
                },
                type: 'spline'
            });

            var json2 = column();
            json2.title.text = 'W2P K码新增激活量';
            json2.series[0].data = sum;
            json2.series[0].name = 'K码总激活量';
            json2.xAxis.categories = dates;
            json2.series[0].type = 'spline';
            json2.plotOptions = plotOptions;
            new Highcharts.Chart("S7KKeysCountsChart3W2P", json2);
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
        url: baseUrl + "/wristband/reports/number/day",
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
        url: baseUrl + "/wristband/reports/ActivationAllCountDay",
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
            json1.title.text = '手环手表的K码新增激活量与实际注册使用量对比';
            json1.xAxis.categories = dates;
            json1.series[0].data = usage;
            json1.series[0].name = '手环手表的注册使用量';
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
                    pointFormat: '<br>{series.name}: {point.y:.3f}%'

                }
            });
        }
    })
});


/**
 * 堆叠柱状图
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
            text: '手环手表K码激活数量(台)'
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
function addWristBandKKeys() {
    var date = document.getElementById("activationDate").value;
    var lianbi = document.getElementById("lianbi").value;
    var wanjia = document.getElementById("wanjia").value;
    var type = document.getElementById("type").value;

    if (lianbi === "0" && wanjia === "0") {
        return alert("数量不应该是0");
    }
    console.log("date:" + date);
    console.log("lianbi = " + lianbi + ",wanjia=" + wanjia);
    console.log("type ="+type)

    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/addActivationData",
        dataType: "json",
        data: {
            "date": date,
            "lianbi": lianbi,
            "wanjia": wanjia,
            "type":type
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
    //当前日期下手环手表的使用量和截止当前日期的总使用量
    getThisDateUsage(date);
    //当前日期下手环手表的激活量和截止当前日期的总使用量
    getThisDateActivation(date);
    //获取当前日期下的活跃用户数量
    getThisDateActiveUser(date);

}
$(function () {
    var date = document.getElementById("reportDate").value;
    updateElementsValuesByName("selectedDate", date);
    //当前日期下手环手表的使用量和截止当前日期的总使用量
    getThisDateUsage(date);
    //当前日期下手环手表的激活量和截止当前日期的总使用量
    getThisDateActivation(date);
    //获取当前日期下手环手表的活跃用户数量
    getThisDateActiveUser(date);
});

/**
 * 用于通过元素名称修改元素内容
 */
function updateElementsValuesByName(elementNames, value) {
    var eleDate = document.getElementsByName(elementNames);
    for (var i = 0; i < eleDate.length; i++) {
        eleDate[i].innerHTML = value;
    }
}

/**
 * 当前日期下的手环手表使用量和总使用量
 * @param thisDate 日期 格式为yyyy-mm-dd
 */
function getThisDateUsage(thisDate) {

}

/**
 * 当前日期下手环手表的激活量
 * @param date 日期 格式为yyyy-mm-dd
 */
function getThisDateActivation(date) {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/ActivationNumByMates",
        dataType: "json",
        data: {
            "date": date
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            if (data.data.length !== 0) {
                console.log("-----" + data.data);
                updateElementsValuesByName("s7ActivationToday", formatNum(data.data[0]["lianbi"] + data.data[0]["wanjia"]));
                updateElementsValuesByName("s7LianbiKKeysCountToday", formatNum(data.data[0]["lianbi"]));
                updateElementsValuesByName("s7WanjiaKKeysCountToday", formatNum(data.data[0]["wanjia"]));
            }
        }
    });


}

$(function getUsageCount() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/wristband/reports/GetActivationAllNum",
        dataType: "json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            if (data.data.length !== 0) {
                updateElementsValuesByName("s7ActivationAll", formatNum(data.data[0]["lianbi"] + data.data[0]["wanjia"]));
                updateElementsValuesByName("s7LianbiKKeysCountAll", formatNum(data.data[0]["lianbi"]));
                updateElementsValuesByName("s7WanjiaKKeysCountAll", formatNum(data.data[0]["wanjia"]));
            }

        }
    });
});

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
            var activeUser = 0;
            if (result.length === 2) {
                for (var i = 0; i < 24; i++) {
                    activeUser += result[0][i];
                }
                updateElementsValuesByName("s7ActiveToday", formatNum(activeUser));
            }

        }
    })
}

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