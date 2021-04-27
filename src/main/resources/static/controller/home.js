
layui.define(['table', 'common', 'carousel'],function(exports) {
    var $ = layui.$;
    var common = layui.common;
    var table = layui.table;
    var carousel = layui.carousel;

    function loadBase () {
        $('#loginUserName').html(sessionStorage.getItem('name'));

        //建造实例
        carousel.render({
            elem: '#home_lbt_div',
            width: '100%' //设置容器宽度
        });

        loadUsrRoleCount();

        loadHomeXtgg();
    }

    function loadUsrRoleCount() {
        $.ajax({
            url: common.basePath + 'sys/user/queryUserRoleSjtj.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: common.token
            },
            dataType: 'json',
            type: 'post',
            async: true,
            data: {},
            success: function (data) {
                if (data.code === 200) {
                    var result = data.data;
                    $('#qzz_user_sl').html(result.qzzCount);
                    $('#qyhr_user_sl').html(result.qyHRCount);
                    $('#xxjyfzr_user_sl').html(result.xxjyfzrCount);
                    $('#xtgly_user_sl').html(result.xtglyCount);
                } else {
                    layui.alert('系统用户分析失败，请稍后重试！', {icon: 5});
                }
            },
            error: function () {
                layui.alert('系统出错，请稍后重试！', {icon: 5});
            }
        });
    }

    function openUserTable(role) {
        layer.open({
            type: 1,
            title: '用户信息',
            id: 'layer_open_user',
            area: ['80%', '80%'],
            btnAlign: 'c',
            content: $('#home_user_table_div'),
            success: function (layero, index) {
                queryUserByRole(role);
            }
        });

    }

    function queryUserByRole(role) {
        table.render({
            elem: '#home_user_table', //指定原始table容器的选择器或DOM
            url: common.basePath + 'sys/user/queryUserList.html', //数据接口
            //请求头
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            toolbar: '#toolbar_home_user_table', //指定头工具栏
            page: true, //开启分页
            method: 'post', //请求类型
            where: {role : role, yxzt: '1'}, //接口请求的其他参数
            limit: 5, //每页显示的数量
            limits: [5, 10, 20, 30, 40, 50, 60, 70, 80, 90],
            height: 360, //设定容器的高度
            text: {none: '对不起，暂无相关数据！'},
            cols: [[
                {field: 'name', title: '姓名', width: '10%'},
                {
                    field: 'xb', title: '性别', width: '8%', templet: function (data) {
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
                {field: 'sfz', title: '身份证', width: '30%'},
                {
                    field: 'role', title: '角色', width: '15%', templet: function (data) {
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
                {field: 'lxdh', title: '联系电话', width: '20%'}
            ]],
            //数据渲染完成之后的回调
            done: function (res, curr, count) {
                var data = res.data;
                console.log(data);
            }
        });

        table.on('toolbar(home_user_table)', function (obj) {
            switch (obj.event) {
                case 'ref':
                    //table重载
                    table.reload('home_user_table', {
                        where: {}
                    });
                    break;
            }
        });
    }

    function openYqlj(dataType) {
        switch (dataType) {
            case '1':
                window.open('https://wenku.baidu.com/view/d143c86475232f60ddccda38376baf1ffc4fe331.html');
                break;
            case '2':
                window.open('http://www.360doc.com/content/17/1218/11/35396069_714152913.shtml');
                break;
            case '3':
                window.open('https://www.520z-2.com/fanwen/481443.html');
                break;
            case '4':
                window.open('http://www.zhuna.cn/newsinfo/info_208335.html');
                break;
            case '5':
                window.open('http://www.chyxx.com/industry/202002/837558.html');
                break;
            case '6':
                window.open('http://jiuye.cnzsedu.com/');
                break;
        }
    }

    function loadHomeXtgg() {
        $.ajax({
            url: common.basePath + 'index/queryHomeXtggList.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: common.token
            },
            dataType: 'json',
            type: 'post',
            async: true,
            data: {},
            success: function (data) {
                if (data.code === 200) {
                    var result = data.data;
                    var html = '';
                    if (result.length > 0) {
                        for (var i = 0; i < result.length; i++) {
                            html +=
                                '<div class="layui-col-md12" style="margin-top: 5px;">' +
                                    result[i] +
                                '</div>';
                        }
                        $('#home_xtgg_row_div').html(html);
                    }
                } else {
                    layui.alert('系统公告数据统计失败，请稍后重试！', {icon: 5});
                }
            },
            error: function () {
                layui.alert('系统出错，请稍后重试！', {icon: 5});
            }
        });
    }

    var obj = {
        load: loadBase,
        openUserTable: openUserTable,
        openYqlj: openYqlj
    }

    exports('home', obj);
});