$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#group_menu_node").addClass("active");
    $("#group-manager-menu-node").addClass("active");
    $("#group-add").addClass("active");
    $("#submit").click(function () {
        var baseUrl = $("#baseUrl").val();
        var formData = new FormData($("#groupForm")[0]);
        $.ajax({
            type: "POST",
            url: baseUrl + "/group/add",
            data: formData,
            async: true,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.status === 0) {
                    alert("创建成功");
                    window.location.href = baseUrl + "/group/list";
                } else if (data.status === 2) {
                    alert("数据格式错误，请检查参数并重新上传！");
                } else if (data.status === 28) {
                    alert("该组已经存在，请检查参数！");
                }
            }
        });
    });
});