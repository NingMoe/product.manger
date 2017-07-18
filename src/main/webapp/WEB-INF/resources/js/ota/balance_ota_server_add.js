$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#balance_ota_menu_node").addClass("active");
    $("#balance_ota_server_add_node").addClass("active");
});

/**
 * 初始化组件
 */
function addNewServer() {
    const baseUrl = $("#baseUrl").val();
    let formData = new FormData($("#addNewAddress")[0]);
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/server/address/insert",
        data: formData,
        contentType: false,
        processData: false,
        dataType: 'json',
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let status = data.status;
            if (status !== 0) {
                if (status === 5) {
                    alert('This address already existed !');
                }else if (status===2){
                    alert('Data format error !');
                }else {
                    alert('Error !');
                }
            } else {
                let result = data.data;
                console.info(result);
                if (data.status === 0 && result !== null) {
                    alert('success .');
                    window.location.reload();
                } else {
                    alert('fail !');
                }
            }
        }
    })
}