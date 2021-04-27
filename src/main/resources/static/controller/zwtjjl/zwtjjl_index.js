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
        var role = sessionStorage.getItem('role');

        //获取当前登录的用户信息
        loadUserGrxx(loginId, role);
    }

    //获取当前登录的用户信息
    function loadUserGrxx(id, role) {
        table.render({
            elem: '#jyglxt_zwtjjl_table', //指定原始table容器的选择器或DOM
            url: common.basePath + 'tb/zwtjjl/queryZwtjjlListByUserId.html', //数据接口
            //请求头
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            toolbar: '#toolbar_jyglxt_zwtjjl_table', //指定头工具栏
            page: true, //开启分页
            method: 'post', //请求类型
            where: {txUserId: id, role: role}, //接口请求的其他参数
            limit: 5, //每页显示的数量
            limits: [5, 10, 20, 30, 40, 50, 60, 70, 80, 90],
            height: 400, //设定容器的高度
            text: {none: '对不起，暂无相关数据！'},
            cols: [[
                {field: 'txr_xm', title: '推向人姓名', width: '10%'},
                {field: 'tjr_xm', title: '推荐人姓名', width: '10%'},
                {field: 'tjgs', title: '推荐公司', width: '20%'},
                {field: 'tjzw', title: '推荐职位', width: '20%'},
                {
                    field: 'sftd', title: '是否投递', width: '10%', templet: function (data) {
                        var sftd = data.sftd;
                        var sftdName = '';
                        switch (sftd) {
                            case '1':
                                sftdName = '<span style="color: #00FF00; font-weight: bold;">已投递</span>';
                                break;
                            case '0':
                                sftdName = '<span style="color: #CF1900; font-weight: bold;">未投递</span>';
                                break;
                        }
                        return sftdName;
                    }
                },
                {
                    field: 'right', title: '操作', templet: function (data) {
                        var role = sessionStorage.getItem('role');
                        var html = '';
                        if (role === '1') {
                            html += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="tdjl">投递简历</a>';
                        }
                        if (role === '3') {
                            html += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>';
                        }
                        return html;
                    }
                }
            ]],
            //数据渲染完成之后的回调
            done: function (res, curr, count) {
                var data = res.data;
            }
        });

        table.on('toolbar(jyglxt_zwtjjl_table)', function (obj) {
            switch (obj.event) {
                case 'ref':
                    //table重载
                    refreshZwtjjlTable();
                    break;
            }
        });

        //行工具栏
        table.on('tool(jyglxt_zwtjjl_table)', function (obj) {
            var data = obj.data;
            data.userId = data.id;
            switch (obj.event) {
                case 'tdjl':
                    if (sessionStorage.getItem('role') !== '1') {
                        layer.alert('对不起，您暂无此操作权限！', {icon: 5});
                        return;
                    }
                    if (data.sftd === '1') {
                        layer.alert('对不起，该推荐信息已申请投递简历，无法再次申请！', {icon: 5});
                        return;
                    }
                    var userId = sessionStorage.getItem('id');
                    hasJltdByUserId(data, userId);
                    break;
                case 'delete':
                    if (sessionStorage.getItem('role') !== '3') {
                        layer.alert('对不起，您暂无此操作权限！', {icon: 5});
                        return;
                    }
                    if (data.sftd === '1') {
                        layer.alert('对不起，该推荐信息已被投递简历，无法删除！', {icon: 5});
                        return;
                    }
                    layer.alert('确认删除该推荐信息吗？', {
                        skin: 'layui-layer_zwyj',
                        icon: 6,
                        btn: ['确认', '取消'],
                        closeBtn: 0,
                        yes: function (index) {
                            layer.close(index);
                            delZwtjjl(data.id);
                        }
                    });
                    break;
            }
        });
    }

    function refreshZwtjjlTable() {
        table.reload('jyglxt_zwtjjl_table', {
            where: {}
        });
    }

    //判断该求职者是否完善简历，若没有！则提示先完善简历
    function hasJltdByUserId(data, userId) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/grjl/hasGrjlByUserId.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {userId: userId},
            success: function (res) {
                layer.close(loadingIndex);
                var result = res.data;
                if (res.code === 200) {
                    if (!result) {
                        layer.alert('请先完善个人简历信息！', {icon: 1});
                        return;
                    } else {
                        hasUserSflyByUserId(data, userId);
                    }
                } else {
                    layer.alert(res.message, {icon: 5});
                }
            },
            error: function () {
                layer.close(loadingIndex);
                layer.alert('失败...', {icon: 5});
            }
        });
    }

    //确定该求职者是否被录用，若录用！则无法再次申请投递简历
    function hasUserSflyByUserId(data, userId) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/jltdjl/hasUserSflyByUserId.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {userId: userId},
            success: function (res) {
                layer.close(loadingIndex);
                console.log(res);
                var result = res.data;
                if (res.code === 200) {
                    if (result.id) {
                        //已被录用
                        layer.alert('求职者【'+ result.sqrxm +'】已被【'+ result.sqdw +'】录用为【'+ result.sqzw +'】，无法再次申请职位！', {icon: 1});
                    } else {
                        //未被录用
                        layer.alert('是否确认申请该公司的【'+ data.tjzw +'】职位？', {
                            skin: 'layui-layer_yhgl_logout',
                            icon: 6,
                            btn: ['确认', '取消'],
                            closeBtn: 0,
                            yes: function (index) {
                                layer.close(index);
                                saveUserJlsqjl(data);
                            }
                        });
                    }
                }
            },
            error: function () {
                layer.close(loadingIndex);
                layer.alert('失败...', {icon: 5});
            }
        });
    }

    function saveUserJlsqjl(zwData) {
        var jlsqjlData = {};
        jlsqjlData.userId = sessionStorage.getItem('id');
        jlsqjlData.dwId = zwData.dwId;
        jlsqjlData.zwId = zwData.zwId;
        jlsqjlData.spzt = '0';
        jlsqjlData.lyzt = '0';
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/jltdjl/saveUserTdjlxx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {saveTdjlJsonData: JSON.stringify(jlsqjlData)},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert(data.data, {icon: 1}, function (index) {
                        layer.close(index);
                        updateZwtjjlSftdById(zwData.id);
                    });
                } else {
                    layer.alert(data.message, {icon: 5});
                }
            },
            error: function () {
                layer.close(loadingIndex);
                layer.alert('失败...', {icon: 5});
            }
        });
    }

    function updateZwtjjlSftdById(id) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/zwtjjl/updateZwtjjlSftdById.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {id: id},
            success: function (data) {
                layer.close(loadingIndex);
                refreshZwtjjlTable();
            },
            error: function () {
                layer.close(loadingIndex);
                layer.alert('失败...', {icon: 5});
            }
        });
    }

    function delZwtjjl(id) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/zwtjjl//delZwtjjl.html', //数据接口
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
                    layer.alert('职位推荐记录删除成功！', {icon: 1}, function (index) {
                        layer.close(index);
                        refreshZwtjjlTable();
                    });
                } else {
                    layer.alert('职位信息删除失败，请稍后重试！', {icon: 5}, function (index) {
                        layer.close(index);
                        refreshZwtjjlTable();
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
    exports('zwtjjl_index', obj);
});