$(function () {
    var i = 5;
    remainTime(i);
});

function remainTime(i) {
    if (i === 0) {
        window.location.href = $("#baseUrl").val() + "/login";
    }
    $("#countDownTime").html("(" +i-- + "s)");
    setTimeout("remainTime(" + i + ")", 1000);
}