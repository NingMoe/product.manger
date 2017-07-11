$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#firmware-upgrade-wristband-li-node").addClass("active");
    $("#firmware-upgrade-wristband-menu-node").addClass("active");
    var baseUrl = $("#baseUrl").val();
    $("#wristbandTestList").DataTable({
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
            url: baseUrl + "/firmware/upgrade/wristband/list?environment=test"
        },
        // columns: [
        //     {
        //         className: "details-control",
        //         orderable: false,
        //         data: null,
        //         defaultContent: ""
        //     },
        //     {
        //         data: "name"
        //     },
        //     {
        //         data: "position"
        //     },
        //     {
        //         data: "office"
        //     },
        //     {
        //         data: "salary"
        //     }
        // ],
    });
    // Add event listener for opening and closing details
    $("#wristbandTestList tbody").on("click", "td.details-control", function () {
        var tr = $(this).closest("tr");
        var row = table.row(tr);
        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });
});