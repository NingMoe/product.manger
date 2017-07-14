$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#balance_ota_menu_node").addClass("active");
    $("#balance_ota_mac_node").addClass("active");

    const baseUrl = $("#baseUrl").val();

    $("#submit").click(function () {
        let environment = $("#environment").val() === '生产环境' ? 'prod' : 'test';
        $.ajax({
            type: "POST",
            url: baseUrl + "/balance/ota/test/mac",
            dataType: "json",
            data: {
                "macList": $("#macList").val(),
                "environment": environment
            },
            error: function (req, status, err) {
                console.log('Failed reason: ' + err);
            }, success: function (data) {
                if (data.status === 0) {
                    alert("Success !");
                    window.location.reload();
                } else {
                    alert("Fail !");
                }
            }
        });
    });
});