function upgradeFirmware(node) {
    if(!confirm("确认是否将该固件作为最新固件？")) {
        return;
    }

    var firmwareType = node.parentNode.parentNode.childNodes[1].innerText;
    var hardwareCode = node.parentNode.parentNode.childNodes[2].innerText;
    var environment = node.parentNode.parentNode.childNodes[3].innerText;
    var versionCode = node.parentNode.parentNode.childNodes[4].innerText;

    console.info("firmwareType = " + firmwareType);
    console.info("hardwareCode = " + hardwareCode);
    console.info("environment = " + environment);
    console.info("versionCode = " + versionCode);

    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/firmware/upgrade/modify/current/version",
        dataType: "json",
        data: {
            "firmwareType": firmwareType,
            "hardwareCode": hardwareCode,
            "environment": "test",
            "versionCode": versionCode
        }, error: function (req, status, err) {
            console.log('Failed reason: ' + err);
        }, success: function (data) {
            if(data.status === 0) {
                alert("操作成功！");
            } else {
                alert("操作失败！");
            }
            window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
        }
    });
}
function repeatTrigger(node) {
    var index = node.parentNode.parentNode.parentNode.childNodes[0].childNodes[1].innerText;
    var baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/firmware/repeat/trigger",
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
function deleteFirmware(node) {
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
            if(data.status === 0) {
                alert("操作成功！");
            } else if(data.status === 9) {
                alert("需要删除的固件不存在");
            } else if(data.status === 12) {
                alert("该固件当前正在使用");
            }
            window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
        }
    });
}
$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#firmware-upgrade-wristband-li-node").addClass("active");
    $("#firmware-upgrade-wristband-menu-node").addClass("active");
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
        drawCallback: function () {
            var nodes = $(".table_enable_upgrade");
            nodes.mouseover(function () {
                if(this.innerHTML !== "当前版本" && this.getElementsByTagName("i").length === 0) {
                    this.innerHTML = '<i class="fa fa-arrow-up" style="cursor: pointer" onclick="upgradeFirmware(this)"></i>';
                }
            });
            nodes.mouseleave(function () {
                if(this.innerHTML !== "当前版本" && this.getElementsByClassName("fa fa-arrow-up").length !== 0) {
                    this.innerHTML = "";
                }
            });
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
            {data: "firmwareType"},
            {data: "hardwareCode"},
            {data: "version"},
            {data: "versionCode"},
            {
                data: "enable",
                className: "table_enable_upgrade"
            }
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
        return '<table class="table"><tr><td>ID:</td><td>#id#</td></tr><tr><td>固件类型:</td><td>#firmwareType#</td></tr><tr><td>硬件版本:</td><td>#hardwareCode#</td></tr><tr><td>环境:</td><td>#environment#</td></tr><tr><td>固件版本:</td><td>#version#</td></tr><tr><td>固件版本号:</td><td>#versionCode#</td></tr><tr><td>固件说明:</td><td>#description#</td></tr><tr><td>下载链接:</td><td>#url#</td></tr><tr><td>上传时间:</td><td>#createTime#</td></tr><tr><td>是否可用:</td><td>#enable#</td><tr><td>重新触发:</td><td><button onclick="repeatTrigger(this)">重新触发</button></td></tr><tr><td>降级:</td><td><button onclick="downgrade(this)">降级</button></td></tr><tr><td>删除:</td><td><button onclick="deleteFirmware(this)">删除</button></td></tr></table>'
            .replace("#id#", result.id)
            .replace("#firmwareType#", result.firmwareType)
            .replace("#hardwareCode#", result.hardwareCode)
            .replace("#environment#", result.environment)
            .replace("#version#", result.version)
            .replace("#versionCode#", result.versionCode)
            .replace("#description#", result.description)
            .replace("#url#", result.url)
            .replace("#createTime#", result.createTime)
            .replace("#enable#", result.enable);
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