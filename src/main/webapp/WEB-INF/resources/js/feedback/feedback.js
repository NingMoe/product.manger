let firstLoad = true;
let startId = 2147483647;
let pageNum = 0;
let pageFilterNum = 0;
let currentPage = 1;
let terminalInfo = "";
let isAll = true;
/**
 * 初始化  显示10条信息
 */
$(function init() {
    fetchFeedback(currentPage);
    statistics();
});

/**
 * 日期选择组件
 */
$(function () {
    $('#timeRangeSelected').daterangepicker({
        "autoApply": true,
        "yearOffset": 0,
        "opens": 'center',
        "todayButton": true,
        "locale": {
            "format": "YYYY/MM/DD",
            "separator": " - ",
            "applyLabel": "Apply",
            "cancelLabel": "Cancel",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": [
                "周日",
                "周一",
                "周二",
                "周三",
                "周四",
                "周五",
                "周六"
            ],
            "monthNames": [
                "一月",
                "二月",
                "三月",
                "四月",
                "五月",
                "六月",
                "七月",
                "八月",
                "九月",
                "十月",
                "十一月",
                "十二月"
            ],
            "firstDay": 1
        },
        "minDate": "2016/08/16",
        "maxDate": "2020/11/20"
    });
});

/**
 * 只让有处理意见反馈权限的客服看见操作按键
 */
function getPermissionList() {
    let baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        async: false,
        url: baseUrl + "/feedback/permission/list",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let result = data.data;
            if (JSON.stringify(result).indexOf("feedbackManger") === -1){
                $(".identity").css("display", "none");
            }
        }
    });
}

/**
 * 获取反馈信息的同时，移除之前的反馈显示信息组件
 */
function fetchWithRemove() {
    firstLoad = 2;
    removeDiv();
    fetchCertainFeedback();
}

/**
 * 加载更多反馈：默认反馈只拉10条
 */
function viewMore() {
    if (firstLoad === 1) {
        fetchFeedback();
    } else {
        fetchCertainFeedback();
    }
}

/**
 * 获取特定条件得反馈信息
 */
function fetchCertainFeedback() {
    const timeRange = $("#timeRange").val();
    const appType = $("#appType").find("option:selected").text();
    let startTime = moment(timeRange.split('-')[0], "YYYY/MM/DD").format("YYYY-MM-DD");
    let endTime = moment(timeRange.split('-')[1], "YYYY/MM/DD").format("YYYY-MM-DD");
    fetch(startTime, endTime, appType);
}

/**
 * 根据指定条件获取反馈信息
 * @param startTime 开始时间
 * @param endTime 终止时间
 * @param appType app类型
 */
function fetch(startTime, endTime, appType) {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/feedback/fetch/v2",
        dataType: "json",
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            "pageSize": 10,
            "startId": startId,
            "appType": appType,
            "startTime": startTime,
            "endTime": endTime
        }),
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let result = data.data;
            console.info(result);
            if (result !== null && result.length !== 0) {
                for (let i = 0; i < result.length; i++) {
                    loadItem(result[i]);
                    startId = startId > result[i].id ? result[i].id - 1 : startId - 1;
                }
                console.info(startId);
            }
        }
    });
}

/**
 * 全部
 */
function getAll() {
    isAll = true;
    $("#appIdSelected").val("");
    $("#deviceTypeSelected").val("");
    $("#versionSelected").val("");
    $("#timeRangeSelected").val("");
    $("#pageId"+currentPage)[0].className = "";
    currentPage = 1;
    $("#parentDiv").empty();
    fetchFeedback(currentPage);
}

/**
 * 联动获取产品
 */
