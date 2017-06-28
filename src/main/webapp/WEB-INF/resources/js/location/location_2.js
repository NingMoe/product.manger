var geoCoordMap = {
    "北京": [116.3976359367, 39.9033991344],
    "天津": [117.1993700000, 39.0851000000],
    "上海": [121.4737000000, 31.2303700000],
    "重庆": [106.5507300000, 29.5647100000],
    "河北": [114.5143000000, 38.0427600000],
    "山西": [112.5506700000, 37.8705900000],
    "辽宁": [123.4292500000, 41.8357100000],
    "吉林": [125.3235700000, 43.8160200000],
    "黑龙江": [126.6628500000, 45.7420800000],
    "江苏": [118.7629500000, 32.0607100000],
    "浙江": [120.1536000000, 30.2655500000],
    "安徽": [117.2856500000, 31.8615700000],
    "福建": [119.2965900000, 26.0998200000],
    "台湾": [121.5200760000, 25.0307240000],
    "江西": [115.8579400000, 28.6820200000],
    "山东": [117.0207600000, 36.6682600000],
    "河南": [113.6249300000, 34.7472500000],
    "湖北": [114.3052500000, 30.5927600000],
    "湖南": [112.9388600000, 28.2277800000],
    "广东": [113.2662700000, 23.1317100000],
    "海南": [110.1998900000, 20.0442200000],
    "四川": [104.0757200000, 30.6508900000],
    "贵州": [106.7072200000, 26.5982000000],
    "云南": [102.8332200000, 24.8796600000],
    "陕西": [108.9398400000, 34.3412700000],
    "甘肃": [103.8341700000, 36.0613800000],
    "青海": [101.7801100000, 36.6208700000],
    "内蒙": [111.7519900000, 40.8414900000],
    "广西": [108.3669000000, 22.8167300000],
    "西藏": [91.11450000000, 29.6441500000],
    "宁夏": [106.2324800000, 38.4864400000],
    "新疆": [87.6168800000, 43.82663000000],
    "香港": [114.1654600000, 22.2753400000],
    "澳门": [113.5491300000, 22.1987500000]
};

function getTotalStatisticForBarY(totalStatistic) {
    var yList = [];
    var length = totalStatistic.length;
    for (var i = 0; i < length; i++) {
        yList.push(totalStatistic[i].province);
    }
    return yList.reverse();
}
function getTotalStatisticForBarX(totalStatistic) {
    var xList = [];
    var length = totalStatistic.length;
    for (var i = 0; i < length; i++) {
        xList.push(totalStatistic[i].number);
    }
    return xList.reverse();
}
function getScatterData(totalStatistic) {
    var result = [];
    totalStatistic.forEach(function (item) {
        var province = item.province;
        var number = item.number;
        var p = geoCoordMap[province];
        var obj = {};
        obj.name = province;
        obj.value = [p[0], p[1], number, 10];
        result.push(obj);
    });
    return result;
}
function getEffectScatter(totalStatistic) {
    var result = [];
    for (var i = 0; i < topN && i < totalStatistic.length; i++) {
        var item = totalStatistic[i];
        var province = item.province;
        var number = item.number;
        var p = geoCoordMap[province];
        var obj = {};
        obj.name = province;
        obj.value = [p[0], p[1], number, 20];
        result.push(obj);
    }
    return result;
}
function getTotalNumber(totalStatistic) {
    var result = 0;
    for (var i = 0; i < totalStatistic.length; i++) {
        result += totalStatistic[i].number;
    }
    return result;
}
function refreshData() {
    var url = $("#baseUrl").val() + "/balance/location/statistic";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        dataType: "json",
        error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            var totalStatistic = data.totalStatistic;
            totalStatistic.sort(function (a, b) {
                return b.number - a.number;
            });
            var dataOption = {
                yAxis: [
                    {
                        id: 'totalStatistic',
                        data: getTotalStatisticForBarY(totalStatistic)
                    }
                ],
                series: [
                    {
                        id: 'totalStatistic',
                        data: getTotalStatisticForBarX(totalStatistic)
                    }, {
                        id: 'scatter',
                        data: getScatterData(totalStatistic)
                    }, {
                        id: 'effectScatter',
                        data: getEffectScatter(totalStatistic)
                    }
                ]
            };
            myChart.setOption(dataOption);
            var totalNumber = getTotalNumber(totalStatistic);
            // counter.update(totalNumber);
        }
    });
}
var topN = 15;

