var firstLoad = true;
var startId = 1;
$(function init() {
    fetchFeedback();
});
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
        "startDate": moment().add(-7,'days'),
        "endDate": moment.now
    });
});
function fetchWithRemove() {
    firstLoad = 2;
    removeDiv();
    fetchCertainFeedback();
}
function viewMore() {
    if (firstLoad === 1) {
        fetchFeedback();
    } else {
        fetchCertainFeedback();
    }
}
function fetchCertainFeedback() {
    var timeRange = $("#timeRange").val();
    var appType = $("#appType").find("option:selected").text();
    startTime = moment(timeRange.split('-')[0], "YYYY/MM/DD").format("YYYY-MM-DD");
    endTime = moment(timeRange.split('-')[1], "YYYY/MM/DD").format("YYYY-MM-DD");
    fetch(startTime, endTime, appType);
}
function fetch(startTime, endTime, appType) {
    var baseUrl = $("#baseUrl").val();
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
            var result = data.data;
            if (result.length !== 0) {
                for (var i = 0; i < result.length; i++) {
                    loadItem(result[i]);
                }
                startId = result[result.length - 1].id + 1;
                console.info(startId);
            }
        }
    })
}
function removeDiv() {
    const parent = document.getElementById("parentDiv");
    var childs = parent.childNodes;
    for (var i = childs.length - 1; i >= 0; i--) {
        parent.removeChild(childs[i]);
    }
    startId = 1;
}
function fetchFeedback() {
    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/feedback/fetch",
        dataType: "json",
        data: {
            "pageSize": "10",
            "startId": startId
        },
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            var result = data.data;
            if (result.length !== 0) {
                for (var i = 0; i < result.length; i++) {
                    loadItem(result[i]);
                }
                startId = result[result.length - 1].id + 1;
            }
        }
    })
}

function loadItem(itemData) {
    var src = `<li class="item" id="childDiv" style="padding-right: 20px;padding-left: 20px">` + loadUserHeader(itemData.headerUrl) + loadUsername(itemData.username) +
        loadAppType(itemData.appId) + loadUserId(itemData.userId) + loadFeedback(itemData.feedback);
    var imgDiv = loadFeedbackImg(itemData.imageUrl, itemData.id);
    if (imgDiv !== null) {
        src = src + imgDiv;
    }
    /* src = src + loadCommonDiv() + loadTime(itemData.createTime);*/
    src = src + loadTime(itemData.createTime);
    const parser = new DOMParser();
    const el = parser.parseFromString(src, "text/html");
    const element = el.getElementById("childDiv");
    const parentDiv = document.getElementById("parentDiv");
    parentDiv.appendChild(element);
}

function loadUserHeader(headerUrl) {
    return `<div class="product-img"><img src=` + headerUrl + ` alt="Header"></div>`;
}

function loadUsername(username) {
    return `<div class="product-info">
                        <a href="javascript:void(0)" class="product-title">` + username;
}

function loadUserId(userId) {
    return `<span class="product-description">` + userId + `</span>`;
}

function loadAppType(appId) {
    var src;
    if (appId === 'balance') {
        src = `<span class="label label-success pull-right">` + 'Balance' + `</span></a>`;
    } else if (appId === 'Link') {
        src = `<span class="label label-info pull-right">` + 'Link' + `</span></a>`;
    }
    return src;
}

function loadFeedback(feedback) {
    return `<p style="margin-left: 40px;word-wrap: break-word">` + feedback + `</p>`;
}

function loadFeedbackImg(imageUrl, id) {
    var images = [];
    images = imageUrl;
    if (images === null) {
        return null;
    }
    var src = ` <div class="row" style="margin-left: 40px"><div id='page'><div class='demonstrations'>`;
    for (var i = 0; i < images.length; i++) {
        src = src + `<a href=` + images[i] + ` class='fresco' data-fresco-group=` + id + ` >
                        <img src=` + images[i] + ` style="width: 80px;height: 80px " alt='img'/></a>`;
    }
    src = src + `</div></div></div>`;
    return src;
}

function loadCommonDiv() {
    return `<div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i>
                        </button>
                    </div>`;
}

function loadTime(createTime) {
    return `<div style="margin-left: 40px;margin-top: 10px">
                        <span>提交于:</span><span>` + createTime + `</span>
                    </div>`;
}