function getDeviceType() {
    let appIdSelected = $("#appIdSelected").val();
    const baseUrl = $("#baseUrl").val();
    if (appIdSelected !== ""){
        $.ajax({
            type: "POST",
            url: baseUrl + "/feedback/terminal/list",
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({
                "appId": appIdSelected
            }),
            error: function (req, status, err) {
                alert('Failed reason: ' + err);
            }, success: function (data) {
                let result = data.data;
                $("#deviceTypeSelected").find("option:not(:first)").remove();
                $("#versionSelected").find("option:not(:first)").remove();
                if (JSON.stringify(result) !== "{}"){
                    terminalInfo = result;
                    for (let key in result){
                        $("#deviceTypeSelected").append("<option value="+key+">"+getDeviceName(key)+"</option>");
                    }
                }else{
                    terminalInfo = "";
                    $("#deviceTypeSelected").find("option:not(:first)").remove();
                }


            }
        });
    }else{
        $("#deviceTypeSelected").find("option:not(:first)").remove();
        $("#versionSelected").find("option:not(:first)").remove();
    }
}

/**
 * 将英文单词转化为汉字
 */
function getDeviceName(data) {
    if ("balance" === data){
        return "斐讯智能体脂称S7/S7P";
    }else if ("bloodPressureMeter" === data){
        return "斐讯智能血压计";
    }else if ("w1" === data){
        return "斐讯智能运动手环W1";
    }else if ("w2" === data){
        return "斐讯智能运动手表W2";
    }
}
/**
 * 联动获取版本号
 */
function getVersion() {
    if (terminalInfo !== ""){
        let deviceTypeSelected = $("#deviceTypeSelected").val();
        let versions = "";
        $("#versionSelected").find("option:not(:first)").remove();
        if ("balance" === deviceTypeSelected){
            versions = terminalInfo.balance;
        }else if ("bloodPressureMeter" === deviceTypeSelected){
            versions = terminalInfo.bloodPressureMeter;
        }else if ("w1" === deviceTypeSelected){
            versions = terminalInfo.w1;
        }else if ("w2" === deviceTypeSelected){
            versions = terminalInfo.w2;
        }
        for (let j =0;j<versions.length;j++){
            $("#versionSelected").append("<option value="+versions[j].softVersion+">"+versions[j].softVersion+"</option>");
        }
    }else{
        $("#versionSelected").find("option:not(:first)").remove();
    }

}

/**
 * 点击搜索
 */
function searchFeedback() {
    currentPage = 1;
    getSearchFeedback(currentPage);
}

/**
 * 获取搜索数据
 */
function getSearchFeedback(n) {
    let appIdSelected = $("#appIdSelected").val();
    let deviceTypeSelected = $("#deviceTypeSelected").val();
    let versionSelected = $("#versionSelected").val();
    let timeRange = $("#timeRangeSelected").val();
    let startTime = "";
    let endTime = "";
    if (timeRange !== ""){
        startTime = moment(timeRange.split('-')[0], "YYYY/MM/DD").format("YYYY-MM-DD");
        endTime = moment(timeRange.split('-')[1], "YYYY/MM/DD").format("YYYY-MM-DD");
    }else{
        startTime = "2016-08-16";
        endTime = "2020-11-20";
    }
    if (appIdSelected !== "" || timeRange !== ""){
        isAll = false;
        let baseUrl = $("#baseUrl").val();
        $("#parentDiv").empty();
        $.ajax({
            type: "POST",
            url: baseUrl + "/feedback/page/list/filter",
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({
                "appId": appIdSelected,
                "deviceType": deviceTypeSelected,
                "softVersion": versionSelected,
                "page": n,
                "startTime": startTime,
                "endTime": endTime
            }),
            error: function (req, status, err) {
                alert('Failed reason: ' + err);
            }, success: function (data) {
                let result = data.data;
                pageFilterNum = result.totalCount % 5 === 0 ? parseInt(result.totalCount / 5) : parseInt(result.totalCount / 5) + 1;
                if (result !== null && result.feedbackWithUserInfos !== null && result.feedbackWithUserInfos.length !== 0) {
                    for (let i = 0; i < result.feedbackWithUserInfos.length; i++) {
                        loadItem(result.feedbackWithUserInfos[i]);
                    }
                    paging(pageFilterNum);
                    $("#pageId"+n)[0].className = "active";
                }else{
                    $("#parentDiv").empty();
                    $("#paging").empty();
                }
                getPermissionList();
            }
        });
    }
}

