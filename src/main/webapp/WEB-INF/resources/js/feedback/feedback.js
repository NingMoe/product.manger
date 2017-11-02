let firstLoad = true;
let startId = 2147483647;
let feedbackUrl = "http://localhost:8081";

/**
 * 初始化  显示10条信息
 */
$(function init() {
    fetchFeedback();
});

/**
 * 日期选择组件
 */
$(function () {
    $('#timeRange').daterangepicker({
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
        "maxDate": "2020/11/20",
        "startDate": moment().add(-7, 'days'),
        "endDate": moment.now
    });
});

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
            if (result.length !== 0) {
                for (let i = 0; i < result.length; i++) {
                    loadItem(result[i]);
                    startId = startId > result[i].id ? result[i].id - 1 : startId - 1;
                }
                console.info(startId);
            }
        }
    })
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
 * 请求获取反馈信息
 */
function fetchFeedback() {
    $.ajax({
        type: "POST",
        url: feedbackUrl + "/feedback/list",
        dataType: "json",
        data: {
            "maxId": "-1"
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let result = data.data;
            console.info(result);
            if (result.length !== 0) {
                for (let i = 0; i < result.length; i++) {
                    loadItem(result[i]);
                    startId = startId > result[i].id ? result[i].id - 1 : startId - 1;
                }
            }
        }
    })
}

/**
 * 动态载入反馈显示
 * @param itemData 某一条反馈信息
 */
function loadItem(itemData) {
    let src = `<li class="item" id="childDiv" style="padding-right: 20px;padding-left: 20px">` +
        loadUserHeader(itemData.imageUrl) + loadUserId(itemData.userId) + loadUsername(itemData.username) +
    loadPhoneNumber(itemData.phoneNumber) + loadAppInfo(itemData.platform, itemData.appVersion) + loadFeedback(itemData.dialogBeans.dialogText);
    let imgDiv = loadFeedbackImg(itemData.dialogPictures, itemData.id);
    if (imgDiv !== null) {
        src = src + imgDiv;
    }
    /* src = src + loadCommonDiv() + loadTime(itemData.createTime);*/
    src = src + loadTime(itemData.createTime) + loadReviewBun();
    const parser = new DOMParser();
    const el = parser.parseFromString(src, "text/html");
    const element = el.getElementById("childDiv");
    const parentDiv = document.getElementById("parentDiv");
    parentDiv.appendChild(element);
}

function loadUserHeader(imageUrl) {
    return `<div class="product-img"><img src=imageUrl alt="Header"></div>`;
}

/**
 * 加载用户名
 */
function loadUsername(username) {
    return `<a href="javascript:void(0)" class="product-title" style="margin-left: 5px">username</a>`;
}

/**
 * 加载电话号码
 */
function loadPhoneNumber(phoneNumber) {
    return `<span class="product-description">` + "手机号：" + phoneNumber + `</span>`;
    /*${userId}*/
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
    return `<div class="product-info"><span class="product-description pull-left">userId</span>`;
}

/**
 * 获取反馈正文模块
 */
function loadFeedback(dialogText) {
    return `<p style="word-wrap: break-word;margin-top: 15px">dialogText</p>`;
}

/**
 * 加载反馈图片
 */
function loadFeedbackImg(images, id) {
    if (images === null) {
        return null;
    }
    let src = ` <div class="row" style="margin-left: 0px"><div id='page'><div class='demonstrations'>`;
    for (let i = 0; i < images.length; i++) {
        src = src + `<a href=${images[i]} class='fresco' data-fresco-group=id >
                        <img src=images[i] style="width: 80px;height: 80px " alt='img'/></a>`;
    }
    return src + `</div></div></div>`;
}

/**
 * 获取共同模块：目前未使用
 * @returns {string} 共同模块
 */
function loadCommonDiv() {
    return `<div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i>
                        </button>
                    </div>`;
}

/**
 * 获取反馈时间模块
 * @param createTime 时间
 */
function loadTime(createTime) {
    return `<div style="margin-top: 15px">
                  <span>提交于:</span>
                  <span>createTime</span>`;
}

/**
 * 加载回复按钮
 */
function loadReviewBun() {
    return `<span style="margin-left: 200px"><a href="#">回复</a> </span></div>`;
}