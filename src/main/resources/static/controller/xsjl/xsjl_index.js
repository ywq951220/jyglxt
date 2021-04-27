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
            elem: '#jyglxt_xsjl_table', //指定原始table容器的选择器或DOM
            url: common.basePath + 'sys/user/queryUserList.html', //数据接口
            //请求头
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            toolbar: '#toolbar_jyglxt_xsjl_table', //指定头工具栏
            page: true, //开启分页
            method: 'post', //请求类型
            where: {role : '1', yxzt: '1'}, //接口请求的其他参数
            limit: 10, //每页显示的数量
            limits: [10, 20, 30, 40, 50, 60, 70, 80, 90],
            height: 500, //设定容器的高度
            text: {none: '对不起，暂无相关数据！'},
            cols: [[
                {field: 'name', title: '姓名', width: '12%'},
                {
                    field: 'xb', title: '性别', width: '12%', templet: function (data) {
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
                {field: 'sfz', title: '身份证', width: '20%'},
                {
                    field: 'role', title: '角色', width: '12%', templet: function (data) {
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
                    field: 'right', title: '操作', templet: function (data) {
                        var html = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="grjl">个人简历</a>';
                        return html;
                    }
                }
            ]],
            //数据渲染完成之后的回调
            done: function (res, curr, count) {
                var data = res.data;
            }
        });

        table.on('toolbar(jyglxt_xsjl_table)', function (obj) {
            switch (obj.event) {
                case 'ref':
                    //table重载
                    table.reload('jyglxt_xsjl_table', {
                        where: {}
                    });
                    break;
            }
        });

        //行工具栏
        table.on('tool(jyglxt_xsjl_table)', function (obj) {
            var data = obj.data;
            console.log(data);
            data.userId = data.id;
            layui.grxx_index.grxxData = data;
            switch (obj.event) {
                case 'grjl':
                    if (!data.grjlId) {
                        layer.alert('求职者【' + data.name + '】尚未维护个人简历信息！');
                        return;
                    }
                    layer.open({
                        type: 2,
                        title: '【' + data.name + '】个人简历信息',
                        id: 'layer_open_user_grjl',
                        area: ['90%', '80%'],
                        btnAlign: 'c',
                        content: '../grxx/user_grjl.html',
                        //层弹出后的成功回调方法
                        success: function (layero, index) {

                        }
                    });
                    break;
            }
        });
    }

    //定义抛出的对象
    var obj = {
        load: loadBase
    };

    //抛出对象
    exports('xsjl_index', obj);
});