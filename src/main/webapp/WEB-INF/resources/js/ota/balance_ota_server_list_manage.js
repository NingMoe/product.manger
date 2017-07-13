$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#balance_ota_menu_node").addClass("active");
    $("#balance_ota_list_test_node").addClass("active");
    const baseUrl = $("#baseUrl").val();
    const divSelector = $("#otaServerList");
    const table = divSelector.DataTable({
        paging: true,
        searching: true,
        ordering: true,
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
            const nodes = $(".operator_btn");
            nodes.mouseover(function () {
                if (this.innerHTML !== "操    作" && this.getElementsByTagName("i").length === 0) {
                    this.innerHTML = `<i class="fa fa-remove" style="cursor: pointer"></i>`;
                }
            });
            nodes.mouseleave(function () {
                if (this.innerHTML !== "操    作" && this.getElementsByClassName("fa fa-remove").length !== 0) {
                    this.innerHTML = "";
                }
            });
        },
        ajax: {
            type: "POST",
            dataType: "JSON",
            url: baseUrl + "/balance/server/address/list/obtain"
        },
        columns: [
            {data: "id"},
            {data: "ip"},
            {data: "port"},
            {data: "createTime"},
            {
                "orderable": false,
                "data": null,
                "defaultContent": '',
                "className": "operator_btn"
            }
        ]
    });

    /**
     * 删除行数据
     */
    divSelector.find('tbody').on('click', 'i', function () {
        let ip = this.parentNode.parentNode.children[1].innerText;
        let port = this.parentNode.parentNode.children[2].innerText;
        if (confirm("确定要删除该地址？")) {
            let result = deleteAddress(ip, port);
            if ('success' === result) {
                table.row($(this).parents('tr')).remove().draw();
            } else {
                alert("Delete fail.");
            }
        }
    });
});

/**
 * 网络请求删除地址
 * @param ip ip地址
 * @param port 端口号
 */
function deleteAddress(ip, port) {
    const baseUrl = $("#baseUrl").val();
    let result = 'fail:' + 'please try again.';
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/server/address/delete",
        dataType: "json",
        async: false,
        data: {
            'ip': ip,
            'port': port
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
