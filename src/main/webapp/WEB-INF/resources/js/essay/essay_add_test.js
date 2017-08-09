$(document).ready(function () {
    $("#essay_manage_node").addClass("active");
    $("#essay_manage_menu_node").addClass("active");
    $("#essay_add_test_node").addClass("active");
});

function uploadEssay() {
    const baseUrl = $("#baseUrl").val();
    let formData = new FormData($("#uploadEssay")[0]);
    let result = 'fail:' + 'please try again.';
    $.ajax({
        type: "POST",
        url: baseUrl + "/essay/upload",
        data: formData,
        contentType: false,
        processData: false,
        dataType: 'json',
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            let status = data.status;
            if (status == 2) {
                alert('数据格式错误 !');
            } else if(status == 11){
                alert('该ID已经被其他人使用，请更换 !');
            }else {
                let result = data.data;
                console.info(result);
                if (data.status === 0 && result !== null) {
                    result = 'success';
                    window.location.reload();
                } else {
                    result = 'fail:' + 'please try again.';
                }
            }
        }
    })
    return result;
}