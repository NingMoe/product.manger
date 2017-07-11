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
        ajax: {
            url: baseUrl + "/firmware/upgrade/list?environment=test"
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
                "firmwareType": d.firmwareType,
                "hardwareCode": d.hardwareCode,
                "versionCode": d.versionCode,
                "environment": "test",
            }, error: function (req, status, err) {
                alert('Failed reason: ' + err);
            }, success: function (data) {
                result = data;
            }
        });
        console.log(JSON.stringify(result));
        return '<table class="table"><tr><td>ID:</td><td>#id#</td></tr><tr><td>固件类型:</td><td>#firmwareType#</td></tr><tr><td>硬件版本:</td><td>#hardwareCode#</td></tr><tr><td>环境:</td><td>#environment#</td></tr><tr><td>固件版本:</td><td>#version#</td></tr><tr><td>固件版本号:</td><td>#versionCode#</td></tr><tr><td>固件说明:</td><td>#description#</td></tr><tr><td>下载链接:</td><td>#url#</td></tr><tr><td>上传时间:</td><td>#createTime#</td></tr><tr><td>是否可用:</td><td>#enable#</td></tr></table>'
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
        }
        else {
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });
});