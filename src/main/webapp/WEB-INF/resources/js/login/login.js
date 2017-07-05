$(document).ready(function () {
    var cookieData = $.cookie('accountInfo');
    if (cookieData !== undefined) {
        var data = $.parseJSON(cookieData);
        $("#username").val(data.phoneNumber);
        $("#password").val(data.password);
    }
});
function login() {
    var username = $("#username").val();
    if (username.length < 1) {
        $("#username").focus();
        return;
    }
    var password = $("#password").val();
    if (password.length < 1) {
        $("#password").focus();
        return;
    }
    try {
        window.btoa(password);
    } catch (error) {
        $("#password").val("");
        return;
    }
    loginRequest(username, password);
}
function loginRequest(username, password) {
    var jsonData = {};
    jsonData.phoneNumber = username;
    jsonData.password = password;
    var jsonDataString = JSON.stringify(jsonData);
    var baseUrl = $("#baseUrl").val();
    var url = baseUrl + "/login/check";
    var successUrl = baseUrl + "/homepage";
    $.ajax({
        type: "POST",
        url: url,
        contentType: "application/json",
        dataType: "json",
        data: jsonDataString,
        success: function (data) {
            if (data.status === 0) {
                $.cookie('accountInfo', jsonDataString, {expires: 7});
                window.location.href = successUrl;
            } else {
                $("#password").val("");
            }
        }
    });
}
$('#password').on('keypress', function (event) {
    if (event.keyCode === 13) {
        var username = $("#username").val();
        var password = $("#password").val();
        if (username.length > 0 && password.length > 0) {
            loginRequest(username, password);
        }
    }
});