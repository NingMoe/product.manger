/**
 *  进行分享内容的图形绘制
 *
 * @param shareDatas 绘制的数据
 */
$(document).ready(function () {
    $("#share-statistic-node").addClass("active");
    $("#share-statistic-menu-node").addClass("active");
    $("#share-statistic-trace-node").addClass("active");
});

/**
 *  初始化加载
 */
$(function statisticUser() {
    const baseUrl = $("#baseUrl").val();
    console.info("baseUrl = " + baseUrl);
    phicommLoading.show();
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
            phicommLoading.hide();
        }
    })
});

/**
 * 进行画图
 *
 * @param shareDatas 来自分享的数据
 */
function layOutShareStatisticContent(shareDatas) {
    let $sharePictureTemplate = $("#col-template");
    let i = 0;
    let startDate = new Date();
    startDate.setDate(startDate.getDate() - 14);
    for (let shareDataKey in shareDatas) {
        if (shareDatas.hasOwnProperty(shareDataKey)) {
            console.info(i);
            let sharePictureIndex = "picture" + i;
            let canvasIndex = "share-statistic-canvas" + i;
            let sharePicture = $sharePictureTemplate.clone().css('display', 'block').attr("id", sharePictureIndex);
            sharePicture.find("#share-statistic-canvas").attr("id", canvasIndex);
            let series = {name: shareDataKey, data: shareDatas[shareDataKey]};
            $("#base-row").append(sharePicture);
            drawOneIndexDaysChart("column", canvasIndex, shareDataKey, "(最近14天)", "分享次数", series, startDate);
            i++;
        }
    }
}

