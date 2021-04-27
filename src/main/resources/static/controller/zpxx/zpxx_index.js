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
        var userId = sessionStorage.getItem('id');
        var role = sessionStorage.getItem('role');

        //获取当前登录用户的招聘单位信息
        loadUserZpdw(userId, role);

        form.render();
    }

    //获取当前登录用户的招聘单位信息
    function loadUserZpdw(userId, role) {
        table.render({
            elem: '#jyglxt_zpxx_table', //指定原始table容器的选择器或DOM
            url: common.basePath + 'tb/zpdw/getZpdwList.html', //数据接口
            //请求头
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            toolbar: '#toolbar_jyglxt_zpxx_table', //指定头工具栏
            page: true, //开启分页
            method: 'post', //请求类型
            where: {userId: userId, role: role}, //接口请求的其他参数
            limit: 10, //每页显示的数量
            limits: [10, 20, 30, 40, 50, 60, 70, 80, 90],
            height: 500, //设定容器的高度
            text: {none: '对不起，暂无相关数据！'},
            cols: [[
                {field: 'gsmc', title: '公司名称', width: '12%'},
                {field: 'clsj', title: '成立时间', width: '12%'},
                {field: 'frdb', title: '法人代表', width: '12%'},
                {field: 'gsdz', title: '公司地址', width: '12%'},
                {field: 'zjdh', title: '座机电话', width: '12%'},
                {
                    field: 'right', title: '操作', templet: function (data) {
                        var role = sessionStorage.getItem('role');
                        var html = '<a class="layui-btn layui-btn-xs" lay-event="edit">公司信息</a>';
                        html += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="zwxx">职位信息</a>';
                        if (role === '2') {
                            html += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>';
                        }
                        if (role === '4') {
                            html += '<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="dwsh">审核</a>';
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

        table.on('toolbar(jyglxt_zpxx_table)', function (obj) {
            var data = obj.data;
            console.log(data);
            layui.zpxx_index.zpxxData = data;
            switch (obj.event) {
                case 'add':
                    if (sessionStorage.getItem('role') !== '2') {
                        layer.alert('对不起，您暂无此操作权限！', {icon: 5});
                        return;
                    }
                    layer.open({
                        type: 2,
                        title: '公司信息',
                        id: 'layer_open_add_zpxx',
                        area: ['90%', '80%'],
                        btnAlign: 'c',
                        content: '../zpxx/add_zpxx.html',
                        //层弹出后的成功回调方法
                        success: function (layero, index) {
                            // var iframe = window[layero.find('iframe')[0]['name']];
                            // console.log(iframe);
                        }
                    });
                    break;
                case 'ref':
                    //table重载
                    refreshZpdwTable();
                    break;
            }
        });

        //行工具栏
        table.on('tool(jyglxt_zpxx_table)', function (obj) {
            var data = obj.data;
            console.log(data);
            layui.zpxx_index.zpxxData = data;
            switch (obj.event) {
                case 'edit':
                    layer.open({
                        type: 2,
                        title: '公司信息',
                        id: 'layer_open_user_zc',
                        area: ['60%', '60%'],
                        btnAlign: 'c',
                        // btn: ['保存', '关闭'],
                        // offset: '30px',
                        // shade: 0.1,
                        content: '../zpxx/add_zpxx.html'
                    });
                    break;
                case 'zwxx':
                    layer.open({
                        type: 1,
                        title: '公司职位信息',
                        id: 'layer_open_zpxx_zwxx_table',
                        area: ['90%', '80%'],
                        btnAlign: 'c',
                        // btn: ['保存', '关闭'],
                        // offset: '30px',
                        // shade: 0.1,
                        content: $('#add_zpxx_zwxx'),
                        //层弹出后的成功回调方法
                        success: function (layero, index) {
                            queryZwxxByDwId(data.id);
                        }
                    });
                    break;
                case 'delete':
                    if (sessionStorage.getItem('role') !== '2') {
                        layer.alert('对不起，您暂无此操作权限！', {icon: 5});
                        return;
                    }
                    layer.alert('是否确认删除该公司信息，删除后将同步删除该公司下的所有 在聘职位信息！', {
                        skin: 'layui_layer_zpdw_del',
                        icon: 6,
                        btn: ['确认', '取消'],
                        closeBtn: 0,
                        yes: function (index) {
                            layer.close(index);
                            delZpdwXx(data.id);
                        }
                    });
                    break;
                case 'dwsh':
                    if (sessionStorage.getItem('role') !== '4') {
                        layer.alert('对不起，您暂无此操作权限！', {icon: 5});
                        return;
                    }
                    dwshInfo(data);
                    break;
            }
        });
    }

    function refreshZpdwTable() {
        table.reload('jyglxt_zpxx_table', {
            where: {}
        });
    }

    function delZpdwXx(dwId) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/zpdw/delZpdwXx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {id: dwId},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('单位信息删除成功！', {icon: 1}, function (index) {
                        layer.close(index);

                        refreshZpdwTable();
                    });
                } else {
                    layer.alert('单位信息删除失败，请稍后重试！', {icon: 5}, function (index) {
                        layer.close(index);
                        refreshZpdwTable();
                    });
                }
            },
            error: function () {
                layer.close(loadingIndex);
                layer.alert('失败...', {icon: 5});
            }
        });
    }

    function dwshInfo(dwData) {
        var html = '';
        html += '<form id="ddgl_audit_form" class="layui-form layui-form-pane" style="padding: 10px;">';
        html += '   <div class="layui-form-item">';
        html += '       <label class="layui-form-label">审核状态</label>';
        html += '       <div class="layui-input-block" style="border-color: rgb(230, 230, 230); border-width: 1px;border-style: solid;border-radius: 2px 0 0 2px;">';
        html += '           <input type="radio" name="shzt" value="1" title="通过"/>';
        html += '           <input type="radio" name="shzt" value="0" title="不通过"/>';
        html += '       </div>';
        html += '   </div>';
        html += '   <div class="layui-form-item">';
        html += '       <label class="layui-form-label" id="audit_label">审核意见</label>';
        html += '       <div class="layui-input-block" id="audit_block">';
        html += '           <textarea id="shyj" class="layui-textarea" name="shyj" placeholder="请输入审核意见说明..."></textarea>';
        html += '       </div>';
        html += '   </div>';
        html += '</form>';
        layer.open({
            title: '请填写审核情况',
            type: 1,
            area: ['50%', '55%'],
            content: html,
            btn: ['确认', '取消'],
            btnAlign: 'c',
            yes: function (index) {
                var dwshData = {};
                var shzt = $("input[name='shzt']:checked").val();
                if (!shzt) {
                    layer.alert('请选择审核状态！', {icon: 2});
                    return;
                }
                var shyj = $('#shyj').val();
                if (!shyj) {
                    layer.alert('请输入审核意见！', {icon: 2});
                    return;
                }
                var id = dwData.id;
                dwshData.id = id;
                dwshData.shzt = shzt;
                dwshData.shyj = shyj;
                layer.alert('确认对【'+ dwData.gsmc +'】进行审核吗？', {icon: 1}, function (index) {
                    layer.close(index);
                    var loadingIndex = layer.load(0, {
                        shade: [0.5, '#000000']
                    });
                    $.ajax({
                        url: common.basePath + 'tb/zpdw/dwsh.html', //数据接口
                        headers: {
                            Accept: 'application/json;charset=utf-8',
                            csglxt: sessionStorage.getItem('token')
                        },
                        type: 'post',
                        dataType: 'json',
                        data: {dwshData: JSON.stringify(dwshData)},
                        success: function (data) {
                            layer.close(loadingIndex);
                            if (data.code === 200) {
                                layer.alert('审核成功！', {icon: 1}, function (index) {
                                    layer.closeAll();
                                    //刷新当前页面
                                    location.reload();
                                });
                            }
                        },
                        error: function () {
                            layer.close(loadingIndex);


                        }
                    });
                });
            }
        });

        //初始化就绪
        layer.ready(function () {
            //设置审核页审核意见框的高度
            var blockHeight = $('#audit_block').css('height');
            $('#audit_label').css('height', blockHeight);
            form.render();
        });
    }

    function queryZwxxByDwId(dwId) {
        table.render({
            elem: '#add_zpxx_zwxx_table', //指定原始table容器的选择器或DOM
            url: common.basePath + 'tb/zpdw/zw/queryZwxxByDwId.html', //数据接口
            //请求头
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            toolbar: '#toolbar_add_zpxx_zwxx_table', //指定头工具栏
            page: true, //开启分页
            method: 'post', //请求类型
            where: {dwId: dwId}, //接口请求的其他参数
            limit: 10, //每页显示的数量
            limits: [10, 20, 30, 40, 50, 60, 70, 80, 90],
            height: 500, //设定容器的高度
            text: {none: '对不起，暂无相关数据！'},
            cols: [[
                {field: 'zwmc', title: '职位名称', width: '12%'},
                {field: 'zwzz', title: '职位职责', width: '12%'},
                {field: 'zwxlyq', title: '学历要求', width: '12%'},
                {field: 'gzjyyq', title: '经验要求', width: '12%'},
                {field: 'sbdz', title: '上班地址', width: '12%'},
                {
                    field: 'right', title: '操作', templet: function (data) {
                        var role = sessionStorage.getItem('role');
                        var html = '';
                        if (role === '1') {
                            html += '<a class="layui-btn layui-btn-xs" lay-event="tdjl">投递简历</a>';
                            html += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">详情</a>';
                        } else if (role === '2') {
                            html += '<a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>';
                            html += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>';
                        } else if (role === '3') {
                            html += '<a class="layui-btn layui-btn-xs" lay-event="zwtj">职位推荐</a>';
                            html += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">详情</a>';
                        } else {
                            html += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">详情</a>';
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

        table.on('toolbar(add_zpxx_zwxx_table)', function (obj) {
            var data = {};
            console.log(obj);
            data.dwId = dwId;
            layui.zpxx_index.zwxxData = data;
            switch (obj.event) {
                case 'zwAdd':
                    if (sessionStorage.getItem('role') !== '2') {
                        layer.alert('对不起，您暂无此操作权限！', {icon: 5});
                        return;
                    }
                    layer.open({
                        type: 2,
                        title: '公司职位信息',
                        id: 'layer_open_add_zpxx',
                        area: ['90%', '80%'],
                        btnAlign: 'c',
                        content: '../zpxx/add_zwxx.html',
                        //层弹出后的成功回调方法
                        success: function (layero, index) {
                            // var iframe = window[layero.find('iframe')[0]['name']];
                            // console.log(iframe);
                        }
                    });
                    break;
                case 'ref':
                    //table重载
                    refreshZwxxTable();
                    break;
            }
        });

        //行工具栏
        table.on('tool(add_zpxx_zwxx_table)', function (obj) {
            var data = obj.data;
            console.log(data);
            layui.zpxx_index.zwxxData = data;
            switch (obj.event) {
                case 'tdjl':
                    if (sessionStorage.getItem('role') !== '1') {
                        layer.alert('对不起，您暂无此操作权限！', {icon: 5});
                        return;
                    }
                    var userId = sessionStorage.getItem('id');
                    hasJltdByUserId(data, userId);
                    break;
                case 'zwtj':
                    if (sessionStorage.getItem('role') !== '3') {
                        layer.alert('对不起，您暂无此操作权限！', {icon: 5});
                        return;
                    }
                    layui.zpxx_index.zwyjModal = layer.open({
                        type: 1,
                        title: '职位推荐',
                        id: 'layer_open_zwtj',
                        area: ['70%', '60%'],
                        btnAlign: 'c',
                        // btn: ['保存', '关闭'],
                        // offset: '30px',
                        // shade: 0.1,
                        content: $('#zpxx_zpzw_tj'),
                        success: function (layero, index) {
                            $('#zwtj_dw_id').val(data.dwId);
                            $('#zwtj_zw_id').val(data.id);
                            queryQzzUserList(data.id);
                        }
                    });
                    // openZwtjModal();
                    break;
                case 'edit':
                    layer.open({
                        type: 2,
                        title: '公司职位信息',
                        id: 'layer_open_zpdw_zpzw',
                        area: ['90%', '80%'],
                        btnAlign: 'c',
                        // btn: ['保存', '关闭'],
                        // offset: '30px',
                        // shade: 0.1,
                        content: '../zpxx/add_zwxx.html'
                    });
                    break;
                case 'delete':
                    if (sessionStorage.getItem('role') !== '2') {
                        layer.alert('对不起，您暂无此操作权限！', {icon: 5});
                        return;
                    }
                    layer.alert('确认删除该公司的职位信息吗？', {
                        skin: 'layui-layer_yhgl_logout',
                        icon: 6,
                        btn: ['确认', '取消'],
                        closeBtn: 0,
                        yes: function (index) {
                            layer.close(index);
                            delZwXx(data.id);
                        }
                    });
                    break;
            }
        });
    }

    function refreshZwxxTable() {
        table.reload('add_zpxx_zwxx_table', {
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
                        layer.alert('是否确认申请该公司的【'+ data.zwmc +'】职位？', {
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
        jlsqjlData.zwId = zwData.id;
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
                        refreshZwxxTable();
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

    function queryQzzUserList(zwId) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'sys/user/queryQzzUserList.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {role: '1', zwId: zwId},
            success: function (data) {
                layer.close(loadingIndex);
                var result = data.data;
                if (data.code === 200) {
                    var optionArr = [];
                    if (result.length > 0) {
                        for (var i in result) {
                            optionArr.push({
                                name: result[i].name,
                                value: result[i].id
                            });
                        }
                        layui.formSelects.data('user_ids', 'local', {
                            arr: optionArr
                        });
                        form.render('select');
                    }
                }
            },
            error: function () {
                layer.close(loadingIndex);
                layer.alert('失败...', {icon: 5});
            }
        });
    }

    function saveZwtjBtn() {
        var selectsName = layui.formSelects.value('user_ids', 'val');
        var selectedStr = layui.formSelects.value('user_ids', 'valStr');
        var zwtjjl = {};
        if (!selectedStr) {
            layer.alert('请选择需要推向的人员！', {icon: 5});
            return;
        }
        zwtjjl.txUserId = selectedStr;

        var dwId = $('#zwtj_dw_id').val();
        if (!dwId) {
            layer.alert('获取的单位主键为空，请刷新页面重试！', {icon: 5});
            return;
        }
        zwtjjl.dwId = dwId;

        var zwId = $('#zwtj_zw_id').val();
        if (!zwId) {
            layer.alert('获取的职位主键为空，请刷新页面重试！', {icon: 5});
            return;
        }
        zwtjjl.zwId = zwId;

        var tjUserId = sessionStorage.getItem('id');
        zwtjjl.tjUserId = tjUserId;

        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/zwtjjl/saveZwtjjl.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {zwtjjlJsonData: JSON.stringify(zwtjjl)},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('职位推荐成功！', {icon: 1}, function (index) {
                        layer.close(index);

                        layer.close(layui.zpxx_index.zwyjModal);
                        refreshZwxxTable();
                    });
                } else {
                    layer.alert('职位推荐失败，请稍后重试！', {icon: 5}, function (index) {
                        layer.close(index);
                        refreshZwxxTable();
                    });
                }
            },
            error: function () {
                layer.close(loadingIndex);
                layer.alert('失败...', {icon: 5});
            }
        });
    }

    function delZwXx(zwId) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/zpdw/zw/delZwXx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {id: zwId},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('职位信息删除成功！', {icon: 1}, function (index) {
                        layer.close(index);
                        refreshZwxxTable();
                    });
                } else {
                    layer.alert('职位信息删除失败，请稍后重试！', {icon: 5}, function (index) {
                        layer.close(index);
                        refreshZwxxTable();
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
        load: loadBase,
        refreshZwxxTable: refreshZwxxTable,
        saveZwtjBtn: saveZwtjBtn
    };

    //抛出对象
    exports('zpxx_index', obj);
});