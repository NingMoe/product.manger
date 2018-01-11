$(document).ready(function () {
    $("#share-statistic-node").addClass("active");
    $("#share-statistic-menu-node").addClass("active");
    $("#share-statistic-trace-node").addClass("active");
});

$(function statisticUser() {
    const baseUrl = $("#baseUrl").val();
    console.info("baseUrl = " + baseUrl);
    $.ajax({
        type: "POST",
        url: baseUrl + "/share/statistic/14days",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let shareDatas = data.data;
            layOutShareStatisticContent(shareDatas);
        }
    })
});

Object.size = function (obj) {
    let size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};

/**
 *  进行分享内容的图形绘制
 *
 * @param shareDatas 绘制的数据
 */
function layOutShareStatisticContent(shareDatas) {
    let $sharePictureTemplate = $("#col-template");
    let labels = getTimeLabels();
    let i = 0;
    let startDate = new Date();
    startDate.setDate(startDate.getDate() - 14);
    for (let shareDataKey in shareDatas) {
        console.info(i);
        let sharePictureIndex = "picture" + i;
        let canvasIndex = "share-statistic-canvas" + i;
        let sharePicture = $sharePictureTemplate.clone().css('display', 'block').attr("id", sharePictureIndex);
        sharePicture.find("#share-statistic-canvas").attr("id", canvasIndex);
        let series = {name: shareDataKey, data: shareDatas[shareDataKey]};
        $("#base-row").append(sharePicture);
        drawOneIndexDaysChart("column", canvasIndex, shareDataKey, "(最近14天)", null, series, startDate);
        i++;
    }
}

/**
 * 绘制chart图形
 *
 * @param ctx         需要绘制的canvas
 * @param type        图形的类型，比如'bar','line','radar'等
 * @param labels      横坐标
 * @param label       图标名
 * @param dataset     数据集
 */
function drawBarChart(ctx, type, labels, label, dataset) {
    new Chart(ctx, {
        type: type,
        data: {
            labels: labels,
            datasets: [
                {
                    label: label,
                    data: dataset,
                    backgroundColor: '#666600'
                }
            ]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}

/**
 * 获取过去14天的日期
 *
 * @returns {Array} 日期
 */
function getTimeLabels() {
    let labels = [];
    for (let i = 0; i < 14; i++) {
        labels.push(new Date().DateAdd('d', -14 + i).Format('yyyy-MM-dd'));
    }
    return labels;
}

/**
 * 格式化日期
 *
 * @param fmt   日期格式
 * @returns {*} 格式化后的日期
 * @constructor
 */
Date.prototype.Format = function (fmt) {
    let o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

/**
 * 根据时间差，获取日期
 *
 * @param strInterval 表示时间差单位
 * @param Number 表示具体的时间差数值
 * @returns {Date} 返回日期
 * @constructor
 */
Date.prototype.DateAdd = function (strInterval, Number) {
    let dtTmp = this;
    switch (strInterval) {
        case 's':
            return new Date(Date.parse(dtTmp) + (1000 * Number));
        case 'n':
            return new Date(Date.parse(dtTmp) + (60 * 1000 * Number));
        case 'h':
            return new Date(Date.parse(dtTmp) + (60 * 60 * 1000 * Number));
        case 'd':
            return new Date(Date.parse(dtTmp) + (24 * 60 * 60 * 1000 * Number));
    }
};