/**
 * 用于移除组件
 */
function removeDiv() {
    const parent = document.getElementById("parentDiv");
    const childs = parent.childNodes;
    for (let i = childs.length - 1; i >= 0; i--) {
        parent.removeChild(childs[i]);
    }
    startId = 2147483647;
}

/**
 * 获取意见统计信息
 */
function statistics() {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        async: false,
        url: baseUrl + "/feedback/statistic",
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let result = data.data;
            let totalCount = result.totalCount;
            $("#totalCount")[0].innerHTML = totalCount;
            $("#newFeedbackCount")[0].innerHTML = result.newFeedbackCount;
            pageNum = totalCount % 5 === 0 ? parseInt(totalCount / 5) : parseInt(totalCount / 5) + 1;
        }
    });
}

/**
 * 分页
 */
function paging(Num) {
    $("#paging").empty();
    $("#paging").append("<li id='first'><a href='javascript:void(0)' onclick='first();'>&lt;&lt;</a></li><li id='previous'><a href='javascript:void(0)' onclick='previous();'>&lt;</a></li>");
    for (let i = 1; i <= Num; i++) {
        let pageId = "pageId"+i;
        $("#paging").append("<li id="+pageId+"><a href='javascript:void(0)' name="+i+" onclick='showPage(this.name);'>" + i + "</a></li>");
    }
    $("#paging").append("<li id='next'><a href='javascript:void(0)' onclick='next();'>&gt;</a></li><li id='end'><a href='javascript:void(0)' onclick='end();'>&gt;&gt;</a></li>");
}

/**
 * 首页
 */
function first() {
    if (isAll === true){
        if (currentPage > 1){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage = 1;
            fetchFeedback(currentPage);
        }
    }else{
        if (currentPage > 1){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage = 1;
            getSearchFeedback(currentPage);
        }
    }

}

/**
 * 上一页
 */
function previous() {
    if (isAll === true){
        if (currentPage > 1){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage--;
            fetchFeedback(currentPage);
        }
    }else{
        if (currentPage > 1){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage--;
            getSearchFeedback(currentPage);
        }
    }

}

/**
 * 下一页
 */
function next() {
    if (isAll === true){
        if (currentPage < pageNum){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage++;
            fetchFeedback(currentPage);
        }
    }else{
        if (currentPage < pageFilterNum){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage++;
            getSearchFeedback(currentPage);
        }
    }
}

/**
 * 末页
 */
function end() {
    if (isAll === true){
        if (currentPage < pageNum){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage = pageNum;
            fetchFeedback(currentPage);
        }
    }else{
        if (currentPage < pageFilterNum){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage = pageFilterNum;
            getSearchFeedback(currentPage);
        }
    }
}

/**
 * 定位具体某页
 */
function showPage(n) {
    if (isAll === true){
        if (n !== currentPage){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage = n;
            fetchFeedback(currentPage);
        }
    }else{
        if (n !== currentPage){
            $("#pageId"+currentPage)[0].className = "";
            $("#parentDiv").empty();
            currentPage = n;
            getSearchFeedback(currentPage);
        }
    }

}

/**
 * 请求获取反馈信息
 */
function fetchFeedback(n) {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/feedback/page/list",
        dataType: "json",
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            "page": n
        }),
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let result = data.data;
            console.info(result);
            if (result !== null && result.length !== 0) {
                for (let i = 0; i < result.length; i++) {
                    loadItem(result[i]);
                    startId = startId > result[i].id ? result[i].id - 1 : startId - 1;
                }
                paging(pageNum);
                $("#pageId"+n)[0].className = "active";
            }else{
                $("#parentDiv").empty();
                $("#paging").empty();
            }
            getPermissionList();
            $("#timeRangeSelected").val("");
        }
    });
}

