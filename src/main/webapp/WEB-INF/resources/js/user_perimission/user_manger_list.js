$(document).ready(function () {
    $("#user-manger-node-1").addClass("active");
    $("#user-manger-node-2").addClass("active");
    $("#user-manger-node-3").addClass("active");
    $("#user-manger-node-li-1").addClass("active");
    var baseUrl = $("#baseUrl").val();
    var table = $("#userList").DataTable({
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
            url: baseUrl + "/user/manger/list",
        },
        columns: [
            {
                className: "details-control",
                orderable: false,
                data: null,
                defaultContent: ""
            },
            {data: "username"},
            {data: "phoneNumber"},
            {data: "email"},
            {data: "role"},
            {data: "createTime"}
        ]
    });

    function format(d) {
        var result = null;
        $.ajax({
            type: "POST",
            url: $("#baseUrl").val() + "/user/manger/detail",
            dataType: "json",
            async: false,
            data: {
                "phoneNumber": d.phoneNumber
            }, error: function (req, status, err) {
                console.log('Failed reason: ' + err);
            }, success: function (data) {
                result = data;
            }
        });
        console.log(JSON.stringify(result));
        return '<table class="table" style="margin-left: 50px"><tr><td>ID:</td><td>#id#</td></tr><tr><td>用户名:</td><td>#username#</td></tr><tr><td>手机号:</td><td>#phoneNumber#</td></tr><tr><td>邮箱:</td><td>#email#</td></tr><tr><td>性别:</td><td>#sex#</td></tr><tr><td>系统角色:</td><td>#role#</td></tr><tr><td>头像:</td><td><img src="#headPicture#" style="height: 50px;"/></td></tr><tr><td>创建时间:</td><td>#createTime#</td></tr></table>'
            .replace("#id#", result.data.id)
            .replace("#username#", result.data.username)
            .replace("#phoneNumber#", result.data.phoneNumber)
            .replace("#email#", result.data.email)
            .replace("#sex#", result.data.sex === 1 ? "男" : "女")
            .replace("#role#", result.data.role)
            .replace("#headPicture#", result.data.headPicture)
            .replace("#createTime#", result.data.createTime);
    }

    $("#userList tbody").on("click", "td.details-control", function () {
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