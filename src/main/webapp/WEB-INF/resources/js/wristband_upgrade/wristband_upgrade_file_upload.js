$(document).ready(function() {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#firmware-upgrade-wristband-li-node").addClass("active");
    $("#firmware-upgrade-wristband-menu-node").addClass("active");
    $("#firmwareType").select2({
        placeholder: "选择固件类型",
        multiple: false
    });
    $("#environment").select2({
        placeholder: "选择固件应用环境",
        multiple: false
    });
    $("#submit").click(function() {

    });
});