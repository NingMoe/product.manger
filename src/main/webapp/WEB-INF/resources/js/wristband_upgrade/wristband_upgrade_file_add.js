$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-wristband-li-node").addClass("active");
    $("#firmware-upgrade-wristband-add").addClass("active");
    $("#appPlatform").select2({
        placeholder: "选择APP平台",
        multiple: true
    });
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
    $.ajax({
        type: "POST",
        url: $("#baseUrl").val() + "/group/list",
        dataType: "json",
        contentType: 'application/json;charset=UTF-8',
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let result = data.data;
            $("#groupSelected").find("option:not(:first)").remove();
            terminalInfo = result;
            for (let i=0;i< result.length;i++) {
                $("#groupSelected").append("<option value=" + result[i].id + ">" + result[i].name + "</option>");
            }
        }
    });
    /*$("#groupSelected").click(function(){
        const baseUrl = $("#baseUrl").val();
        $.ajax({
            type: "POST",
            url: $("#baseUrl").val() + "/group/list",
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            error: function (req, status, err) {
                alert('Failed reason: ' + err);
            }, success: function (data) {
                let result = data.data;
                $("#groupSelected").find("option:not(:selected)").remove();
                //$("#groupSelected").empty();
                terminalInfo = result;
                for (let i=0;i< result.length && $("#groupSelected").find("option:selected").val()!==result[i].id;i++) {
                    $("#groupSelected").append("<option value=" + result[i].id + ">" + result[i].name + "</option>");
                }
            }
        });
    });*/
    $("#appPlatform").change(function () {
        var platForms = $("#appPlatform").val();
        if (platForms !== null && 1 === platForms.length && "android" === platForms[0] && "none" === $("#appIos").css("display") && "none" === $("#appAndroid").css("display")) {
            $("#appAndroid").css("display", 'block');
        } else if (platForms !== null && 1 === platForms.length && "ios" === platForms[0] && "none" === $("#appIos").css("display") && "none" === $("#appAndroid").css("display")) {
            $("#appIos").css("display", 'block');
        } else if (platForms !== null && 2 === platForms.length) {
            $("#appAndroid").css("display", 'block');
            $("#appIos").css("display", 'block');
        } else if (platForms !== null && 1 === platForms.length && "android" === platForms[0] && "block" === $("#appIos").css("display")) {
            $("#appIos").css("display", 'none');
        } else if (platForms !== null && 1 === platForms.length && "ios" === platForms[0] && "block" === $("#appAndroid").css("display")) {
            $("#appAndroid").css("display", 'none');
        } else if (platForms === null) {
            $("#appAndroid").css("display", 'none');
            $("#appIos").css("display", 'none');
        }
    });
    $("#submit").click(function () {
        var baseUrl = $("#baseUrl").val();
        var formData = new FormData($("#form")[0]);
        $.ajax({
            type: "POST",
            url: baseUrl + "/firmware/upgrade/wristband/file/add",
            data: formData,
            async: true,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.status === 0) {
                    alert("上传成功");
                    if ($("#environment").val() === "test") {
                        window.location.href = baseUrl + "/wristband/upgrade/page/test/list";
                    } else {
                        window.location.href = baseUrl + "/wristband/upgrade/page/prod/list";
                    }
                } else if (data.status === 2) {
                    alert("数据格式错误，请检查参数并重新上传！");
                } else if (data.status === 7) {
                    alert("文件上传失败，请重新上传！");
                } else if (data.status === 8){
                    alert("固件版本已经存在，请更改后重新上传！");
                } else if (data.status === 19){
                    alert("固件触发失败，请再试一次！");
                }
            }
        });
    });
});