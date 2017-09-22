function uploadFileToServer() {
    var baseUrl = $("#baseUrl").val();
    var url = baseUrl + "/picture/upload/file";
    var formData = new FormData($("#uploadPictureForm")[0]);

    $.ajax({
        type: "POST",
        url: url,
        data: formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if(data.status !== 0) {
                alert("上传图片失败请重试！");
                return;
            }

        }
    });

}
$(document).ready(function () {
    $("#pictureDocumentAdd").click(function () {
        var rowStr=  "<tr>" +
            "<td><input type='number' name='picId'></td> " +
            "<td><input type='text' name='picChiName'></td> " +
            "<td><input type='text' name='picEngName'></td>" +
            "<td><input type='file' name='file'></td>" +
            "<td><input type='button' id='btnDelete' value='删除' onclick='deleteRow(this)' class='btn btn-primary'></td>" +
            "</tr>";
        $("#pictureList tbody").append(rowStr);
    });

});
function isNotEmpty(str) {
    if (!str) {
        return false;
    }
    if (str.length == 0 || str.trim() == '') {
        return false;
    }
    return true;

}
function deleteRow(obj)
{
    $(obj).parent("td").parent("tr").remove();
}
function uploadPicture()
{
    $("input[type!='file']").css("border","1px solid #ddd");
    $("input[type='file']").css("border","0");
    var baseUrl = $("#baseUrl").val();
    var url = baseUrl + "/picture/upload/file";

    var formData = new FormData($("#uploadPictureForm")[0]);
    var paramsSuccess=true;
    for (var key of formData.keys()) {
        var values = formData.getAll(key);
        var rows=0;
        for (var value of values) {
            if (typeof value == 'string') {
                if (!isNotEmpty(value)) {
                    paramsSuccess = false;

                    $("input[name='"+key+"']:eq("+rows+")").css("border","1px solid red");
                }
            } else if(!isNotEmpty(value.name)) {
                paramsSuccess = false;
                $("input[name='"+key+"']:eq("+rows+")").css("border","1px solid red");
            }
            rows++;
        }
    }




    if(!paramsSuccess)
    {
        alert("请填写信息完整");
        return;
    }
    uploadFileToServer();
    document.getElementById("uploadPictureForm").reset();
    $("#pictureList tbody tr").remove();
}