var mainOption = {
    backgroundColor: '#404a59',
    animation: true,
    animationDuration: 1000,
    animationEasing: 'cubicInOut',
    animationDurationUpdate: 1000,
    animationEasingUpdate: 'cubicInOut',
    title: [
        {
            id: 'totalStatistic',
            text: '地区销量分布',
            right: '1%',
            top: '21%',
            width: 100,
            textStyle: {
                color: '#fff',
                fontSize: 16,
                fontWeight: 300,
                fontStyle: 'consolas'
            }
        },
        {
            id: 'chinaMap',
            text: '智能体脂秤S7销售地区统计图',
            left: 'center',
            textStyle: {
                color: '#fff',
                fontFamily: 'consolas',
                fontSize: 35,
                fontWeight: 600
            },
            padding: [
                50, 0, 0, 0
            ]
        }
    ],
    toolbox: {
        iconStyle: {
            normal: {
                borderColor: '#fff'
            },
            emphasis: {
                borderColor: '#b1e4ff'
            }
        }
    },
    geo: {
        map: 'china',
        left: '200',
        top: '140',
        right: '35%',
        center: [113.98561551896913, 37.205000490896193],
        zoom: 0.95,
        label: {
            emphasis: {
                show: false
            }
        },
        roam: true,
        itemStyle: {
            normal: {
                areaColor: '#323c48',
                borderColor: '#111'
            },
            emphasis: {
                areaColor: '#2a333d'
            }
        }
    },
    tooltip: {
        trigger: 'item'
    },
    grid: [
        {
            show: true,
            zlevel: 0,
            right: 50,
            top: '27%',
            height: '65%',
            width: '33%',
            borderWidth: 0
        }
    ],
    xAxis: [
        {
            id: 'totalStatistic',
            type: 'value',
            gridIndex: 0,
            min: 0,
            scale: true,
            position: 'top',
            boundaryGap: false,
            splitLine: {show: false},
            axisLine: {show: false},
            axisTick: {show: false},
            axisLabel: {
                margin: 2,
                textStyle: {
                    color: '#fff',
                    fontSize: 25,
                    fontWeight: 300,
                    fontStyle: 'consolas'
                }
            }
        }
    ],
    yAxis: [
        {
            id: 'totalStatistic',
            type: 'category',
            name: '省份',
            gridIndex: 0,
            axisLine: {show: false, lineStyle: {color: '#ddd'}},
            axisTick: {show: false, lineStyle: {color: '#ddd'}},
            axisLabel: {
                interval: 0,
                textStyle: {
                    color: '#fff',
                    fontSize: 25,
                    fontWeight: 300,
                    fontStyle: 'consolas'
                }
            },
            data: []
        }
    ],
    series: [
        {
            id: 'totalStatistic',
            xAxisIndex: 0,
            yAxisIndex: 0,
            zlevel: 2,
            type: 'bar',
            symbol: 'none',
            itemStyle: {
                normal: {
                    color: '#FF9933'
                }
            },
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    offset: [0, 0],
                    formatter: function(item) {
                        return item['data'];
                    },
                    textStyle: {
                        color: '#fff',
                        fontStyle: 'normal',
                        fontWeight: '400',
                        fontFamily: 'consolas',
                        fontSize: 20
                    }
                },
                emphasis: {
                    show: true,
                    position: 'right',
                    offset: [0, 0],
                    formatter: function(item) {
                        return item['data'];
                    },
                    textStyle: {
                        color: '#fff',
                        fontStyle: 'normal',
                        fontWeight: '400',
                        fontFamily: 'consolas',
                        fontSize: 20
                    }
                }
            },
            data: []
        }, {
            id: 'scatter',
            name: '销售数量',
            type: 'scatter',
            coordinateSystem: 'geo',
            data: [],
            symbolSize: function (val) {
                return val[3];
            },
            label: {
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    show: true
                },
                emphasis: {
                    show: true
                }
            },
            itemStyle: {
                normal: {
                    color: '#ddb926'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: function(item) {
                    console.log(JSON.stringify(item));
                    var s = item["seriesName"];
                    var c = item["data"]["name"];
                    var v = item["data"]["value"][2];
                    return s + '<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#ddb926;"></span>' + c + ": " + v;
                }
            }
        }, {
            id: 'effectScatter',
            name: '销售数量',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            data: [],
            symbolSize: function (val) {
                return val[3];
            },
            showEffectOn: 'render',
            rippleEffect: {
                brushType: 'stroke'
            },
            hoverAnimation: true,
            label: {
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    dataIndex: 3,
                    show: true
                }
            },
            itemStyle: {
                normal: {
                    color: '#f4e925',
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            zlevel: 1,
            tooltip: {
                trigger: 'item',
                formatter: function(item) {
                    console.log(JSON.stringify(item));
                    var s = item["seriesName"];
                    var c = item["data"]["name"];
                    var v = item["data"]["value"][2];
                    return s + '<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#f4e925;"></span>' + c + ": " + v;
                }
            }
        }
    ]
};
var myChart = echarts.init(document.getElementById('main'));
myChart.setOption(mainOption);
refreshData();
setInterval(function () {
    refreshData();
}, 300000);
$('.counter').countUp();