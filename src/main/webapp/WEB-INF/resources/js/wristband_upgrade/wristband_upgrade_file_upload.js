$(document).ready(function() {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#firmware-upgrade-wristband-li-node").addClass("active");
    $("#firmware-upgrade-wristband-menu-node").addClass("active");
    $("#firmwareType").select2({
        placeholder: "选择固件类型",
        multiple: false
    });
    $("#hardwareVersion").select2({
        placeholder: "选择固件对应的硬件平台",
        multiple: false
    });
    $("#environment").select2({
        placeholder: "选择固件应用环境",
        multiple: false
    });
    $("#submit").click(function() {
        var baseUrl  =$("#baseUrl").val();
        var formData = new FormData($("#form")[0]);
        $.ajax({
            type   : "POST",
            url    : baseUrl + "/firmware/upgrade/wristband/file/upload",
            data: formData,
            async: true,
            cache: false,
            contentType: false,
            processData: false,
            success: function(data) {
                if(data.status === 0) {
                    alert("上传成功");
                    if($("#environment").val() === "test") {
                        window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
                    } else {
                        window.location.href = baseUrl + "/wristband/upgrade/page/prod/list";
                    }
                } else if(data.status === 2) {
                    alert("数据格式错误，请检查参数并重新上传！");
                } else if(data.status === 7) {
                    alert("文件上传失败，请重新上传！");
                } else {
                    alert("版本已经存在，请重新上传！");
                }
            }
        });
    });
});