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

        loadUserGrjl();

        form.render();
    }

    function loadUserGrjl() {
        table.render({
            elem: '#jyglxt_user_table', //指定原始table容器的选择器或DOM
            url: common.basePath + 'sys/user/queryUserList.html', //数据接口
            //请求头
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            toolbar: '#toolbar_jyglxt_user_table', //指定头工具栏
            page: true, //开启分页
            method: 'post', //请求类型
            where: {}, //接口请求的其他参数
            limit: 10, //每页显示的数量
            limits: [10, 20, 30, 40, 50, 60, 70, 80, 90],
            height: 500, //设定容器的高度
            text: {none: '对不起，暂无相关数据！'},
            cols: [[
                {field: 'name', title: '姓名', width: '12%'},
                {
                    field: 'xb', title: '性别', width: '6%', templet: function (data) {
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
                {field: 'sfz', title: '身份证', width: '18%'},
                {
                    field: 'role', title: '角色', width: '10%', templet: function (data) {
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
                {field: 'lxdh', title: '联系电话', width: '12%'},
                {
                    field: 'yxzt', title: '账号类型', width: '7%', templet: function (data) {
                        var yxzt = data.yxzt;
                        var zhlx = '';
                        switch (yxzt) {
                            case '1':
                                zhlx = '<span style="color: #00FF00; font-weight: bold;">正常</span>';
                                break;
                            case '-1':
                                zhlx = '<span style="color: #CF1900; font-weight: bold;">已冻结</span>';
                                break;
                        }
                        return zhlx;
                    }
                },
                {
                    field: 'right', title: '操作', templet: function (data) {
                        var html = '<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="zhdj">冻结/取消</a>';
                        html += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>';
                        return html;
                    }
                }
            ]],
            //数据渲染完成之后的回调
            done: function (res, curr, count) {
                var data = res.data;
            }
        });

        table.on('toolbar(jyglxt_user_table)', function (obj) {
            switch (obj.event) {
                case 'ref':
                    //table重载
                    refreshUserTable();
                    break;
            }
        });

        //行工具栏
        table.on('tool(jyglxt_user_table)', function (obj) {
            var data = obj.data;
            var role = sessionStorage.getItem('role');
            if (role !== '4') {
                layer.alert('对不起，您暂无此操作权限！', {icon: 1});
                return;
            }
            switch (obj.event) {
                case 'zhdj':
                    var yxzt = data.yxzt;
                    var updateYxzt = '';
                    var czlx = '';
                    switch (yxzt) {
                        case '1':
                            updateYxzt = '-1';
                            czlx = '冻结';
                            break;
                        case '-1':
                            updateYxzt = '1';
                            czlx = '取消冻结';
                            break;
                    }
                    layer.alert('是否确认' + czlx + '【' + data.name + '】的账号信息！', {
                        skin: 'layui_layer_zpdw_dj',
                        icon: 6,
                        btn: ['确认', '取消'],
                        closeBtn: 0,
                        yes: function (index) {
                            layer.close(index);
                            zhDjQx(data.id, updateYxzt, czlx);
                        }
                    });
                    break;
                case 'delete':
                    layer.alert('是否确认删除【' + data.name + '】的账号信息！', {
                        skin: 'layui_layer_zpdw_del',
                        icon: 6,
                        btn: ['确认', '取消'],
                        closeBtn: 0,
                        yes: function (index) {
                            layer.close(index);
                            delYhxxById(data.id);
                        }
                    });
                    break;
            }
        });
    }

    function refreshUserTable() {
        table.reload('jyglxt_user_table', {
            where: {}
        });
    }

    function zhDjQx(id, yxzt, czlx) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'sys/user/zhDjQx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {id: id, yxzt: yxzt},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('账号' + czlx + '成功！', {icon: 1}, function (index) {
                        layer.close(index);
                        refreshUserTable();
                    });
                }
            },
            error: function () {
                layer.close(loadingIndex);
                layer.alert('失败...', {icon: 5});
            }
        });
    }

    function delYhxxById(id) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'sys/user/delYhxxById.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {id: id},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('账号删除成功！', {icon: 1}, function (index) {
                        layer.close(index);
                        refreshUserTable();
                    });
                }
            },
            error: function () {
                layer.close(loadingIndex);
                layer.alert('失败...', {icon: 5});
            }
        });
    }

    //定义抛出的对象
    var obj = {
        load: loadBase
    };

    //抛出对象
    exports('yhgl_index', obj);
});