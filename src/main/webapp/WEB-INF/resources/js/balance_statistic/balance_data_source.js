/**
 * Created by yafei.hou on 2017/12/28.
 */
$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});

$(function getBalanceElectrodeDisplay() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/measure/data/source",
        contentType: "application/json",
        dataType: "json",
        error: function (req, status, error) {
            alert("error: " + error);
        },
        success: function (rtValue) {
            let dates = [];
            let autoData = [];
            let claimData = [];
            let lockingData = [];
            console.info(rtValue.data);
            for (var i = rtValue.data.length - 1; i >= 0; i--) {
                dates.push(rtValue.data[i]["date"]);
                autoData.push(rtValue.data[i]["auto_claim"]);
                claimData.push(rtValue.data[i]["locking"]);
                lockingData.push(rtValue.data[i]["claim"]);
            }
            let title = "体脂秤称量数据来源";
            let subTitile = "最近10天数据来源";
            let series = [
                {name: "自动认领", data: autoData},
                {name: "手动认领", data: claimData},
                {name: "锁定上称", data: lockingData}
            ];
            let startDate = new Date();
            startDate.setDate(startDate.getDate() - 10);
            drawMultiIndexDaysChart("column", "balance-data-source", title, subTitile, "次数", series, startDate, 1);
        }
    });
});
