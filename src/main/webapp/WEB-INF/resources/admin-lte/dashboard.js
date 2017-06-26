$(function () {
    "use strict";
    $(".textarea").wysihtml5();
    $(".sign-out").bind("click", function () {
        var baseUrl = $("#baseUrl").val();
        var successUrl = baseUrl + "/login";
        var url =baseUrl + "/logout";
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json",
            dataType: "json",
            success: function (data) {
                if (data.status === 0) {
                    window.location.href = successUrl;
                }
            }
        });
    });
    $(".setting").bind("click", function () {
        alert("setting");
    });
});
