$(document).ready(function () {
    var imageHeightPx = $("#admin-head-image").height();
    var divHeightPx = $("#admin-div").height();
    var paddingTopPx = ((divHeightPx - imageHeightPx) / 2) + "px";
    $(".permissionHeadImage").css("padding-top", paddingTopPx);
});