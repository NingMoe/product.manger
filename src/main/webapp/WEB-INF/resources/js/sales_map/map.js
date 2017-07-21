$(function(){
    jQuery.axpost = function(e, a, b, c) {
        a = null == a || "" == a || "undefined" == typeof a ? {
            date: (new Date).getTime()
        } : a;
        $.ajax({
            type: "post",
            data: a,
            url: e,
            dataType: "json",
            success: function(a) {
                b(a)
            },
            error: function(a) {
                c(a)
            }
        })
    };
    function data(a,dataAxis,datay) {
        for(var key in a){
            dataAxis.push(key);
            datay.push(a[key]);
        }
        return {"dataAxis":dataAxis,"datay":datay}
    }

    //左边地图
    ajaxData('https://test.phicomm.com/product-manger/balance/location/statistic',"",showMap);
    getdata(); 
    setInterval('getdata()', 5000); //每隔10秒执行一次

    //第一个
    var dataAxis = [];
    var datay = [];
    var myChart = echarts.init(document.getElementById('balanceone'));
    setTimeout($.axpost("https://test.phicomm.com/product-manger/balance/location/day",{"day":30,"type":"lianbi","pageSize":12},function (a) {
        var number = data(a.data,dataAxis,datay);
        setoption('体脂秤K码激活地区分布前12名(月)',['#fe6161','#fe6161','#fec182'],number.dataAxis.reverse(),number.datay.reverse());
        myChart.setOption(option);
    },function (c) {
        // alert("接口出错了"+c)
    }),3000);

    //第二个
    var dataAxis1 = [];
    var datay1 = [];
    var myChart1 = echarts.init(document.getElementById('balancetwo'));
    setTimeout($.axpost("https://test.phicomm.com/product-manger/balance/location/month",{"month":12,"type":"lianbi","pageSize":12},function (a) {
        var number = data(a.data,dataAxis1,datay1);
        setoption('体脂秤K码激活地区分布前12名(年)',['#fe6161','#fe6161','#fec182'],number.dataAxis.reverse(),number.datay.reverse());
        myChart1.setOption(option);
    },function (c) {
        // alert("接口出错了"+c)
    }),5000);

    //第三个
    var dataAxis2 = [];
    var datay2 = [];
    var myChart2 = echarts.init(document.getElementById('balancethird'));
    $.axpost("https://test.phicomm.com/product-manger/balance/location/day",{"day":30,"type":"balance","pageSize":12},function (a) {
        var number = data(a.data,dataAxis2,datay2);
        setoption('体脂秤使用地区分布前12名(月)',['#fe6161','#fe6161','#fec182'],number.dataAxis,number.datay);
        myChart2.setOption(option);
    },function (c) {
       // alert("接口出错了"+c);
    });


    //第四个
    var dataAxis3 = [];
    var datay3 = [];
    var myChart3 = echarts.init(document.getElementById('balancefour'));
    $.axpost("https://test.phicomm.com/product-manger/balance/location/month",{"month":12,"type":"balance","pageSize":12},function (a) {
        var number = data(a.data,dataAxis3,datay3);
        setoption('体脂秤使用地区分布前12名(月)',['#fe6161','#fe6161','#fec182'],number.dataAxis,number.datay);
        myChart3.setOption(option);
    },function (c) {
       // alert("接口出错了"+c);
    });

    //第五个
    var dataAxis4 = [];
    var datay4 = [];
    var myChart4 = echarts.init(document.getElementById('balancefive'));
    $.axpost("https://test.phicomm.com/product-manger/balance/statistic/day",{"day":12,"type":"balance","pageSize":12},function (a) {
        var number = data(a.data,dataAxis4,datay4);
        setoption('体脂秤新增使用数(月)',['#57fdfe'],number.dataAxis,number.datay);
        myChart4.setOption(option);
    },function (c) {
       // alert("接口出错了"+c);
    });

    //第6个
    var dataAxis5 = [];
    var datay5 = [];
    var myChart5 = echarts.init(document.getElementById('balancesix'));
    $.axpost("https://test.phicomm.com/product-manger/balance/statistic/month",{"month":12,"type":"balance"},function (a) {
        var number = data(a.data,dataAxis5,datay5);
        setoption('体脂秤新增使用数(年)',['#57fdfe'],number.dataAxis,number.datay);
        myChart5.setOption(option);
    },function (c) {
       //alert("接口出错了"+c);
    });
});

function setoption(text,color,dataAxis1,datay1) {
    var color1 = color[0],
        color2= color[1] || color1,
        color3 = color[2] ||color1;
    option = {
        title: {
            text:text,
            textStyle:{
                fontSize:15,
                color:'#fff'
            },
            padding:[-1,1],
            // subtext: subtext,
            left: "20px"
        },
        grid:{
            left:"16%"
        },
        xAxis: {
            inverse:true,
            data: dataAxis1,
            splitLine:{
                show:false
            },
            axisTick: {
                show: true
            },
            axisLine: {
                show: true
            },
            axisLabel: {
                inside: false,
                interval:'0',
                rotate:'55',
                textStyle: {
                    color: '#fff',
                    fontSize:12
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
            splitLine:{
                show:true
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
                barGap:'20%',
                barCategoryGap:'20%',
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
                        barBorderRadius:[10, 10, 10, 10]
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
function show_num(n){ 
    var it = $(".t_num i"); 
    var len = String(n).length; 
    for(var i=0;i<len;i++){ 
        if(it.length<=i){ 
            $(".t_num").append("<span><i></i></span>"); 
        } 
        var num=String(n).charAt(i); 
        var y = -parseInt(num)*50; //y轴位置 
        var obj = $(".t_num i").eq(i); 
        obj.animate({ //滚动动画 
            backgroundPosition :'(0 '+String(y)+'px)'  
            }, 'slow','swing',function(){} 
        );
    } 
} 
/* 获取销量数据 */
function getdata(){ 
    $.ajax({ 
        url: 'https://test.phicomm.com/product-manger/balance/sales/number', 
        type: 'POST', 
        dataType: "json", 
        contentType: 'application/json',
        cache: false, 
        timeout: 10000, 
        error: function(){}, 
        success: function(data){ 
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
        success: function(data) {
            callback(data);
            // console.log(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            if (200 != jqXHR.status || 'error' != textStatus) {
                console.log('接口错误：'+url+';httpcCode:' + jqXHR.status + ';errtype:' + textStatus)
            }
        }
    })
}
/* 地图数据展示 */
function showMap(data){
    var str = JSON.stringify(data.data.totalStatistic).replace(/province/g, "name");
    str = str.replace(/number/g, "value");
    var statistic = JSON.parse(str);
    statistic.sort(function (a, b) {
        return b.value - a.value;
    });

    for (var i=0; i<=3; i++) {
        var html = '';
        for (var j=0; j<=4; j++) {
            html += '<span>'+ statistic[5*i+j].name +'：'+ statistic[5*i+j].value +'</span>';
        }
        $('.ranklist'+(i+1)).append(html);
    }

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item'
        },
        visualMap: {
            min: 0,
            max: 2500,
            right: '30px',
            top: '60%',
            inRange: {
                color: ['#e1ebfb', '#6ea5ff', '#f9e71e', '#eb7180', '#ff0006']
            },
            text: ['Hign','Low'],
            textStyle: {
                color: '#20a5d3'
            },
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
                data:statistic
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}
/*** wanghaidi end ***/
