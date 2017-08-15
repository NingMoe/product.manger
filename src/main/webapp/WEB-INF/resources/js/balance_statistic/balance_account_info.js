$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});

/**
 * 获取数量信息
 */
$(function obtainAccountInfo() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/account",
        contentType: "application/json",
        dataType: "json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            console.info(data);
            let result = data.data;
            console.info(result);
            if (result !== null && data.status === 0) {
                let memberCount = result.memberCount;
                let userCount = result.userCount;
                let macCount = result.macCount;
                drawUserAndMember(userCount, memberCount);
                drawTotalChart(userCount, memberCount, macCount);
                drawMemberAndMac(macCount, memberCount);
                drawUserAndMac(userCount, macCount);
            } else {
                alert("fail");
                console.info(data);
            }
        }
    })
});

/**
 * 总体
 */
function drawTotalChart(userValue, memberValue, macValue) {
    let pieData = [
        {
            value: memberValue,
            color: "#3c8dbc",
            highlight: "#3c8dbc",
            label: "成员数"
        },
        {
            value: userValue,
            color: "#00a65a",
            highlight: "#00a65a",
            label: "用户数"
        },
        {
            value: macValue,
            color: "#f39c12",
            highlight: "#f39c12",
            label: "电子秤数"
        }
    ];
    drawChart(pieData, new Chart($("#totalInfo").get(0).getContext("2d")));
}

/**
 * 用户与mac
 */
function drawUserAndMac(userValue, macValue) {
    let pieData = [
        {
            value: macValue,
            color: "#f39c12",
            highlight: "#f39c12",
            label: "电子秤数"
        },
        {
            value: userValue,
            color: "#00a65a",
            highlight: "#00a65a",
            label: "用户数"
        }
    ];
    drawChart(pieData, new Chart($("#macAndUser").get(0).getContext("2d")));
}

/**
 * mac与成员
 */
function drawMemberAndMac(macValue, memberValue) {
    let pieData = [
        {
            value: macValue,
            color: "#f39c12",
            highlight: "#f39c12",
            label: "电子秤数"
        },
        {
            value: memberValue,
            color: "#3c8dbc",
            highlight: "#3c8dbc",
            label: "成员数"
        }
    ];
    drawChart(pieData, new Chart($("#macAndMember").get(0).getContext("2d")));
}

/**
 * 用户与成员
 */
function drawUserAndMember(userValue, memberValue) {
    let pieData = [
        {
            value: userValue,
            color: "#00a65a",
            highlight: "#00a65a",
            label: "用户数"
        },
        {
            value: memberValue,
            color: "#3c8dbc",
            highlight: "#3c8dbc",
            label: "成员数"
        }
    ];
    drawChart(pieData, new Chart($("#userAndMember").get(0).getContext("2d")));
}

/**
 * 绘图
 * @param pieData 数据
 * @param chart 图
 */
function drawChart(pieData, chart) {
    let pieOptions = {
        //Boolean - Whether we should show a stroke on each segment
        segmentShowStroke: true,
        //String - The colour of each segment stroke
        segmentStrokeColor: "#fff",
        //Number - The width of each segment stroke
        segmentStrokeWidth: 2,
        //Number - The percentage of the chart that we cut out of the middle
        percentageInnerCutout: 50, // This is 0 for Pie charts
        //Number - Amount of animation steps
        animationSteps: 100,
        //String - Animation easing effect
        animationEasing: "easeOutBounce",
        //Boolean - Whether we animate the rotation of the Doughnut
        animateRotate: true,
        //Boolean - Whether we animate scaling the Doughnut from the centre
        animateScale: false,
        //Boolean - whether to make the chart responsive to window resizing
        responsive: true,
        // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
        maintainAspectRatio: true,
        //String - A legend template
        legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++)" +
        "{%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label)" +
        "{%><%=segments[i].label%><%}%></li><%}%></ul>"
    };
    chart.Doughnut(pieData, pieOptions);
}