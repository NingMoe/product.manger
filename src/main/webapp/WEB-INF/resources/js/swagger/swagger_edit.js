let newProject;
/**
 * 初始化：如果是页面跳转则赋值
 */
$(function () {
    $("#swagger_menu_node").addClass("active");
    //非管理员权限则关闭
    if (projectName !== "$projectName") {
        let initialValue = fetchProjectDetail(projectName);
        if (initialValue !== '') {
            $('#projectName').val(initialValue.projectName).attr('disabled', true);
            $('#projectAddress').val(initialValue.projectAddress);
            $('#swaggerAddressProd').val(initialValue.swaggerAddressProd);
            $('#swaggerUsernameProd').val(initialValue.swaggerUsernameProd);
            $('#swaggerPasswordProd').val(initialValue.swaggerPasswordProd);
            $('#swaggerAddressTest').val(initialValue.swaggerAddressTest);
            $('#swaggerUsernameTest').val(initialValue.swaggerUsernameTest);
            $('#swaggerPasswordTest').val(initialValue.swaggerPasswordTest);
            $('#description').val(initialValue.description);
            newProject = false;
        }
    } else {
        newProject = true;
    }

});

/**
 * 处理按钮事件
 */
function editOrInsertNewProject() {
    let obj = {};
    obj.projectName = $('#projectName').val();
    obj.projectAddress = $('#projectAddress').val();
    obj.swaggerAddressProd = $('#swaggerAddressProd').val();
    obj.swaggerUsernameProd = $('#swaggerUsernameProd').val();
    obj.swaggerPasswordProd = $('#swaggerPasswordProd').val();
    obj.swaggerAddressTest = $('#swaggerAddressTest').val();
    obj.swaggerUsernameTest = $('#swaggerUsernameTest').val();
    obj.swaggerPasswordTest = $('#swaggerPasswordTest').val();
    obj.description = $('#description').val();
    if (obj.projectName === null || obj.projectName === ''
        && obj.projectAddress === null || obj.projectAddress === ''
        && obj.swaggerAddressProd === null || obj.swaggerAddressProd === ''
        && obj.swaggerUsernameProd === null || obj.swaggerUsernameProd === ''
        && obj.swaggerPasswordProd === null || obj.swaggerPasswordProd === ''
        && obj.swaggerAddressTest === null || obj.swaggerAddressTest === ''
        && obj.swaggerUsernameTest === null || obj.swaggerUsernameTest === ''
        && obj.swaggerPasswordTest === null || obj.swaggerPasswordTest === ''
        && obj.description === null || obj.description === '') {
        alert("请完善信息");
    }
    if (newProject) {
        insertNewProject(obj);
    } else {
        update(obj);
    }
}

/**
 * 更新一下某个记录
 * @param obj 项目信息
 */
function update(obj) {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/swagger/project/edit",
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType: 'json',
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            const status = data.status;
            if (status === 0) {
                alert('success');
                window.location.href = baseUrl + "/swagger/project/manager";
            } else {
                alert("未知错误!");
            }
        }
    });
}

/**
 * 写入一条新记录
 * @param obj 项目信息
 */
function insertNewProject(obj) {
    const baseUrl = $("#baseUrl").val();
    $.ajax({
        type: "POST",
        url: baseUrl + "/swagger/project/insert",
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType: 'json',
        error: function (req, status, err) {
            alert('Failed reason: ' + err);
        }, success: function (data) {
            const status = data.status;
            if (status === 0) {
                alert('success');
                window.location.reload();
            } else if (status === 2) {
                alert("数据格式错误，请检查!");
            } else if (status === 14) {
                alert("该项目已经存在！");
            } else {
                alert("未知错误!");
            }
        }
    });
}

/**
 * 获取某个项目的详情
 * @param projectName 项目名称
 * @returns {string} 详情数据
 */
function fetchProjectDetail(projectName) {
    const baseUrl = $("#baseUrl").val();
    let result = '';
    $.ajax({
        type: "POST",
        url: baseUrl + "/swagger/project/detail",
        dataType: "json",
        async: false,
        data: {
            'projectName': projectName
        },
        error: function (req, status, err) {
            result = 'Failed reason: ' + err;
        }, success: function (data) {
            const status = data.status;
            if (status === 0) {
                result = data.data;
            }
        }
    });
    return result;
}
