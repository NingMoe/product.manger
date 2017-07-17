$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#balance_mcu_menu_node").addClass("active");
    $("#balance_mcu_mac_node").addClass("active");

    const baseUrl = $("#baseUrl").val();
    let count = 0;

    /**
     * textarea 输入监听
     */
    $("textarea").on("input propertychange", function () {
        let text = $('#macList').val().trim().replace(/\s/g, "");
        let len = text.length - count;
        if (len === 0) {
            count = 0;
        }
        if (text % 18 !== getSplitCount(text)) {
            text = format(text);
            document.getElementById('macList').value = text;
        } else if (len % 17 === 0 && len !== 0) {
            count++;
            text = text + '、';
            document.getElementById('macList').value = text;
        }
    });

    /**
     * 处理复制情况
     * @param text 需要处理的文本
     * @returns {string|*} 处理好的文本
     */
    function format(text) {
        let macList = [];
        text = text.replace(/、/g, "");
        let splitLen = text.length / 17;
        for (let i = 0; i < splitLen; i++) {
            i < splitLen - 1 ? macList[i] = text.substring(17 * i, 17 * (i + 1)) + "、" : macList[i] = text.substring(17 * i, 17 * (i + 1));
        }
        text = macList.join("");
        count = macList.length;
        return text;
    }

    /**
     * 获取、出现的次数
     * @param text 文本
     * @returns {number} 次数
     */
    function getSplitCount(text) {
        let splitCount = 0;
        for (let i = 0; i < text.length; i++) {
            if ('、' === text[i]) {
                splitCount++;
            }
        }
        return splitCount;
    }

    /**
     * 处理按钮事件
     */
    $("#submit").click(function () {
        let environment = $("#environment").val() === '生产环境' ? 'prod' : 'test';
        $.ajax({
            type: "POST",
            url: baseUrl + "/balance/test/mac",
            dataType: "json",
            data: {
                "macList": $("#macList").val(),
                "environment": environment,
                "macType":"mcu"
            },
            error: function (req, status, err) {
                console.log('Failed reason: ' + err);
            }, success: function (data) {
                console.info(data);
                if (data.status === 0) {
                    alert("Success !");
                    window.location.reload();
                } else {
                    alert("Fail !");
                }
            }
        });
    });
});