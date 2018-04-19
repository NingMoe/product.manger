function addUser(node) {
    $("#groupUserModalLabel").text("添加");
    let index = node.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.previousElementSibling.childNodes[1].innerText;
    let type = node.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.previousElementSibling.childNodes[3].innerText;
    $("#groupId").val(index);
    $("#groupType").val(type);
    $('#userModal').modal();
}

function saveUser() {
    var baseUrl = $("#baseUrl").val();
    var formData = new FormData($("#addUser")[0]);
    $.ajax({
        type: "POST",
        url: baseUrl + "/group/user/add",
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
            } else if (data.status === 17) {
                alert("没有该用户 !");
            } else if (data.status === 29) {
                alert("该组内已经存在该用户 !");
            }
            window.location.href = baseUrl + "/group/list";
        }
    });
}

function editGroup(node) {
    $("#groupModalLabel").text("编辑");
    let id = node.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.previousElementSibling.childNodes[1].innerText;
    let name = node.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.previousElementSibling.childNodes[2].innerText;
    let type = node.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.previousElementSibling.childNodes[3].innerText;
    let description = node.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.previousElementSibling.childNodes[5].innerText;
    $("#id").val(id);
    $("#name").val(name);
    $("#type").val(type);
    $("#description").val(description);
    $('#groupModal').modal();
}

function updateGroup() {
    $("#type").removeAttr("disabled");
    let baseUrl = $("#baseUrl").val();
    let formData = new FormData($("#updateGroup")[0]);
    $.ajax({
        type: "POST",
        url: baseUrl + "/group/update",
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
            }
            window.location.href = baseUrl + "/group/list";
        }
    });
}

function deleteGroup(node) {
    if (confirm("若执行此操作，该灰度组将被删除，同时组内所有用户与该灰度组的绑定关系也将被全部清除，请确定您的操作？")) {
        var index = node.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.previousElementSibling.childNodes[1].innerText;
        var baseUrl = $("#baseUrl").val();
        $.ajax({
            type: "POST",
            url: baseUrl + "/group/delete",
            dataType: "json",
            data: {
                "id": index
            }, error: function (req, status, err) {
                console.log('Failed reason: ' + err);
            }, success: function (data) {
                if (data.status === 0) {
                    alert("删除成功！");
                }
                window.location.href = baseUrl + "/group/list";
            }
        });
    }
}

function deleteGroupUser(node) {
    if (confirm("确定删除？")) {
        let groupId = node.parentNode.parentNode.childNodes[1].innerText;
        let userId = node.parentNode.parentNode.childNodes[2].innerText;
        let baseUrl = $("#baseUrl").val();
        $.ajax({
            type: "POST",
            url: baseUrl + "/group/user/delete",
            dataType: "json",
            data: {
                "groupId": groupId,
                "userId": userId
            }, error: function (req, status, err) {
                console.log('Failed reason: ' + err);
            }, success: function (data) {
                if (data.status === 0) {
                    alert("删除成功！");
                }
                window.location.href = baseUrl + "/group/list";
            }
        });
    }
}

function GroupUserList(node) {
    $("#groupUserModalLabel").text("用户列表");
    let baseUrl = $("#baseUrl").val();
    let id = node.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.previousElementSibling.childNodes[1].innerText;
    let table = $("#groupUserList").DataTable({
        paging: true,
        searching: true,
        ordering: true,
        processing: true,
        aLengthMenu: [[10, 20, 40], ["10", "20", "40"]],
        iDisplayLength: 10,
        autoWidth: false,
        destroy:true,
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
            url: baseUrl + "/group/user/list?groupId="+id,
        },
        columns: [
            {
                "render": function (data) {
                    return data = '<button class="btn btn-danger btn-sm" onclick="deleteGroupUser(this)" data-id=' + data + '><i class="fa fa-trash-o"></i>Delete</button>';

                }
            },
            {data: "groupId"},
            {data: "userId"},
            {data: "phoneNumber"},
            {data: "description"},
            {data: "createTime"}
        ]
    });
    $('#groupUserModal').modal();
}

$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#group_menu_node").addClass("active");
    $("#group-manager-menu-node").addClass("active");
    $("#group-list").addClass("active");
    var baseUrl = $("#baseUrl").val();
    var table = $("#groupList").DataTable({
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
            url: baseUrl + "/group/list",
        },
        columns: [
            {
                className: "details-control",
                orderable: false,
                data: null,
                defaultContent: ""
            },
            {data: "id"},
            {data: "name"},
            {data: "type"},
            {data: "memberNumber"},
            {data: "description"},
            {data: "createTime"},
            {data: "updateTime"}
        ]
    });

    function format(d) {
        return '<table class="table"><tr><td style="width: 15%"></td><td><button class="btn btn-success" onclick="addUser(this)">添加用户</button></td><td><button class="btn btn-warning" onclick="editGroup(this)">编辑</button></td><td><button class="btn btn-danger" onclick="deleteGroup(this)">删除</button></td><td><button class="btn btn-info" onclick="GroupUserList(this)">详情</button></td></tr></table>';
    };

    $("#groupList tbody").on("click", "td.details-control", function () {
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