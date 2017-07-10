function obtainMacInfo() {
    $.ajax({
        type: "POST",
        url: "$baseUrl/balance/mac/info",
        dataType: "json",
        data: {
            "mac": $("#inputMac").val()
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            var status = data.status;
            if (status !== 0) {
                alert('Data format error !');
            } else {
                var result = data.data;
                console.info(result);
                document.getElementById("result").style.visibility = 'visible';
                var createTime = result.createTime;
                var activeLocation = result.activeLocation;
                var memberCount = result.memberCount;
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