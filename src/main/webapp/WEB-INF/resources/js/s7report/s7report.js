/**
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
        text: 'Source: phicomm-AI'
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
        '<td style="padding:0"><b>{point.y}台 </b></td></tr>',
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
        },
        color: '#3ab539'
    }];

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
    return json;


}


/**
 * 获取不同合作商激活的最近30天激活的K码数量
 */
$(function obtainKKeyCount() {
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
$(function obtainKKeyCount() {
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
            console.info(rtValue.data);
            for (var i = rtValue.data.length - 1; i >= 0; i--) {
                dates.push(rtValue.data[i]["month"]);
                lianbi.push(rtValue.data[i]["lianbi"]);
                wanjia.push(rtValue.data[i]["wanjia"]);

            }
            var json1 = column();
            json1.title.text = 'S7K码新增激活量';
            // json1.series[1].data = wanjia;
            // json1.series[1].name = '万家金服K码激活量';
            json1.xAxis.categories = dates;
            json1.series[0].data = lianbi;
            json1.series[0].name = '联璧K码激活量';
            var chart = $("#S7KKeysCountsChart2").highcharts(json1);
            chart.addSeries({data:wanjia,name:'万家金服K码激活量'});
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
            allowOverlap:true
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
            color: '#f72347',
            dataLabels: {
                style: {
                    fontWeight: 'bold',
                    color: 'black'
                },
                enabled: true,
                shadow: false
            }
        }, {
            color: '#3ab539',
            dataLabels: {
                style: {
                    fontWeight: 'bold',
                    color: 'black'
                },
                enabled: true,
                shadow: false
            }
        }];

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
    return json;
}
