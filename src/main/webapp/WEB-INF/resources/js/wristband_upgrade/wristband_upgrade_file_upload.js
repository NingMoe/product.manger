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
        console.log($("#firmwareType").select2("val"));
        console.log($("#hardwareVersion").select2("val"));
        console.log($("#firmwareVersion").val());
        console.log($("#environment").select2("val"));
    });
});