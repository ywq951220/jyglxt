layui.extend({
    // common: '{/}/jyglxt/src/main/resources/static/controller/common'
    common: '{/}/jyglxt/static/controller/common'
}).define(['table', 'form', 'common'], function (exports) {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var common = layui.common;


    //页面初始化加载
    function loadBase() {
        loadXsUserJyqk();

        form.render();
    }

    function loadXsUserJyqk() {
        table.render({
            elem: '#jyglxt_xsjyqk_table', //指定原始table容器的选择器或DOM
            url: common.basePath + 'sys/user/queryXsjyqkList.html', //数据接口
            //请求头
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            toolbar: '#toolbar_jyglxt_xsjyqk_table', //指定头工具栏
            page: true, //开启分页
            method: 'post', //请求类型
            where: {}, //接口请求的其他参数
            limit: 10, //每页显示的数量
            limits: [10, 20, 30, 40, 50, 60, 70, 80, 90],
            height: 500, //设定容器的高度
            text: {none: '对不起，暂无相关数据！'},
            cols: [[
                {field: 'name', title: '姓名', width: '7%'},
                {
                    field: 'xb', title: '性别', width: '5%', templet: function (data) {
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
                {field: 'sfz', title: '身份证', width: '12%'},
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
                {
                    field: 'jltdjlId', title: '是否投递简历', width: '9%', templet: function (data) {
                        var jltdjlId = data.jltdjlId;
                        var sftdjl = '';
                        if (jltdjlId) {
                            sftdjl = '<span style="color: lime; font-weight: bold;">是</span>';
                        } else {
                            sftdjl = '<span style="color: red; font-weight: bold;">否</span>';
                        }
                        return sftdjl;
                    }
                },
                {
                    field: 'spzt', title: '简历审核状态', width: '12%', templet: function (data) {
                        var spzt = data.spzt;
                        var spztName = '';
                        switch (spzt) {
                            case '0':
                                spztName = '<span style="color: #bd644e; font-weight: bold;">未审核</span>';
                                break;
                            case '1':
                                spztName = '<span style="color: dodgerblue; font-weight: bold;">邀请面试</span>';
                                break;
                            case '-1':
                                spztName = '<span style="color: #CF1900; font-weight: bold;">拒绝面试</span>';
                                break;
                        }
                        return spztName;
                    }
                },
                {
                    field: 'lyzt', title: '学生录用状态', width: '12%', templet: function (data) {
                        var lyzt = data.lyzt;
                        var lyztName = '';
                        switch (lyzt) {
                            case '0':
                                lyztName = '<span style="color: #bd644e; font-weight: bold;">未反馈</span>';
                                break;
                            case '1':
                                lyztName = '<span style="color: dodgerblue; font-weight: bold;">已录用</span>';
                                break;
                            case '-1':
                                lyztName = '<span style="color: #CF1900; font-weight: bold;">拒绝录用</span>';
                                break;
                        }
                        return lyztName;
                    }
                }
            ]],
            //数据渲染完成之后的回调
            done: function (res, curr, count) {
                var data = res.data;
            }
        });

        table.on('toolbar(jyglxt_xsjyqk_table)', function (obj) {
            switch (obj.event) {
                case 'ref':
                    //table重载
                    table.reload('jyglxt_xsjyqk_table', {
                        where: {}
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
    exports('xsjyqk_index', obj);
});