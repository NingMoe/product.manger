const testEle = `<span><select class="form-control select2" id="testSelector">
                               <option selected="selected"></option>
                               <option>测试版</option>
                               <option>公开版</option>
                           </select>
                     </span>`;

const enableEle = `<span><select class="form-control select2" id="enableSelector">
                               <option selected="selected"></option>
                               <option>不可用</option>
                               <option>可用</option>
                           </select>
                     </span>`;

const modifyBtn = `<i class="fa fa-arrow-up" style="cursor: pointer" id="updateVersionBtn" 
        onclick="upgradeVersionList(this)"></i>`;

let firstClickVersion;

let firstClick = true;

/**
 * 初始化表格
 */
$(document).ready(function () {
    $("#firmware-upgrade-node").addClass("active");
    $("#firmware-upgrade-menu-node").addClass("active");
    $("#balance_mcu_menu_node").addClass("active");
    $("#balance_mcu_list_test_node").addClass("active");
    let baseUrl = $("#baseUrl").val();

    const table = $("#mcuVersionList").DataTable({
        paging: true,
        searching: true,
        ordering: true,
        processing: true,
        aLengthMenu: [[10, 20, 40], ["10", "20", "40"]],
        iDisplayLength: 10,
        autoWidth: false,
        oLanguage: {
            sProcessing: "努力加载数据中...",
            sLengthMenu: "每页显示 _MENU_ 条记录",
            sZeroRecords: "抱歉，没有找到",
            sInfo: "从 _START_ 到 _END_ / 共 _TOTAL_ 条数据",
            sInfoEmpty: "没有数据",
            oPaginate: {
                sFirst: "首页",
                sPrevious: "前一页",
                sNext: "后一页",
                sLast: "尾页"
            }
        },
        ajax: {
            type: "POST",
            dataType: "JSON",
            url: baseUrl + "/balance/mcu/list/json",
            data: {
                "environment": "test"
            }
        },
        columns: [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {data: "version"},
            {data: "testing"},
            {data: "enable"},
            {data: "createTime"},
            {
                "orderable": false,
                "data": null,
                "defaultContent": ''
            }
        ]
    });
    const otaVersionListDiv = $(`#mcuVersionList`).find("tbody");

    /**
     * 下滑展示详情
     */
    otaVersionListDiv.on("click", "td.details-control", function () {
        let tr = $(this).closest('tr');
        let row = table.row(tr);
        if (row.child.isShown()) {
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });

    /**
     * 双击监听
     */
    otaVersionListDiv.on('dblclick', 'td', function () {
        const lineNumber = $(this).parent().find('td').index($(this)[0]);
        if ((lineNumber === 2 || lineNumber === 3 ) && (firstClick || firstClickVersion === $(this).parent().find('td').eq(1).text())) {
            if (lineNumber === 2) {
                selectNewValue(this, 2);
            } else {
                selectNewValue(this, 3);
            }
        }
    });

});

/**
 * 双击后出现选择框来选择需要的值
 * @param node 节点
 * @param columnNumber 行号
 */
function selectNewValue(node, columnNumber) {
    let selector;
    let oldVal;
    $(node).text('');
    remove(node);
    if (columnNumber === 2) {
        $(node).append(testEle);
        selector = $("#testSelector");
    } else {
        $(node).append(enableEle);
        selector = $("#enableSelector");
    }
    selector.focus();
    selector.blur(function () {
        if ($(this).val() !== '') {
            oldVal = $(this).val();
        }
        $(this).closest('td').text(oldVal);
    });
    if (document.getElementById('updateVersionBtn') === null) {
        $(node).parent().find('td').eq(5).append(modifyBtn);
    }
    firstClick = false;
    firstClickVersion = $(node).parent().find('td').eq(1).text();
}

/**
 * 修改版本状态并移除按钮
 * @param node 节点
 */
function upgradeVersionList(node) {
    const version = node.parentNode.parentNode.children[1].innerText;
    const testing = node.parentNode.parentNode.children[2].innerText === '公开版' ? 0 : 1;
    const enable = node.parentNode.parentNode.children[3].innerText === '可用' ? 1 : 0;
    let result = modifyVersionStatus(version, testing, enable);
    if (confirm("确定要修改该版本的状态？")) {
        if ('success' === result) {
            remove(document.getElementById('updateVersionBtn').parentNode);
            firstClick = true;
        }
        alert(result);
    }
}

/**
 * 修改版本状态
 * @param version 版本号
 * @param testing 是否为测试版
 * @param enable 是否可用
 */
function modifyVersionStatus(version, testing, enable) {
    const baseUrl = $("#baseUrl").val();
    let result = 'fail:' + 'please try again.';
    $.ajax({
        type: "POST",
        url: baseUrl + "/balance/mcu/status/update/trigger",
        dataType: "json",
        async: false,
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            "environment": 'test',
            "version": version,
            "testing": testing,
            "enable": enable
        }),
        error: function (req, status, err) {
            result = 'Failed reason: ' + err;
        }, success: function (data) {
            const status = data.status;
            if (status === 0) {
                result = 'success';
            } else {
                result = 'fail:' + 'please try again.';
            }
        }
    });
    return result;
}

/**
 * 移除子节点
 */
function remove(element) {
    const childs = element.childNodes;
    for (let i = childs.length - 1; i >= 0; i--) {
        element.removeChild(childs[i]);
    }
}

/**
 * 格式化
 */
function format(d) {
    return `<table class="table">
            <tr>
                <td>ID:</td>
                <td>${d.id}</td>
            </tr>
             <tr>
                <td>固件版本:</td>
                <td>${d.version}</td>
            </tr>
            <tr>
                <td>文件下载链接:</td>
                <td><a href="${d.fileUrl}">${d.fileUrl}</a></td>
            </tr><tr>
                <td>文件CRC:</td>
                <td>${d.crc}</td>
            </tr>
            <tr>
                <td>版本类型:</td>
                <td>${d.testing}</td>
            </tr>
            <tr>
                <td>版本状态:</td>
                <td>${d.enable}</td>
            </tr>
            <tr>
                <td>上传时间:</td>
                <td>${d.createTime}</td>
            </tr>
        </table>`;
}