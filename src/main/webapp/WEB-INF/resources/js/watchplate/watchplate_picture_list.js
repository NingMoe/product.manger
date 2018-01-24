var table =$("#pictureList").DataTable();
$(document).ready(function () {

        $('#environment').change(function () {
        const environment =$("#environment").val();
        const baseUrl = $("#baseUrl").val();
        if(table)
        {
            table.destroy();
        }
        table =   $("#pictureList").DataTable({
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
                dataType: "JSON",
                data: {environment:environment}
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
         //  console.log(table);
    });

    $('#pictureList tbody').on('click','tr',function(){
        if($(this).hasClass('selected')){
            $(this).removeClass('selected');
            $(this).css("background","");
        }else{
            $(this).toggleClass('selected').css("background","#ccc");
        }
    });

    $('button').click(function () {
        table.rows('.selected').remove().draw(false);
    });

});
function pictureDelete(){
    var datas=[];
    if(table.rows('.selected').data().length<=0){
        alert("请选择你要删除的行")
    }
    for(var i=0;i<table.rows('.selected').data().length;i++) {
        datas.push(table.rows('.selected').data()[i]);
        console.log(table.rows('.selected').data()[i]);
    }
    console.log($("#environment").val());
    console.log($("#baseUrl").val());
    $.ajax({
        type: "POST",
        url: $("#baseUrl").val() + "/watchplate/picture/list/delete",
        dataType: "json",
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify({
            "environment":$("#environment").val(),
            "data":datas
        })

    });
}




