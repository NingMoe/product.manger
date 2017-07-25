$(function () {
    baseUrl = $("#baseUrl").val();
    jQuery.axpost = function (e, a, b, c) {
        a = null === a || "" === a || "undefined" === typeof a ? {
            date: (new Date).getTime()
        } : a;
        $.ajax({
            type: "post",
            data: a,
            url: e,
            dataType: "json",
            success: function (a) {
                b(a)
            },
            error: function (a) {
                c(a)
            }
        })
    };

    function data(a, dataAxis, datay) {
        for (var key in a) {
            dataAxis.push(key);
            datay.push(a[key]);
        }
        return {"dataAxis": dataAxis, "datay": datay}
    }

    //左边地图
    ajaxData(baseUrl + '/balance/location/statistic', "", showMap);
    checkNum();
    setInterval('getdata()', 5000); //每隔10秒执行一次

    //第一个
    var dataAxis = [];
    var datay = [];
    var myChart = echarts.init(document.getElementById('balanceone'));
    setTimeout($.axpost(baseUrl + "/balance/location/day", {"day": 30, "type": "lianbi", "pageSize": 12}, function (a) {
        var number = data(a.data, dataAxis, datay);
        setoption('体脂秤K码激活地区分布前12名(月)', ['#cf641c', '#fe6161', '#ff7708'], number.dataAxis.reverse(), number.datay.reverse());
        myChart.setOption(option);
    }, function (c) {
        // alert("接口出错了"+c)
    }), 3000);


    //第二个
    var dataAxis1 = [];
    var datay1 = [];
    var myChart1 = echarts.init(document.getElementById('balancetwo'));
    setTimeout($.axpost(baseUrl + "/balance/location/month", {
        "month": 12,
        "type": "lianbi",
        "pageSize": 12
    }, function (a) {
        var number = data(a.data, dataAxis1, datay1);
        setoption('体脂秤K码激活地区分布前12名(年)', ['#cf641c', '#fe6161', '#ff7708'], number.dataAxis.reverse(), number.datay.reverse());
        myChart1.setOption(option);
    }, function (c) {
        // alert("接口出错了"+c)
    }), 5000);

    //第三个
    var dataAxis2 = [];
    var datay2 = [];
    var myChart2 = echarts.init(document.getElementById('balancethird'));
    $.axpost(baseUrl + "/balance/location/day", {"day": 30, "type": "balance", "pageSize": 12}, function (a) {
        var number = data(a.data, dataAxis2, datay2);
        setoption('体脂秤使用地区分布前12名(月)', ['#cf641c', '#fe6161', '#ff7708'], number.dataAxis.reverse(), number.datay.reverse());
        myChart2.setOption(option);
    }, function (c) {
        // alert("接口出错了"+c);
    });


    //第四个
    var dataAxis3 = [];
    var datay3 = [];
    var myChart3 = echarts.init(document.getElementById('balancefour'));
    $.axpost(baseUrl + "/balance/location/month", {"month": 12, "type": "balance", "pageSize": 12}, function (a) {
        var number = data(a.data, dataAxis3, datay3);
        setoption('体脂秤使用地区分布前12名(年)', ['#cf641c', '#fe6161', '#ff7708'], number.dataAxis.reverse(), number.datay.reverse());
        myChart3.setOption(option);
    }, function (c) {
        // alert("接口出错了"+c);
    });

    //第五个
    var dataAxis4 = [];
    var datay4 = [];
    var myChart4 = echarts.init(document.getElementById('balancefive'));
    $.axpost(baseUrl + "/balance/statistic/day", {"day": 12, "type": "balance", "pageSize": 12}, function (a) {
        var number = data(a.data, dataAxis4, datay4);
        setoption('体脂秤新增使用数(月)', ['#34a7e8', '#58ffff'], number.dataAxis.reverse(), number.datay.reverse());
        myChart4.setOption(option);
    }, function (c) {
        // alert("接口出错了"+c);
    });

    //第6个
    var dataAxis5 = [];
    var datay5 = [];
    var myChart5 = echarts.init(document.getElementById('balancesix'));
    $.axpost(baseUrl + "/balance/statistic/month", {"month": 12, "type": "balance"}, function (a) {
        var number = data(a.data, dataAxis5, datay5);
        setoption('体脂秤新增使用数(年)', ['#34a7e8', '#58ffff'], number.dataAxis.reverse(), number.datay.reverse());
        myChart5.setOption(option);
    }, function (c) {
        //alert("接口出错了"+c);
    });
});

