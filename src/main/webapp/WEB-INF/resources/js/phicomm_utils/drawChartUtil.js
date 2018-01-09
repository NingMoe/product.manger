/**
 * 作图表的一些基本设定
 *
 * @param chartType 图表的类型
 * @param title 图表的主标题
 * @param subTitle 图表的副标题
 * @param yTitle y轴名称
 * @param data 要传递的数据，数据类型为[{data:[],name:x},{data:[],name:x}...{data:[],name:x}]
 * @returns {{chart: {type: *}, title: {text: *}, subtitle: {text: *}, xAxis: {type: string}, yAxis: {title: {text: *}}, legend: {enabled: boolean}, series: *}}
 */
function drawChartBasicSetting(chartType, title, subTitle, yTitle, data) {
    var plotOptions = {};
    switch (chartType) {
        case 'column':
            plotOptions = {
                series: {
                    dataLabels: {
                        enabled: true
                    }
                }
            }
            break;
        case 'line':
            console.info("line now");
            plotOptions = {
                series: {
                    dataLabels: {
                        enabled: false
                    }
                }
            }
            break;
        default:
    }
    var setting = {
        chart: {
            type: chartType
        },
        title: {
            text: title
        },
        subtitle: {
            text: subTitle
        },
        xAxis: {
            type: 'datetime',
            categories: null
        },
        yAxis: {
            title: {
                text: yTitle
            }
        },
        legend: {
            enabled: false
        },
        series: data,
        plotOptions: plotOptions
    };
    return setting;
}


/**
 * 制作单一指标最近n天图表
 *
 * @param chartType 图表的类型，诸如"column", "line", "bar"
 * @param divId 页面中指定的作图divId
 * @param title 主标题
 * @param subTitle 副标题
 * @param yTitle y轴名称
 * @param data 传递的数据，数据类型为{name:xxx,data:[]}
 * @param startDate 开始的日期,为Date类型
 */
function drawOneIndexDaysChart(chartType, divId, title, subTitle, yTitle, data, startDate) {
    var settings = drawChartBasicSetting(chartType, title, subTitle, yTitle, [data]);
    var plotOptions = {
        series: {
            pointStart: Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()),
            pointIntervalUnit: 'day'
        }
    };
    settings.plotOptions = plotOptions;
    Highcharts.chart(divId, settings);
}

/**
 * 制作单一指标最近n月图表
 *
 * @param chartType 图表的类型，诸如"column", "line", "bar"
 * @param divId 页面中指定的作图divId
 * @param title 主标题
 * @param subTitle 副标题
 * @param yTitle y轴名称
 * @param data 传递的数据，数据类型为{name:xxx,data:[]}
 * @param startDate 开始的日期
 */
function drawOneIndexMonthsChart(chartType, divId, title, subTitle, yTitle, data, startDate) {
    var settings = drawChartBasicSetting(chartType, title, subTitle, yTitle, [data]);
    var plotOptions = {
        series: {
            pointStart: Date.UTC(startDate.getFullYear(), startDate.getMonth()),
            pointIntervalUnit: 'month'
        }
    };
    settings.plotOptions = plotOptions;
    Highcharts.chart(divId, settings);
}

/**
 * 制作单一指标最近n年图表
 *
 * @param chartType 图表的类型，诸如"column", "line", "bar"
 * @param divId 页面中指定的作图divId
 * @param title 主标题
 * @param subTitle 副标题
 * @param yTitle y轴名称
 * @param data 传递的数据，数据类型为{name:xxx,data:[]}
 * @param startDate 开始的日期
 */
function drawOneIndexYearsChart(chartType, divId, title, subTitle, yTitle, data, startDate) {
    var settings = drawChartBasicSetting(chartType, title, subTitle, yTitle, [data]);
    var plotOptions = {
        series: {
            pointStart: Date.UTC(startDate.getFullYear()),
            pointIntervalUnit: 'year'
        }
    };
    settings.plotOptions = plotOptions;
    Highcharts.chart(divId, settings);
}

/**
 * 制作单一指标24小时图表
 *
 * @param chartType 图表的类型，诸如"column", "line", "bar"
 * @param divId 页面中指定的作图divId
 * @param title 主标题
 * @param subTitle 副标题
 * @param yTitle y轴名称
 * @param data 传递的数据，数据类型为{name:xxx,data:[]}
 */
function drawOneIndex24HoursChart(chartType, divId, title, subTitle, yTitle, data) {
    var settings = drawChartBasicSetting(chartType, title, subTitle, yTitle, [data]);
    settings.xAxis.type = "linear";
    Highcharts.chart(divId, settings);
}

function drawOneIndexCatagoryChart(chartType, divId, title, subTitle, yTitle, xCategories, data) {
    var settings = drawChartBasicSetting(chartType, title, subTitle, yTitle, [data]);
    console.info("xCategories = " + xCategories);
    settings.xAxis.type = "category";
    settings.xAxis.categories = xCategories;
    Highcharts.chart(divId, settings);
}

