$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});

/**
 *  日期格式化处理
 * Created by yafei.hou on 2017/11/13.
 */
Date.prototype.format = function (format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
}

function gettodayDate() {
    return new Date().format("yyyy-MM-dd");
}

/**
 * 确保数字输入框中输入负数
 */
function checkNum(name, val) {
    document.getElementById(name).value = (val <= 0 || val === null ? 0 : val);
    console.log(val);
}


//首次加载时执行，初始化时间
var yesterday = new Date();
yesterday.setDate(yesterday.getDate() - 1);
var element1 = document.getElementById('activationDate');
element1.value = yesterday.format("yyyy-MM-dd");

var beforeYesterday = new Date();
beforeYesterday.setDate(beforeYesterday.getDate() - 2);
document.getElementById('reportDate').value = beforeYesterday.format("yyyy-MM-dd");

console.log("----选择的日期--begin-" + document.getElementById("reportDate").value);