function setoption(text, color, dataAxis1, datay1) {
    var color1 = color[0],
        color2 = color[1] || color1,
        color3 = color[2] || color2;
    option = {
        title: {
            text: text,
            textStyle: {
                fontSize: 13,
                color: '#fff'
            },
            padding: [-1, 1],
            // subtext: subtext,
            left: "10px"
        },
        grid: {
            left: "20%"
        },
        xAxis: {
            inverse: true,
            data: dataAxis1,
            splitLine: {
                show: false
            },
            axisTick: {
                show: true
            },
            axisLine: {
                show: true
            },
            axisLabel: {
                inside: false,
                interval: '0',
                rotate: '55',
                textStyle: {
                    color: '#fff',
                    fontSize: 10
                }
            },
            z: -15
        },
        yAxis: {
            // name: "公里",
            axisLine: {
                show: true
            },
            axisTick: {
                show: true
            },
            axisLabel: {
                textStyle: {
                    color: '#fff'
                }
            },
            splitLine: {
                show: true
            }
        },
        dataZoom: [
            {
                type: 'inside',
                start: 0,
                end: 100,
                zoomLock: true
            }
        ],
        series: [
            { // For shadow
                type: 'bar',
                itemStyle: {
                    normal: {color: 'rgba(0,0,0,0.05)'}
                },
                barGap: '20%',
                barCategoryGap: '20%',
                // data: dataShadow,
                animation: false
            },
            {
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: color1},
                                {offset: 0.5, color: color2},
                                {offset: 1, color: color3}
                            ]
                        ),
                        barBorderRadius: [10, 10, 10, 10]
                    },
                    emphasis: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#fe6161'},
                                {offset: 0.7, color: '#fe6161'},
                                {offset: 1, color: '#fec182'}
                            ]
                        )
                    }
                },
                data: datay1
            }
        ]
    };
    return option
}

/*** wanghaidi start ***/

/* annimate */
function show_num(n) {
    var it = $(".t_num i");
    var len = String(n).length;
    var index = (len < 6) ? 1 : 0;
    for (var i = 0; i < len; i++) {
        if (it.length <= i) {
            if ((i + 1) % 3 === 0 && it.length !== i) {
                $(".t_num").append("<img src='../../../resources/image/sales_map/icon01.png' />");
            }
            $(".t_num").append("<span><i></i></span>");
        }
        var num = String(n).charAt(i);
        var y = -parseInt(num) * 50; //y轴位置
        var obj = $(".t_num i").eq(i);
        obj.animate({ //滚动动画
                backgroundPosition: '(0 ' + String(y) + 'px)'
            }, 'slow', 'swing', function () {
            }
        );
    }
}

/* 获取销量数据 */
function checkNum() {
    $.ajax({
        url: baseUrl + '/balance/sales/number',
        type: 'POST',
        dataType: "json",
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        error: function () {
        },
        success: function (data) {
            if (data.data.saleNumber < 100000) {
                $(".num_zero").css("display", "inline-block");
                $(".num_comma").css("display", "inline-block");
            } else if (data.data.saleNumber < 1000000) {
                $(".num_zero1").css("display", "inline-block");
                $(".num_zero2").css("display", "inline-block");
                $(".num_comma").css("display", "inline-block");
            } else if (data.data.saleNumber < 10000000) {
                $(".num_zero1").css("display", "inline-block");
            }
            show_num(data.data.saleNumber);
        }
    });
}

function getdata() {
    $.ajax({
        url: baseUrl + '/balance/sales/number',
        type: 'POST',
        dataType: "json",
        contentType: 'application/json',
        cache: false,
        timeout: 10000,
        error: function () {
        },
        success: function (data) {
            show_num(data.data.saleNumber);
        }
    });
}

/* ajax */
function ajaxData(url, data, callback) {
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            callback(data);
            // console.log(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (200 !== jqXHR.status || 'error' !== textStatus) {
                console.log('接口错误：' + url + ';httpcCode:' + jqXHR.status + ';errtype:' + textStatus)
            }
        }
    })
}

/* 地图数据展示 */
function showMap(data) {
    var str = JSON.stringify(data.data.totalStatistic).replace(/province/g, "name");
    str = str.replace(/number/g, "value");
    str = str.replace(/内蒙/g, "内蒙古");
    var statistic = JSON.parse(str);
    statistic.sort(function (a, b) {
        return b.value - a.value;
    });

    for (var i = 0; i <= 3; i++) {
        var html = '';
        for (var j = 0; j <= 4; j++) {
            html += '<span>' + statistic[5 * i + j].name + '：' + statistic[5 * i + j].value + '</span>';
        }
        $('.ranklist' + (i + 1)).append(html);
    }

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item'
        },
        visualMap: {
            min: 200,
            max: 2000,
            right: '30px',
            top: '60%',
            inRange: {
                color: ['#e1ebfb', '#6ea5ff', '#f9e71e', '#eb7180', '#eb7180', '#eb7180', '#ff0006']
            },
            text: ['High', 'Low'],
            textStyle: {
                color: '#20a5d3'
            }
            // calculable: true,
        },
        series: [
            {
                name: '激活量',
                type: 'map',
                mapType: 'china',
                roam: false,
                top: '22%',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: statistic
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

/*** wanghaidi end ***/
