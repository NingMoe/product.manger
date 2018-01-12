$(document).ready(function () {
    $("#balance-statistic-1").addClass("active");
    $("#balance-statistic-2").addClass("active");
});
$(function statisticUser() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/user/analysis/all",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let labels = [];
            let datas = [];
            for (let key in data.data) {
                if (data.data.hasOwnProperty(key)) {
                    labels.push(key);
                    datas.push(data.data[key]);
                }
            }
            let womenData = datas[0];
            let menData = datas[1];
            let series = [{
                name: "体脂秤用户男女比例",
                data: [["女", womenData],["男", menData]],
                colors: ["#FF7430","#95CEFF"]
            }];
            drawPieChart("user-all-chart", "体脂秤用户男女比例", "(总)", series);
        }
    })
});
$(function statisticUserByAge() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/user/age/v2",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let labels = [];
            let boyData = [];
            let girlData = [];
            let boyMap = data.data["boy"];
            let girlMap = data.data["girl"];
            for (let key in boyMap) {
                if (boyMap.hasOwnProperty(key)) {
                    labels.push(key);
                    boyData.push(boyMap[key]);
                }
            }
            for (let key in girlMap) {
                if (girlMap.hasOwnProperty(key)) {
                    girlData.push(girlMap[key]);
                }
            }
            let series = [
                {name: "男", data: boyData, color: "#95CEFF"},
                {name: "女", data: girlData, color: "#FF7430"}
            ];
            drawMultiIndexCategoryChart("column", "user-age-chart", "体脂称用户男女比例", "(年龄)", "人数",
                labels, series, 0);
        }
    })
});
$(function statisticMember() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/member/analysis/all",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let labels = [];
            let datas = [];
            for (let key in data.data) {
                if (data.data.hasOwnProperty(key)) {
                    labels.push(key);
                    datas.push(data.data[key]);
                }
            }
            let womenData = datas[0];
            let menData = datas[1];
            let series = [{
                name: "体脂秤成员男女比例",
                data: [["女", womenData],["男", menData]],
                colors: ["#FF7430","#95CEFF"]
            }];
            drawPieChart("member-all-chart", "体脂秤成员男女比例", "(总)", series);
        }
    })
});

$(function statisticMemberByAge() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/statistic/member/age/v2",
        dataType: "json",
        contentType: "application/json",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let labels = [];
            let boyData = [];
            let girlData = [];
            let boyMap = data.data["boy"];
            let girlMap = data.data["girl"];
            for (let key in boyMap) {
                if (boyMap.hasOwnProperty(key)) {
                    labels.push(key);
                    boyData.push(boyMap[key]);
                }
            }
            for (let key in girlMap) {
                if (girlMap.hasOwnProperty(key)) {
                    girlData.push(girlMap[key]);
                }
            }
            let series = [
                {name: "男", data: boyData, color: "#95CEFF"},
                {name: "女", data: girlData, color: "#FF7430"}
            ];
            drawMultiIndexCategoryChart("column", "member-age-chart", "体脂称成员男女比例", "(年龄)", "人数",
                labels, series, 0);
        }
    })
});