/**
 * 动态载入反馈显示
 * @param itemData 某一条反馈信息
 */
function loadItem(itemData) {
    let src = `<li class="item" id="childDiv" style="padding-right: 20px;padding-left: 20px">` +
        loadUserHeader(itemData.dialogBeans[0].imageUrl) + loadUserId(itemData.dialogBeans[0].userId) + loadUsername(itemData.dialogBeans[0].username) +
        loadPhoneNumber(itemData.phoneNumber) + loadAppInfo(itemData.platform, itemData.appVersion) + loadFeedback(itemData);
    const parser = new DOMParser();
    const el = parser.parseFromString(src, "text/html");
    const element = el.getElementById("childDiv");
    const parentDiv = document.getElementById("parentDiv");
    parentDiv.appendChild(element);
}

function loadUserHeader(imageUrl) {
    return `<div class="product-img"><img src=${imageUrl} alt="Header"></div>`;
}

/**
 * 加载用户名
 */
function loadUsername(username) {
    return `<a href="javascript:void(0)" class="product-title" style="margin-left: 5px">${username}</a>`;
}

/**
 * 加载电话号码
 */
function loadPhoneNumber(phoneNumber) {
    return `<span class="product-description">` + "手机号：" + phoneNumber + `</span>`;
}

/**
 * 获取手机系统及APP版本号
 */
function loadAppInfo(platform, appVersion) {
    return `<span style="margin-top: 15px" class="product-description">` + "手机系统：" + platform + `&nbsp;&nbsp;&nbsp;版本号：` + appVersion + `</span>`;
}

/**
 * 加载用户ID
 */
function loadUserId(userId) {
    return `<div class="product-info"><span class="product-description pull-left">${userId}</span>`;
}

/**
 * 字符转换
 */
function translateDeviceType(deviceType) {
    switch (deviceType) {
        case "balance":
            return "斐讯智能体脂称S7/S7P";
        case "bloodPressureMeter":
            return "斐讯智能血压计";
        case "w1":
            return "斐讯智能运动手环W1";
        case "w2":
            return "斐讯智能运动手表W2";
        default:
            return "";
    }
}

/**
 * 获取反馈模块
 */