/**
 * 制作多指标最近n天图表
 *
 * @param chartType 图表的类型，诸如"column", "line", "bar"
 * @param divId 页面中指定的作图divId
 * @param title 主标题
 * @param subTitle 副标题
 * @param yTitle y轴名称
 * @param data 传递的数据，数据类型为[{name:xxx,data:[]},{name:xxx,data:[]}...,{name:xxx,data:[]}]
 * @param startDate 开始的日期,为Date类型
 * @param enableLegendRight legend是位于右边还是底部,1表示位于右边，否则位于底部
 */
function drawMultiIndexDaysChart(chartType, divId, title, subTitle, yTitle, data, startDate, enableLegendRight) {
    var settings = drawChartBasicSetting(chartType, title, subTitle, yTitle, data);
    var plotOptions = {
        series: {
            pointStart: Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()),
            pointIntervalUnit: 'day'
        }
    };
    settings.plotOptions = plotOptions;
    settings.legend.enabled = true;
    console.info("enable = " + enableLegendRight);
    if (enableLegendRight == 1) {
        console.info("in enableLegendRight");
        settings.legend.layout = "vertical";
        settings.legend.align = "right";
        settings.legend.verticalAlign = "middle";
    }
    Highcharts.chart(divId, settings);
}

/**
 * 制作多指标最近n月图表
 *
 * @param chartType 图表的类型，诸如"column", "line", "bar"
 * @param divId 页面中指定的作图divId
 * @param title 主标题
 * @param subTitle 副标题
 * @param yTitle y轴名称
 * @param data 传递的数据，数据类型为[{name:xxx,data:[]},{name:xxx,data:[]}...,{name:xxx,data:[]}]
 * @param startDate 开始的日期,为Date类型
 * @param enableLegendRight legend是位于右边还是底部,1表示位于右边，否则位于底部
 */
function drawMultiIndexMonthsChart(chartType, divId, title, subTitle, yTitle, data, startDate, enableLegendRight) {
    var settings = drawChartBasicSetting(chartType, title, subTitle, yTitle, data);
    var plotOptions = {
        series: {
            pointStart: Date.UTC(startDate.getFullYear(), startDate.getMonth()),
            pointIntervalUnit: 'month'
        }
    };
    settings.plotOptions = plotOptions;
    settings.legend.enabled = true;
    if (enableLegendRight == 1) {
        console.info("in enableLegendRight");
        settings.legend.layout = "vertical";
        settings.legend.align = "right";
        settings.legend.verticalAlign = "middle";
    }
    Highcharts.chart(divId, settings);
}

/**
 * 制作多指标最近n年图表
 *
 * @param chartType 图表的类型，诸如"column", "line", "bar"
 * @param divId 页面中指定的作图divId
 * @param title 主标题
 * @param subTitle 副标题
 * @param yTitle y轴名称
 * @param data 传递的数据，数据类型为[{name:xxx,data:[]},{name:xxx,data:[]}...,{name:xxx,data:[]}]
 * @param startDate 开始的日期,为Date类型
 * @param enableLegendRight legend是位于右边还是底部,1表示位于右边，否则位于底部
 */
function drawMultiIndexYearsChart(chartType, divId, title, subTitle, yTitle, data, startDate, enableLegendRight) {
    var settings = drawChartBasicSetting(chartType, title, subTitle, yTitle, data);
    var plotOptions = {
        series: {
            pointStart: Date.UTC(startDate.getFullYear()),
            pointIntervalUnit: 'year'
        }
    };
    settings.plotOptions = plotOptions;
    settings.legend.enabled = true;
    if (enableLegendRight == 1) {
        console.info("in enableLegendRight");
        settings.legend.layout = "vertical";
        settings.legend.align = "right";
        settings.legend.verticalAlign = "middle";
    }
    Highcharts.chart(divId, settings);
}

/**
 * 制作多指标24小时图表
 *
 * @param chartType 图表的类型，诸如"column", "line", "bar"
 * @param divId 页面中指定的作图divId
 * @param title 主标题
 * @param subTitle 副标题
 * @param yTitle y轴名称
 * @param data 传递的数据，数据类型为[{name:xxx,data:[]},{name:xxx,data:[]}...,{name:xxx,data:[]}]
 * @param enableLegendRight legend是位于右边还是底部,1表示位于右边，否则位于底部
 */
function drawMultiIndex24HoursChart(chartType, divId, title, subTitle, yTitle, data, enableLegendRight) {
    var settings = drawChartBasicSetting(chartType, title, subTitle, yTitle, data);
    settings.legend.enabled = true;
    settings.xAxis.type = "linear";
    if (enableLegendRight == 1) {
        console.info("in enableLegendRight");
        settings.legend.layout = "vertical";
        settings.legend.align = "right";
        settings.legend.verticalAlign = "middle";
    }
    Highcharts.chart(divId, settings);
}


