function uploadFileToServer() {
    var baseUrl = $("#baseUrl").val();
    var url = baseUrl + "/hermes/upload/file";
    var formData = new FormData($("#file-form")[0]);
    var startTimestamp = (new Date()).valueOf();
    $.ajax({
        type: "POST",
        url: url,
        data: formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            var endTimestamp = (new Date()).valueOf();
            var costTime = endTimestamp - startTimestamp;
            if(data.status !== 0) {
                alert("上传失败请重试！");
                return;
            }
            $(".upload-file-result-row").removeClass("hidden");
            $("#tableFileName").html(data.data.originalFileName);
            $("#tableFileSize").html(data.data.fileSize);
            $("#tableCostTime").html(costTime);
            $("#tableMd5").html(data.data.md5);
            $("#tableSha256").html(data.data.sha256);
            $("#tableHttpsFile").html(data.data.fileHttpsUrl);
            $("#tableHttpsImage").html(data.data.imageHttpsUrl);
            $("#tableHttpFile").html(data.data.imageHttpUrl);
            $("#tableHttpImage").html(data.data.imageHttpUrl);
            $("#tableInnerFile").html(data.data.fileHttpInnerUrl);
        }
    });
}
$(document).ready(function () {
    $("#file-upload-submit").click(function () {
        uploadFileToServer();
    });
});