function loadFeedback(itemData) {
    let dialog = itemData.dialogBeans;
    let id = itemData.id;
    let deviceType = translateDeviceType(itemData.deviceType);
    let appId = itemData.appId;
    let src = `<div hidden><span id="appId">${appId}</span></div><div hidden><span id="sessionId">${dialog[0].sessionId}</span></div>`;
    for (let i = 0; i < dialog.length; i++) {
        let images = dialog[i].dialogPictures;
        let headImage = dialog[i].imageUrl;
        if (i === 2) {
            let moreInfo = "moreInfo" + dialog[0].sessionId;
            src = src + `<div id=${moreInfo} hidden>`
        }
        if ("b2c" === dialog[i].dialogType) {
            src = src + `<div style="float: left;margin-top: 15px"><img src=${headImage} style="width: 30px;height: 30px " alt='img'/></a></div>`;
            src = src + ` <div class="row" style="margin-left: 50px;margin-right: 630px"><p style="word-wrap: break-word;margin-top: 15px">${dialog[i].dialogText}</p><div id='page'><div class='demonstrations'>`;
        } else {
            if (i !== 0){
                src = src + `<div class="product-img" style="float: left;margin-top: 15px;margin-left: -60px;"><img src=${headImage} alt='img'/></a></div>`;
            }
            src = src + ` <div class="row" style="margin-left: 0px;margin-right: 630px"><p style="word-wrap: break-word;margin-top: 15px">${dialog[i].dialogText}</p><div id='page'><div class='demonstrations'>`;
        }
        if (images !== null) {
            for (let j = 0; j < images.length; j++) {
                src = src + `<a href=${images[j]} class='fresco' data-fresco-group=${dialog[i].dialogId} >
                   <img src=${images[j]} style="width: 80px;height: 80px " alt='img'/></a>`;
            }
        }
        src = src + `</div></div></div>`;
        if (0 === i) {
            src = src + `<div style="margin-top: 10px"><span class="product-description">产品型号：${deviceType}</span></div>`;
        }
        src = src + `<div style="margin-top: 15px;color: #9999A6;"><span hidden id="dialogId">${dialog[i].dialogId}</span><span>提交于：${dialog[i].createTime}</span>`;
        if ("c2b" === dialog[i].dialogType) {
            src = src + `<span class="identity" style="margin-left: 297px"><a href="javascript:void(0)" name =` + i + ` onclick="replayShow(this, this.name);">回复</a></span></div>`;
        } else if ("b2c" === dialog[i].dialogType) {
            src = src + `<span class="identity" style="margin-left: 250px"><a href="javascript:void(0)" name =` + i + ` onclick="replayShow(this, this.name);">回复</a></span>` +
                `<span class="identity" style="height:10px;width:2px;background-color: #9d9d9d;display: inline-block;margin-left: 10px"></span>` +
                `<span class="identity" style="margin-left: 10px"><a style="color: red" href="javascript:void(0)" name =` + i + ` onclick="deleteReplay(this, this.name);">删除</a></span></div>`;
        }
        src = src + `<div><span style="height:1px;width:515px;background-color: #ECF0F5;display: inline-block;"></span></div>`;
    }
    if (dialog.length > 2) {
        src = src + `</div>`;
        let iconMore = "iconMore" + dialog[0].sessionId;
        let iconLess = "iconLess" + dialog[0].sessionId;
        src = src + `<div id=${iconMore}><a style="color: #0c0c0c;margin-left: 473px;" href="javascript:void(0)" onclick="showMore(this);"><i class="fa fa-angle-double-down"></i><span>&nbsp;更多</span></a></div>`;
        src = src + `<div id=${iconLess} hidden><a style="color: #0c0c0c;margin-left: 473px;" href="javascript:void(0)" onclick="showLess(this);"><i class="fa  fa-angle-double-up"></i><span>&nbsp;收起</span></a></div>`;
    }
    return src;
}

/**
 * 更多
 */
function showMore(node) {
    let sessionId = node.parentNode.parentNode.childNodes[5].innerText;
    let iconMore = "iconMore" + sessionId;
    let moreInfo = "moreInfo" + sessionId;
    let iconLess = "iconLess" + sessionId;
    $("#" + iconMore).css("display", "none");
    $("#" + moreInfo).css("display", "block");
    $("#" + iconLess).css("display", "block");
}

/**
 * 收起
 */
function showLess(node) {
    let sessionId = node.parentNode.parentNode.childNodes[5].innerText;
    let iconMore = "iconMore" + sessionId;
    let moreInfo = "moreInfo" + sessionId;
    let iconLess = "iconLess" + sessionId;
    $("#" + iconMore).css("display", "block");
    $("#" + moreInfo).css("display", "none");
    $("#" + iconLess).css("display", "none");
}

/**
 * 回复展示
 */
function replayShow(node, i) {
    let baseUrl = $("#baseUrl").val();
    let appId = "";
    let sessionId = "";
    if (i >= 2) {
        sessionId = node.parentNode.parentNode.parentNode.parentNode.childNodes[5].innerText;
        appId = node.parentNode.parentNode.parentNode.parentNode.childNodes[4].innerText;
    } else {
        sessionId = node.parentNode.parentNode.parentNode.childNodes[5].innerText;
        appId = node.parentNode.parentNode.parentNode.childNodes[4].innerText;
    }
    $.ajax({
        type: "POST",
        async: false,
        url: baseUrl + "/feedback/lock",
        dataType: "json",
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            "userId": "",
            "sessionId": sessionId
        }),error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            if (data.status === 0) {
                $("#dialogTextReplay").val("");
                for (i = 1; i <= 4; i++) {
                    if ($("#inputList li").length >= 8) {
                        $("#input0" + i).show();
                    }
                    $("#file0" + i).val("");
                    $(".btn-close").parent('li').remove();
                }
                $("#appIdReplay").val(appId);
                $("#sessionIdReplay").val(sessionId);
                $('#replayModal').modal('show');
            } else if (data.status === 2) {
                alert("数据格式错误 !");
            } else if (data.status === 18) {
                alert("回复为空 !");
            } else if (data.status === 21) {
                alert("反馈处于锁定状态 !");
            } else if (data.status === 22) {
                alert("反馈意见不存在！");
            }
        }
    });
}

