function downgrade(node) {
    var index = node.parentNode.parentNode.parentNode.childNodes[0].childNodes[1].innerText;
    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/firmware/downgrade",
        dataType: "json",
        data: {
            "id": index
        }, error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            if (data.status === 0) {
                alert("下线成功！");
            } else if (data.status === 11) {
                alert("当前固件不是可用状态");
            } else if (data.status === 19) {
                alert("固件触发失败，请重试！");
            }
            window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
        }
    });
}

function activate(node) {
    var index = node.parentNode.parentNode.parentNode.childNodes[0].childNodes[1].innerText;
    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/firmware/activate",
        dataType: "json",
        data: {
            "id": index
        }, error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            if (data.status === 0) {
                alert("上线成功！");
            } else if (data.status === 11) {
                alert("当前固件已经是可用状态");
            } else if (data.status === 19) {
                alert("固件触发失败，请重试！");
            }
            window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
        }
    });
}

function editWristband(node) {
    $("#wristbandTestModalLabel").text("编辑");
    let id = node.parentNode.parentNode.parentNode.childNodes[0].childNodes[1].innerText;
    let appPlatform = node.parentNode.parentNode.parentNode.childNodes[0].childNodes[3].innerText;
    let appVersionCode = node.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerText;
    let firmwareType = node.parentNode.parentNode.parentNode.childNodes[1].childNodes[3].innerText;
    let hardwareVersion = node.parentNode.parentNode.parentNode.childNodes[2].childNodes[1].innerText;
    let environment = node.parentNode.parentNode.parentNode.childNodes[2].childNodes[3].innerText;
    let firmwareVersion = node.parentNode.parentNode.parentNode.childNodes[3].childNodes[3].innerText;
    let gnssVersion = node.parentNode.parentNode.parentNode.childNodes[4].childNodes[1].innerText;
    let forceUpgrade = node.parentNode.parentNode.parentNode.childNodes[4].childNodes[3].innerText;
    let fotaForceUpgrade = node.parentNode.parentNode.parentNode.childNodes[5].childNodes[1].innerText;
    let description = node.parentNode.parentNode.parentNode.childNodes[6].childNodes[1].lastChild.innerHTML;
    let enable = node.parentNode.parentNode.parentNode.childNodes[9].childNodes[3].innerText;
    $("#id").val(id);
    $("#appPlatform").val(appPlatform);
    $("#appVersionCode").val(appVersionCode);
    $("#firmwareType").val(firmwareType);
    $("#hardwareVersion").val(hardwareVersion);
    $("#environment").val(environment);
    $("#firmwareVersion").val(firmwareVersion);
    $("#gnssVersion").val(gnssVersion);
    $("#forceUpgrade").val(forceUpgrade === "是" ? 1 : 0);
    $("#fotaForceUpgrade").val(fotaForceUpgrade === "是" ? 1 : 0);
    $("#description").val(description);
    $("#enable").val(enable);
    $('#wristbandTestModal').modal();
}

function updateWristband() {
    var baseUrl = $("#baseUrl").val();
    var description = $("#description").val();
    var formData = new FormData($("#updateWristband")[0]);
    formData.append("description", description);
    $.ajax({
        type: "POST",
        url: baseUrl + "/firmware/upgrade/wristband/file/update",
        data: formData,
        contentType: false,
        processData: false,
        dataType: 'json',
        error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            if (data.status === 0) {
                alert("操作成功！");
            } else if (data.status === 2) {
                alert("数据格式错误 !");
            } else if (data.status === 7) {
                alert("文件上传失败 !");
            } else if (data.status === 8) {
                alert("固件版本已经存在 !");
            } else if (data.status === 19) {
                alert("固件触发失败，请重试！");
            }
            window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
        }
    });
}

function deleteFirmware(node) {
    if (confirm("确定删除？")) {
        var index = node.parentNode.parentNode.parentNode.childNodes[0].childNodes[1].innerText;
        var baseUrl = $("#baseUrl").val();
        $.ajax({
            type: "POST",
            url: baseUrl + "/firmware/delete",
            dataType: "json",
            data: {
                "id": index
            }, error: function (req, status, err) {
                console.log('Failed reason: ' + err);
            }, success: function (data) {
                if (data.status === 0) {
                    alert("删除成功！");
                } else if (data.status === 9) {
                    alert("需要删除的固件不存在");
                } else if (data.status === 12) {
                    alert("该固件当前正在使用");
                }
                window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
            }
        });
    }
}

