layui.extend({
    // common: '{/}/jyglxt/src/main/resources/static/controller/common'
    common: '{/}/jyglxt/static/controller/common'
}).define(['table', 'form', 'common'], function (exports) {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var common = layui.common;

    var openId;

    //页面初始化加载
    function loadBase() {
        var loginId = sessionStorage.getItem('id');

        //获取当前登录的用户信息
        loadUserGrxx(loginId);
    }

    //获取当前登录的用户信息
    function loadUserGrxx(id) {
        table.render({
            elem: '#jyglxt_grxx_table', //指定原始table容器的选择器或DOM
            url: common.basePath + 'sys/user/queryLoginUser.html', //数据接口
            //请求头
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            toolbar: '#toolbar_jyglxt_grxx_table', //指定头工具栏
            page: true, //开启分页
            method: 'post', //请求类型
            where: {id: id}, //接口请求的其他参数
            limit: 5, //每页显示的数量
            limits: [5, 10, 20, 30, 40, 50, 60, 70, 80, 90],
            height: 400, //设定容器的高度
            text: {none: '对不起，暂无相关数据！'},
            cols: [[
                {field: 'name', title: '姓名', width: '10%'},
                {
                    field: 'xbmc', title: '性别', width: '10%', templet: function (data) {
                        var sex = data.sex;
                        var xbmc = '';
                        switch (sex) {
                            case '1':
                                xbmc = '男性';
                                break;
                            case '2':
                                xbmc = '女性';
                                break;
                            case '9':
                                xbmc = '未知';
                                break;
                        }
                        return xbmc;
                    }
                },
                {field: 'sfz', title: '身份证号', width: '10%'},
                {field: 'userName', title: '用户名', width: '10%'},
                {
                    field: 'roleName', title: '角色', width: '10%', templet: function (data) {
                        var role = data.role;
                        var roleName = '';
                        switch (role) {
                            case '1':
                                roleName = '求职者';
                                break;
                            case '2':
                                roleName = '企业HR';
                                break;
                            case '3':
                                roleName = '学校就业负责人';
                                break;
                            case '4':
                                roleName = '系统管理员';
                                break;
                        }
                        return roleName;
                    }
                },
                {field: 'zcsj', title: '注册时间', width: '10%'},
                {
                    field: 'right', title: '操作', templet: function (data) {
                        var role = data.role;
                        var html = '<a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>';
                        if (role === '1') {
                            html += '<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="grjl">个人简历</a>';
                        }
                        html += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="editPass">密码修改</a>';
                        return html;
                    }
                }
            ]],
            //数据渲染完成之后的回调
            done: function (res, curr, count) {
                var data = res.data;
            }
        });

        //行工具栏
        table.on('tool(jyglxt_grxx_table)', function (obj) {
            var data = obj.data;
            data.userId = data.id;
            layui.grxx_index.grxxData = data;
            switch (obj.event) {
                case 'edit':
                    layer.open({
                        type: 2,
                        title: '用户修改',
                        id: 'layer_open_user_zc',
                        area: ['60%', '60%'],
                        btnAlign: 'c',
                        // btn: ['保存', '关闭'],
                        // offset: '30px',
                        // shade: 0.1,
                        content: '../user_register.html'
                    });
                    break;
                case 'grjl':
                    layer.open({
                        type: 2,
                        title: '个人简历',
                        id: 'layer_open_user_grjl',
                        area: ['90%', '80%'],
                        btnAlign: 'c',
                        content: '../grxx/user_grjl.html'
                    });
                    break;
                case 'editPass':
                    layer.alert('确认修改您的密码吗？', {
                        skin: 'layui-layer_yhgl_logout',
                        icon: 6,
                        btn: ['确认', '取消'],
                        closeBtn: 0,
                        yes: function (index) {
                            layer.close(index);
                            openId = layer.open({
                                type: 1,
                                id: 'layer_open_update_yhmm',
                                title: '用户密码修改',
                                area: ['70%', '60%'],
                                content: $('#update_password'),
                                btnAlign: 'c',
                                shade: 0,
                            });
                        }
                    })
                    break;
            }
        });
    }

    function qrBtn() {
        var formData = common.getFormJson($('#update_password_form'));
        var oldPassword = formData.password;
        if (!oldPassword) {
            layer.alert('请输入原始密码！', {icon: 5});
            return;
        }
        var sessionPassword = sessionStorage.getItem('password');
        if (oldPassword !== sessionPassword) {
            layer.alert('原始密码有误，请确认无误后重新修改！', {icon: 5});
            return;
        }
        var newPassword = formData.newPassword;
        var qrNewPassword = formData.qrNewPassword;
        if (!newPassword) {
            layer.alert('请输入新密码！', {icon: 5});
            return;
        }
        if (!qrNewPassword) {
            layer.alert('请再次输入新密码！', {icon: 5});
            return;
        }
        if (newPassword !== qrNewPassword) {
            layer.alert('两次密码输入不同，请重新输入！', {icon: 5});
            return;
        }
        if (oldPassword === newPassword) {
            layer.alert('原密码和新密码相同！', {icon: 5});
            return;
        }
        var id = sessionStorage.getItem('id');
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'sys/user/updatePassword.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            async: true,
            data: {id: id, newPassword: newPassword},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.close(openId);
                    layer.alert('密码修改成功，请重新登录！', function (index) {
                        sessionStorage.setItem('token', '');
                        layer.close(index);
                        parent.window.location.href = '../../templates/login.html';
                    })
                } else {
                    layer.alert('失败...', {icon: 5});
                }
            },
            error: function () {
                layer.close(loadingIndex);
            }
        });
    }

    function refreshTable() {

    }

    //定义抛出的对象
    var obj = {
        load: loadBase,
        refreshTable: refreshTable,
        qrBtn: qrBtn
    };

    //抛出对象
    exports('grxx_index', obj);
});