$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#balance_blu_menu_node").addClass("active");
    $("#balance_blu_upload_node").addClass("active");
});

/**
 * 上传文件
 */
function uploadFile() {
    const baseUrl = $("#baseUrl").val();
    let formData = new FormData($("#uploadFile")[0]);
    formData.append("firmwareType", "blu");
    formData.append("production", "s9");
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/ota/upload",
        data: formData,
        contentType: false,
        processData: false,
        dataType: 'json',
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let status = data.status;
            if (status !== 0) {
                alert('Error !');
            } else {
                let result = data.data;
                console.info(result);
                if (data.status === 0 && result !== null) {
                    alert('success ');
                    window.location.reload();
                } else {
                    alert('fail !');
                }
            }
        }
    })
}