function showAddAppVersion(node) {
    $("#appVersionAddModalLabel").text("添加新的APP版本号");
    var id = node.parentNode.parentNode.parentNode.childNodes[0].childNodes[1].innerText;
    var appVersionCode = node.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerText;
    $("#idAdd").val(id);
    $("#appVersionCodeAdd").val(appVersionCode);
    $('#appVersionAddModal').modal();
}

function saveAppVersion() {
    var baseUrl = $("#baseUrl").val();
    var formData = new FormData($("#addAppVersion")[0]);

    $.ajax({
        type: "POST",
        url: baseUrl + "/firmware/upgrade/wristband/app/add",
        data: formData,
        contentType: false,
        processData: false,
        dataType: 'json',
        error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            if (data.status === 0) {
                alert("添加成功！");
            } else if (data.status === 2) {
                alert("数据格式错误 !");
            } else if (data.status === 8) {
                alert("APP版本已经存在 !");
            } else if (data.status === 19) {
                alert("固件触发失败，请重试！");
            }
            window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
        }
    });
}

$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-wristband-li-node").addClass("active");
    $("#firmware-upgrade-wristband-test-list").addClass("active");
    var baseUrl = $("#baseUrl").val();
    var table = $("#wristbandTestList").DataTable({
        paging: true,
        searching: true,
        ordering: true,
        processing: true,
        aLengthMenu: [[10, 20, 40], ["10", "20", "40"]],
        iDisplayLength: 10,
        autoWidth: false,
        oLanguage: {
            sProcessing: "努力加载数据中...",
            sLengthMenu: "每页显示 _MENU_ 条记录",
            sZeroRecords: "抱歉，没有找到",
            sInfo: "从 _START_ 到 _END_ / 共 _TOTAL_ 条数据",
            sInfoEmpty: "没有数据",
            oPaginate: {
                sFirst: "首页",
                sPrevious: "前一页",
                sNext: "后一页",
                sLast: "尾页"
            }
        },
        ajax: {
            url: baseUrl + "/firmware/upgrade/list?environment=test",
        },
        columns: [
            {
                className: "details-control",
                orderable: false,
                data: null,
                defaultContent: ""
            },
            {data: "appPlatform"},
            {data: "appVersionCode"},
            {data: "firmwareType"},
            {data: "hardwareCode"},
            {data: "version"},
            {data: "versionCode"},
            {data: "enable"}
        ]
    });

    function format(d) {
        var result = null;
        $.ajax({
            type: "POST",
            url: $("#baseUrl").val() + "/firmware/upgrade/detail",
            dataType: "json",
            async: false,
            data: {
                "appPlatform": d.appPlatform,
                "appVersionCode": d.appVersionCode,
                "firmwareType": d.firmwareType,
                "hardwareCode": d.hardwareCode,
                "versionCode": d.versionCode,
                "environment": "test",
            }, error: function (req, status, err) {
                console.log('Failed reason: ' + err);
            }, success: function (data) {
                result = data;
            }
        });
        console.log(JSON.stringify(result));
        if ("不可用" === d.enable) {
            return '<table class="table"><tr><th width="25%">ID:</th><td width="25%">#id#</td><th width="25%">APP平台:</th><td width="25%">#appPlatform#</td></tr><tr><th>APP版本号:</th><td>#appVersionCode#<button style="margin-left: 5%" onclick="showAddAppVersion(this)"><i class="fa fa-plus"></i></button></td><th>固件类型:</th><td>#firmwareType#</td></tr><tr><th>硬件版本:</th><td>#hardwareCode#</td><th>环境:</th><td>#environment#</td></tr><tr><th>固件版本:</th><td>#version#</td><th>固件版本号:</th><td>#versionCode#</td></tr><tr><th>GNSS版本:</th><td>#gnssVersion#</td><th>是否强制升级:</th><td>#forceUpgrade#</td></tr><tr><th>是否为里程碑版本:</th><td>#fotaForceUpgrade#</td><th></th><td></td></tr><tr><th>固件说明:</th><td  colspan="3"><textarea rows="3" cols="110">#description#</textarea></td></tr><tr><th>下载链接:</th><td colspan="3"><a href="#url#"><textarea rows="1" cols="110">#url#</textarea></a></td></tr><tr><th>MD5:</th><td>#md5#</td><th>文件大小:</th><td>#size#</td></tr><tr><th>上传时间:</th><td>#createTime#</td><th>状态:</th><td>#enable#</td><tr><th>操作:</th><td><button style="background-color: #01ff70" onclick="activate(this)">上线</button></td><td><button style="background-color: #db8b0b" onclick="editWristband(this)">编辑</button></td><td><button style="background-color: slategray" onclick="deleteFirmware(this)">删除</button></td></tr></table>'
                .replace("#id#", result.id)
                .replace("#appPlatform#", result.appPlatform)
                .replace("#appVersionCode#", result.appVersionCode)
                .replace("#firmwareType#", result.firmwareType)
                .replace("#hardwareCode#", result.hardwareCode)
                .replace("#environment#", result.environment)
                .replace("#version#", result.version)
                .replace("#versionCode#", result.versionCode)
                .replace("#description#", result.description)
                .replace("#url#", result.url)
                .replace("#url#", result.url)
                .replace("#gnssVersion#", result.gnssVersion)
                .replace("#forceUpgrade#", result.forceUpgrade === true ? "是" : "否")
                .replace("#fotaForceUpgrade#", result.fotaForceUpgrade === true ? "是" : "否")
                .replace("#md5#", result.md5)
                .replace("#size#", result.size)
                .replace("#createTime#", result.createTime)
                .replace("#enable#", result.enable === 1 ? "可用" : "不可用");
        } else {
            return '<table class="table"><tr><th width="25%">ID:</th><td width="25%">#id#</td><th width="25%">APP平台:</th><td width="25%">#appPlatform#</td></tr><tr><th>APP版本号:</th><td>#appVersionCode#<button style="margin-left: 5%" onclick="showAddAppVersion(this)"><i class="fa fa-plus"></i></button></td><th>固件类型:</th><td>#firmwareType#</td></tr><tr><th>硬件版本:</th><td>#hardwareCode#</td><th>环境:</th><td>#environment#</td></tr><tr><th>固件版本:</th><td>#version#</td><th>固件版本号:</th><td>#versionCode#</td></tr><tr><th>GNSS版本:</th><td>#gnssVersion#</td><th>是否强制升级:</th><td>#forceUpgrade#</td></tr><tr><th>是否为里程碑版本:</th><td>#fotaForceUpgrade#</td><th></th><td></td></tr><tr><th>固件说明:</th><td  colspan="3"><textarea rows="3" cols="110">#description#</textarea></td></tr><tr><th>下载链接:</th><td colspan="3"><a href="#url#"><textarea rows="1" cols="110">#url#</textarea></a></td></tr><tr><th>MD5:</th><td>#md5#</td><th>文件大小:</th><td>#size#</td></tr><tr><th>上传时间:</th><td>#createTime#</td><th>状态:</th><td>#enable#</td><tr><th>操作:</th><td><button style="background-color: red" onclick="downgrade(this)">下线</button></td><td><button style="background-color: #db8b0b" onclick="editWristband(this)">编辑</button></td></tr></table>'
                .replace("#id#", result.id)
                .replace("#appPlatform#", result.appPlatform)
                .replace("#appVersionCode#", result.appVersionCode)
                .replace("#firmwareType#", result.firmwareType)
                .replace("#hardwareCode#", result.hardwareCode)
                .replace("#environment#", result.environment)
                .replace("#version#", result.version)
                .replace("#versionCode#", result.versionCode)
                .replace("#description#", result.description)
                .replace("#url#", result.url)
                .replace("#url#", result.url)
                .replace("#gnssVersion#", result.gnssVersion)
                .replace("#forceUpgrade#", result.forceUpgrade === true ? "是" : "否")
                .replace("#fotaForceUpgrade#", result.fotaForceUpgrade === true ? "是" : "否")
                .replace("#md5#", result.md5)
                .replace("#size#", result.size)
                .replace("#createTime#", result.createTime)
                .replace("#enable#", result.enable === 1 ? "可用" : "不可用");
        }

    }

    $("#wristbandTestList tbody").on("click", "td.details-control", function () {
        var tr = $(this).closest("tr");
        var row = table.row(tr);
        if (row.child.isShown()) {
            row.child.hide();
            tr.removeClass('shown');
        } else {
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });
});