$(document).ready(function () {
    const baseUrl = $("#baseUrl").val();
    $("#pictureList");
    const table =   $("#pictureList").DataTable({
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
            url: baseUrl + "/watchplate/picture/list/page",
            type: "POST",
            dataType: "JSON"
        },
        columns: [
            {data: "picVersion"},
            {data: "picId"},
            {data: "picChiName"},
            {data: "picEngName"},
            {data: "picResolution"},
            {data: "createTime"}
        ]
    });
});

