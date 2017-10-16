function trigger(node) {
    var index = node.parentNode.parentNode.parentNode.childNodes[0].childNodes[1].innerText;
    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/firmware/trigger",
        dataType: "json",
        data: {
            "id": index
        }, error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            if(data.status === 0) {
                alert("操作成功！");
            } else if(data.status === 9) {
                alert("当前固件不存在");
            } else if(data.status === 11) {
                alert("当前固件不是可用状态");
            }
            window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
        }
    });
}

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
            if(data.status === 0) {
                alert("操作成功！");
            } else if(data.status === 11) {
                alert("当前固件不是可用状态");
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
            if(data.status === 0) {
                alert("操作成功！");
            } else if(data.status === 11) {
                alert("当前固件已经是可用状态");
            }
            window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
        }
    });
}

function editWristband(node) {
    $("#wristbandTestModalLabel").text("编辑");
    var id = node.parentNode.parentNode.parentNode.childNodes[0].childNodes[1].innerText;
    var appName = node.parentNode.parentNode.parentNode.childNodes[1].childNodes[1].innerText;
    var appPlatform = node.parentNode.parentNode.parentNode.childNodes[2].childNodes[1].innerText;
    var appVersionCode = node.parentNode.parentNode.parentNode.childNodes[3].childNodes[1].innerText;
    var firmwareType = node.parentNode.parentNode.parentNode.childNodes[4].childNodes[1].innerText;
    var hardwareVersion = node.parentNode.parentNode.parentNode.childNodes[5].childNodes[1].innerText;
    var environment = node.parentNode.parentNode.parentNode.childNodes[6].childNodes[1].innerText;
    var firmwareVersion = node.parentNode.parentNode.parentNode.childNodes[8].childNodes[1].innerText;
    var gnssVersion = node.parentNode.parentNode.parentNode.childNodes[9].childNodes[1].innerText;
    var description = node.parentNode.parentNode.parentNode.childNodes[10].childNodes[1].innerText;
    $("#id").val(id);
    $("#appName").val(appName);
    $("#appPlatform").val(appPlatform);
    $("#appVersionCode").val(appVersionCode);
    $("#firmwareType").val(firmwareType);
    $("#hardwareVersion").val(hardwareVersion);
    $("#environment").val(environment);
    $("#firmwareVersion").val(firmwareVersion);
    $("#gnssVersion").val(gnssVersion);
    $("#description").val(description);
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
            if(data.status === 0) {
                alert("操作成功！");
            } else if(data.status === 2) {
                alert("数据格式错误 !");
            } else if(data.status === 7) {
                alert("文件上传失败 !");
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
        searching: false,
        ordering: false,
        processing: true,
        aLengthMenu: [10],
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
            {data: "appName"},
            {data: "firmwareType"},
            {data: "hardwareCode"},
            {data: "version"},
            {data: "versionCode"},
            {data: "enable", "render": function(data){ return data === "1" ? "可用" : "不可用";}}
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
                "appName": d.appName,
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
        return '<table class="table" style="margin-left: 50px"><tr><td>ID:</td><td>#id#</td></tr><tr><td>APP名字:</td><td>#appName#</td></tr><tr><td>APP平台:</td><td>#appPlatform#</td></tr><tr><td>APP版本号:</td><td>#appVersionCode#</td></tr><tr><td>固件类型:</td><td>#firmwareType#</td></tr><tr><td>硬件版本:</td><td>#hardwareCode#</td></tr><tr><td>环境:</td><td>#environment#</td></tr><tr><td>固件版本:</td><td>#version#</td></tr><tr><td>固件版本号:</td><td>#versionCode#</td></tr><tr><td>GNSS版本:</td><td>#gnssVersion#</td></tr><tr><td>固件说明:</td><td>#description#</td></tr><tr><td>下载链接:</td><td><a href="#url#">#url#</a></td></tr><tr><td>MD5:</td><td>#md5#</td></tr><tr><td>文件大小:</td><td>#size#</td></tr><tr><td>上传时间:</td><td>#createTime#</td></tr><tr><td>状态:</td><td>#enable#</td><tr><td>触发:</td><td><button onclick="trigger(this)">触发</button></td></tr><tr><td>失效:</td><td><button onclick="downgrade(this)">失效</button></td></tr><tr><td>激活:</td><td><button onclick="activate(this)">激活</button></td></tr><td>编辑:</td><td><button onclick="editWristband(this)">编辑</button></td></tr></table>'
            .replace("#id#", result.id)
            .replace("#appName#", result.appName)
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
            .replace("#md5#", result.md5)
            .replace("#size#", result.size)
            .replace("#createTime#", result.createTime)
            .replace("#enable#", result.enable == 1 ? "可用" : "不可用");
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