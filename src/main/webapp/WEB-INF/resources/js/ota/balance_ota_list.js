$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#firmware-upgrade-wristband-li-node").addClass("active");
    $("#firmware-upgrade-wristband-menu-node").addClass("active");
    let baseUrl = $("#baseUrl").val();

    const table = $("#otaVersionList").DataTable({
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
        ajax: {
            type: "POST",
            dataType: "JSON",
            url: baseUrl + "/balance/ota/list/json",
            data: {
                "environment": "test"
            }
        },
        columns: [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {data: "softwareVersion"},
            {data: "testing"},
            {data: "enable"},
            {data: "createTime"}
        ]
    });

    const otaVersionListDiv = $(`#otaVersionList`).find("tbody");
    otaVersionListDiv.on("click", "td.details-control", function () {
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

function format(d) {
    return `<table class="table" style="margin-left: 50px">
            <tr>
                <td>ID:</td>
                <td>${d.id}</td>
            </tr>
            <tr>
                <td>固件版本:</td>
                <td>${d.softwareVersion}</td>
            </tr>
            <tr>
                <td>A类型文件下载链接:</td>
                <td><a href="${d.aVersionFileUrl}">${d.aVersionFileUrl}</a></td>
            </tr><tr>
                <td>A类型文件CRC:</td>
                <td>${d.aVersionCrc}</td>
            </tr>
            <tr>
                <td>B类型文件下载链接:</td>
                <td><a href="${d.bVersionFileUrl}">${d.bVersionFileUrl}</a></td>
            </tr>
            <tr>
                <td>B类型文件CRC:</td>
                <td>${d.bVersionCrc}</td>
            </tr>
            <tr>
                <td>版本类型:</td>
                <td>${d.testing}</td>
            </tr>
            <tr>
                <td>版本状态:</td>
                <td>${d.enable}</td>
            </tr>
            <tr>
                <td>上传时间:</td>
                <td>${d.createTime}</td>
            </tr>
        </table>`;
}