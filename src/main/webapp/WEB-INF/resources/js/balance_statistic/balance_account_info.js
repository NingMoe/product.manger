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
                drawChart(macCount, userCount, memberCount);
            } else {
                alert("fail");
                console.info(data);
            }
        }
    })
});

function drawChart(macCount, userCount, memberCount) {
    let macAndUserDatas = [{
        name: "用户数与体脂秤数",
        data: [["用户数", userCount], ["体脂秤数", macCount]]
    }];
    let macAndMemberDatas = [{
        name: "成员数与体脂秤数",
        data: [["成员数", memberCount], ["体脂秤数", macCount]]
    }];
    let userAndMemberDatas = [{
        name: "用户数与成员数",
        data: [["用户数", userCount], ["成员数", memberCount]]
    }];
    let totalInfoDatas = [{
        name: "总体信息",
        data: [["用户数", userCount], ["成员数", memberCount], ["体脂秤数", macCount]]
    }];
    drawPieChart("mac-and-user", "用户数与体脂秤数", null, macAndUserDatas);
    drawPieChart("mac-and-member", "用户数与体脂秤数", null, macAndMemberDatas);
    drawPieChart("user-and-member", "用户数与成员数", null, userAndMemberDatas);
    drawPieChart("total-info", "总体信息", null, totalInfoDatas);
}