/**
 * 回复提交
 */
function replaySave() {
    let baseUrl = $("#baseUrl").val();
    let formData = new FormData($("#addReplay")[0]);
    $.ajax({
        type: "POST",
        url: baseUrl + "/feedback/customer/reply",
        contentType: false,
        processData: false,
        dataType: 'json',
        data: formData,
        error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            if (data.status === 0) {
                alert("回复成功！");
            } else if (data.status === 2) {
                alert("数据格式错误 !");
            } else if (data.status === 18) {
                alert("回复为空 !");
            } else if (data.status === 24) {
                alert("反馈意见不存在 !");
            } else if (data.status === 25) {
                alert("该意见反馈处于锁定状态！");
            }
            $('#replayModal').modal('hide');
            $("#parentDiv").empty();
            if (isAll){
                fetchFeedback(currentPage);
            }else{
                getSearchFeedback(currentPage);
            }
        }
    });
}

/**
 * 删除回复
 */
function deleteReplay(node, i) {
    if (confirm("确定删除？")) {
        let baseUrl = $("#baseUrl").val();
        let dialogId = node.parentNode.parentNode.childNodes[0].innerText;
        let sessionId = "";
        if (i >= 2) {
            sessionId = node.parentNode.parentNode.parentNode.parentNode.childNodes[5].innerText;
        } else {
            sessionId = node.parentNode.parentNode.parentNode.childNodes[5].innerText;
        }
        $.ajax({
            type: "POST",
            url: baseUrl + "/feedback/dialog/customer/revoke",
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({
                "customerId": "",
                "dialogId": dialogId,
                "sessionId": sessionId
            }), error: function (req, status, err) {
                console.log('Failed reason: ' + err);
            }, success: function (data) {
                if (data.status === 0) {
                    alert("删除成功！");
                } else if (data.status === 2) {
                    alert("数据格式错误 !");
                } else if (data.status === 21) {
                    alert("反馈处于锁定状态 !");
                }else if (data.status === 24) {
                    alert("会话撤回失败！");
                }
                $("#parentDiv").empty();
                if (isAll){
                    fetchFeedback(currentPage);
                }else{
                    getSearchFeedback(currentPage);
                }

            }
        });
    }
}

/**
 * 添加图片
 */
function preview(file, num) {
    if (file.files && file.files[0]) {
        console.log(file.files[0]);
        if (file.files[0].size >= 5 * 1024 * 1024) {
            alert("上传文件超过5M，请重新上传！");
        } else {
            var reader = new FileReader();
            reader.onload = function (evt) {
                var html = '<li><img id="img0' + num + '" src="' + evt.target.result + '" /><a class="btn-close" href="javascript:;" onclick="deleteImg(this,' + num + ');"></a></li>';
                $("#input01").before(html);
                $("#input0" + num).hide();
                for (i = 1; i <= 4; i++) {
                    if ((i !== num) && ($("#file0" + i).val() === "")) {
                        $("#input0" + i).show();
                        break;
                    }
                }
            }
            reader.readAsDataURL(file.files[0]);
        }
    }
}

/**
 * 删除图片
 */
function deleteImg(img, num) {
    if ($("#inputList li").length >= 8) {
        $("#input0" + num).show();
    }
    $("#file0" + num).val("");
    $(img).parent("li").remove();
}