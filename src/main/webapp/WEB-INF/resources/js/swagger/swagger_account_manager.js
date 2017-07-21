/**
 * 初始化组件
 */
$(document).ready(function () {
    const baseUrl = $("#baseUrl").val();
    const table = $("#swaggerProjectList").DataTable({
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
            type: "POST",
            dataType: "JSON",
            url: baseUrl + "/swagger/project/list",
            data: {
                "environment": "prod"
            }
        },
        columns: [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {data: "projectName"},
            {data: "projectAddress"},
            {data: "createTime"}
        ]
    });

    const swaggerProjectList = $(`#swaggerProjectList`).find("tbody");

    /**
     * 下滑展示详情
     */
    swaggerProjectList.on("click", "td.details-control", function () {
        let tr = $(this).closest('tr');
        let row = table.row(tr);
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

/**
 * 格式化:``中的方法传值，必须在参数外围加上''
 */
function format(d) {
    if (d.hasOwnProperty("swaggerAddressProd")) {
        return `<table class="table" style="margin-left: 50px">
            <tr>
                <td>ID:</td>
                <td>${d.id}</td>
            </tr>
            <tr>
                <td>项目名称:</td>
                <td>${d.projectName}</td>
            </tr>
            <tr>
                <td>项目地址:</td>
                <td><a href="${d.projectAddress}">${d.projectAddress}</a></td>
            </tr>
            <tr>
                <td>生产环境地址:</td>
                <td><a href="${d.swaggerAddressProd}">${d.swaggerAddressProd}</a></td>
            </tr>
            <tr>
                <td>生产环境用户名:</td>
                <td>${d.swaggerUsernameProd}</td>
            </tr>
            <tr>
                <td>生产环境密码:</td>
                <td>${d.swaggerPasswordProd}</td>
            </tr>
            <tr>
                <td>测试环境地址:</td>
                <td><a href="${d.swaggerAddressTest}">${d.swaggerAddressTest}</a></td>
            </tr>
            <tr>
                <td>测试环境用户名:</td>
                <td>${d.swaggerUsernameTest}</td>
            </tr>
            <tr>
                <td>测试环境密码:</td>
                <td>${d.swaggerPasswordTest}</td>
            </tr>
            <tr>
                <td>项目描述:</td>
                <td>${d.description}</td>
            </tr>
            <tr>
                <td>创建时间:</td>
                <td>${d.createTime}</td>
            </tr>
            <tr>
                <td>更新时间:</td>
                <td>${d.updateTime}</td>
            </tr>
            <tr>
                <td>删除:</td>
                <td>
                    <button onclick=deleteItem('${d.projectName}')>删除</button>
                </td>
            </tr>
            <tr>
                <td>编辑:</td>
                <td>
                    <button onclick="skip('${d.projectName}')">编辑</button>
                </td>
            </tr>
        </table>`;
    } else {
        return `<table class="table" style="margin-left: 50px">
            <tr>
                <td>ID:</td>
                <td>${d.id}</td>
            </tr>
            <tr>
                <td>项目名称:</td>
                <td>${d.projectName}</td>
            </tr>
            <tr>
                <td>项目地址:</td>
                <td><a href="${d.projectAddress}">${d.projectAddress}</a></td>
            </tr><tr>
                <td>测试环境地址:</td>
                <td><a href="${d.swaggerAddressTest}">${d.swaggerAddressTest}</a></td>
            </tr>
            <tr>
                <td>测试环境用户名:</td>
                <td>${d.swaggerUsernameTest}</td>
            </tr>
            <tr>
                <td>测试环境密码:</td>
                <td>${d.swaggerPasswordTest}</td>
            </tr>
            <tr>
                <td>项目描述:</td>
                <td>${d.description}</td>
            </tr>
            <tr>
                <td>创建时间:</td>
                <td>${d.createTime}</td>
            </tr>
            <tr>
                <td>更新时间:</td>
                <td>${d.updateTime}</td>
            </tr>
        </table>`;
    }
}

/**
 * 删除
 * @param projectName 项目名称
 */
function deleteItem(projectName) {
    const baseUrl = $("#baseUrl").val();
    if (confirm("确定要删除该地址？")) {
        let result = deleteProject(projectName);
        if ('success' === result) {
            window.location.href = baseUrl + "/swagger/account/manager";
        } else {
            alert("Delete fail.");
        }
    }
}

/**
 * 编辑跳转
 * @param projectName 项目名
 */
function skip(projectName) {
    const baseUrl = $("#baseUrl").val();
    window.location.href = baseUrl + "/swagger/project/edit" + "?projectName=" + projectName;
}

/**
 * 网络请求删除一条记录
 * @param projectName 项目名
 * @returns {string} 结果
 */
function deleteProject(projectName) {
    const baseUrl = $("#baseUrl").val();
    let result = 'fail:' + 'please try again.';
    $.ajax({
        type: "POST",
        url: baseUrl + "/swagger/project/delete",
        dataType: "json",
        async: false,
        data: {
            'projectName': projectName
        },
        error: function (req, status, err) {
            result = 'Failed reason: ' + err;
        }, success: function (data) {
            const status = data.status;
            if (status === 0) {
                result = 'success';
            } else {
                result = 'fail:' + 'please try again.';
            }
        }
    });
    return result;
}