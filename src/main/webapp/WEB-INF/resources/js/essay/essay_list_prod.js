var essayMap = new Map();
$(document).ready(function () {
    $("#essay_manage_node").addClass("active");
    $("#essay_manage_menu_node").addClass("active");
    $("#essay_list_prod_node").addClass("active");
    const baseUrl = $("#baseUrl").val();
    const divSelector = $("#essayList");
    const table = divSelector.DataTable({
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
            url: baseUrl + "/essay/list/json",
            data: {
                "type": "prod"
            }
        },
        columns: [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": ''
            },
            {data: "essayId"},
            {data: "title"},
            {data: "subtitle"},
            {data: "createTime"},
            {"render": render_getOperationHtml}
        ]
    });
    const otaVersionListDiv = $(`#essayList`).find("tbody");
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

});

function render_getOperationHtml(data, type, full, meta) {
    essayMap.put(full.essayId, new Array(full.title, full.subtitle, full.summary, full.coverUrl, full.contentUrl));
    var OperationHtml = '<div class="btn-group pull-right">'
        + '<button type="button" class="btn dropdown-toggle"'
        + 'data-toggle="dropdown" data-hover="dropdown" data-delay="1000" data-close-others="true">'
        + '操作 <i class="fa fa-angle-down"></i>'
        + '</button>'
        + '<ul class="dropdown-menu pull-right" role="menu">'
        + '<li><a id="' + full.essayId + '"  onclick="editRow(this)">编辑</a></li>'
        + '<li><a id="' + full.essayId + '" onclick="deleteRow(this)">删除</a></li>'
        + '</ul>'
        + '</div>';
    return OperationHtml;
}

function deleteRow(row) {
    let essayId = row.id;
    if (confirm("确定要删除该文章？")) {
        let result = deleteEssay(essayId);
        if ('success' === result) {
            window.history.go(0);
        } else {
            alert("Delete fail.");
        }
    }
}

function deleteEssay(essayId) {
    const baseUrl = $("#baseUrl").val();
    let result = 'fail:' + 'please try again.';
    $.ajax({
        type: "POST",
        url: baseUrl + "/essay/delete",
        dataType: "json",
        async: false,
        data: {
            "essayId": essayId,
            "type": "prod"
        },
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
 * 格式化
 */
function format(d) {
    return `<table class="table">
            <tr>
                <td>ID:</td>
                <td>${d.essayId}</td>
            </tr>
            <tr>
                <td>标题:</td>
                <td>
                    ${d.title}
                </td>
            </tr>
            <tr>
                <td>副标题:</td>
                <td>${d.subtitle}</td>
            </tr>
            <tr>
                <td>简介:</td>
                <td>${d.summary}</td>
            </tr>
            <tr>
                <td>封面预览:</td>
                <td><img src=${d.coverUrl} id="image" style="width: 320px;height: 240px "/></td>
            </tr>
            <tr>
                <td>正文URL:</td>
                <td>${d.contentUrl}</td>
            </tr>
            <tr>
                <td>创建时间:</td>
                <td>${d.createTime}</td>
            </tr>
            <tr>
                <td>更新时间:</td>
                <td>${d.updateTime}</td>
            </tr>
        </table>`;
}

/**
 * 编辑文章
 *
 */
function editRow(row) {
    $("#essayModalLabel").text("编辑");
    var essayId = row.id;
    var title = essayMap.get(essayId)[0];
    var subtitle = essayMap.get(essayId)[1];
    var summary = essayMap.get(essayId)[2];
    var coverUrl = essayMap.get(essayId)[3];
    var contentUrl = essayMap.get(essayId)[4];
    $("#essayId").val(essayId);
    $("#title").val(title);
    $("#subtitle").val(subtitle);
    $("#summary").val(summary);
    $("#coverUrl").val(coverUrl);
    $("#contentUrl").val(contentUrl);
    $('#essayModal').modal();
}

function updateEssay() {
    const baseUrl = $("#baseUrl").val();
    let formData = new FormData($("#updateEssay")[0]);
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
            url: baseUrl + "/essay/update",
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

function Map() {
    /** 存放键的数组(遍历用到) */
    this.keys = new Array();
    /** 存放数据 */
    this.data = new Object();

    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if(this.data[key] == null){
            this.keys.push(key);
        }
        this.data[key] = value;
    };

    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };

    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function(key) {
        this.keys.remove(key);
        this.data[key] = null;
    };

    /**
     * 遍历Map,执行处理函数
     *
     * @param {Function} 回调函数 function(key,value,index){..}
     */
    this.each = function(fn){
        if(typeof fn != 'function'){
            return;
        }
        var len = this.keys.length;
        for(var i=0;i<len;i++){
            var k = this.keys[i];
            fn(k,this.data[k],i);
        }
    };

    /**
     * 获取键值数组(类似<a href="http://lib.csdn.net/base/java" class='replace_word' title="Java 知识库" target='_blank' style='color:#df3434; font-weight:bold;'>Java</a>的entrySet())
     * @return 键值对象{key,value}的数组
     */
    this.entrys = function() {
        var len = this.keys.length;
        var entrys = new Array(len);
        for (var i = 0; i < len; i++) {
            entrys[i] = {
                key : this.keys[i],
                value : this.data[i]
            };
        }
        return entrys;
    };

    /**
     * 判断Map是否为空
     */
    this.isEmpty = function() {
        return this.keys.length == 0;
    };

    /**
     * 获取键值对数量
     */
    this.size = function(){
        return this.keys.length;
    };

    /**
     * 重写toString
     */
    this.toString = function(){
        var s = "{";
        for(var i=0;i<this.keys.length;i++,s+=','){
            var k = this.keys[i];
            s += k+"="+this.data[k];
        }
        s+="}";
        return s;
    };
}


