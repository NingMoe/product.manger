$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});

/**
 * 获取mac统计信息
 */
function obtainMacInfo() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/mac/info",
        dataType: "json",
        data: {
            "searchParam": $("#inputParam").val()
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            const status = data.status;
            if (status !== 0) {
                alert('Error !');
            } else {
                const result = data.data;
                console.info(result);
                document.getElementById("result").style.visibility = 'visible';
                let createTime = result.createTime;
                let activeLocation = result.activeLocation;
                let memberCount = result.memberCount;
                if (createTime === null) {
                    createTime = "尚未使用";
                    memberCount = "尚未绑定";
                    if (activeLocation === "无激活信息") {
                        activeLocation = "尚未激活";
                    }
                } else {
                    if (memberCount === 0) {
                        memberCount = "尚未绑定";
                    }
                }
                $("#createTime").text(createTime);
                $("#bindNumber").text(memberCount);
                $("#activeLocation").text(activeLocation);
            }
        }
    })
}