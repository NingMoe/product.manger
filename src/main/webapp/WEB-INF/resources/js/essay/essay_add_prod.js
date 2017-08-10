$(document).ready(function () {
    $("#essay_manage_node").addClass("active");
    $("#essay_manage_menu_node").addClass("active");
    $("#essay_add_prod_node").addClass("active");
});

function uploadEssay() {
    const baseUrl = $("#baseUrl").val();
    let formData = new FormData($("#uploadEssay")[0]);
    var reg=/^([hH][tT]{2}[pP][sS]:\/\/)([^/:]+)(:\d*)/;
    var coverUrl = formData.get("coverUrl");
    var contentUrl = formData.get("contentUrl");
    if(!reg.test(coverUrl)){
        urlHint();
        $("#coverUrlLabel").append("<span style='color: red'><small>请输入正确格式！</small></span>");
    }else if (!reg.test(contentUrl)){
        urlHint();
        $("#contentUrlLabel").append("<span style='color: red'><small>请输入正确格式！</small></span>");
    }else {
        urlHint();
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
                } else if (status == 18) {
                    alert('该ID已经被其他人使用，请更换 !');
                } else {
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
}
function urlHint() {
    $("#contentUrlLabel").empty();
    $("#contentUrlLabel").append("<font style='color: red'>*</font>正文URL</label>");
    $("#coverUrlLabel").empty();
    $("#coverUrlLabel").append("<font style='color: red'>*</font>封面URL</label>");
}