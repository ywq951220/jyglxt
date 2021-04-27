layui.define(['table', 'form', 'common'], function (exports) {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var common = layui.common;

    //页面初始化加载
    function loadBase() {
    }

    //登录按钮
    function loginBtn() {
        $('#errorDiv').html('');
        var jsonData = common.getFormJson($('#actionForm'));
        var userName = jsonData.userName;
        if (!userName) {
            $('#errorDiv').html('用户名信息为空！');
            return;
        }
        var password = jsonData.password;
        if (!password) {
            $('#errorDiv').html('密码信息为空！');
            return;
        }
        $.ajax({
            url: common.basePath + 'index/ssoLogin.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: common.token
            },
            dataType: 'json',
            type: 'post',
            async: true,
            data: {userName: userName, password: password},
            success: function (data) {
                if (data.code === 200) {
                    var yxzt = data.data.yxzt;
                    if (yxzt === '1') {
                        layer.msg('登录成功！欢迎光临...');
                        sessionStorage.setItem('id', data.data.id);
                        sessionStorage.setItem('name', data.data.name);
                        sessionStorage.setItem('sfz', data.data.sfz);
                        sessionStorage.setItem('password', data.data.password);
                        sessionStorage.setItem('role', data.data.role);
                        sessionStorage.setItem('token', data.data.token);
                        // var url = '../templates/index.html';
                        var url = 'http://localhost:63342/jyglxt/templates/index.html';
                        window.location = encodeURI(encodeURI(url));
                    } else if (yxzt === '-1') {
                        $('#errorDiv').html('登录失败！账号【'+ data.data.userName +'】已被冻结！');
                    }
                } else {
                    $('#errorDiv').html('登录失败！用户名或密码错误！');
                }
            },
            error: function () {
                $('#errorDiv').html('接口服务异常！');
            }
        });
    }

    //注册按钮
    function registerBtn() {
        layer.open({
            type: 2,
            title: '用户注册',
            id: 'layer_open_user_zc',
            area: ['60%', '70%'],
            btnAlign: 'c',
            // btn: ['保存', '关闭'],
            // offset: '30px',
            // shade: 0.1,
            content: './user_register.html'
        });
    }

    //定义抛出的对象
    var obj = {
        load: loadBase,
        loginBtn: loginBtn,
        registerBtn: registerBtn
    };

    //抛出对象
    exports('login', obj);
});