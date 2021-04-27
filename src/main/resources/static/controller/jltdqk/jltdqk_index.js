layui.extend({
    // common: '{/}/jyglxt/src/main/resources/static/controller/common'
    common: '{/}/jyglxt/static/controller/common'
}).define(['table', 'form', 'laydate', 'common'], function (exports) {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var laydate = layui.laydate;
    var common = layui.common;

    var openId;

    //页面初始化加载
    function loadBase() {
        loadDate();

        loadRadioMonitor();

        var loginId = sessionStorage.getItem('id');
        var role = sessionStorage.getItem('role');

        loadUserJltdjl(loginId, role);

        form.render();
    }

    function loadRadioMonitor() {
        form.on('radio(spzt)', function (data) {
            var value = data.value;
            if (value === '1') {
                $('#sh_mssj_div').show();
                $('#sh_msdz_div').show();
            } else {
                $('#sh_mssj_div').hide();
                $('#sh_msdz_div').hide();

                $('#sh_mssj').val('');
                $('#sh_msdz').val('');
            }
        });
    }

    function loadDate() {
        laydate.render({
            elem: '#sh_mssj',
            type: 'datetime',
            theme: 'molv'
        });
    }

    //获取当前登录的用户信息
    function loadUserJltdjl(id, role) {
        table.render({
            elem: '#jyglxt_jltdqk_table', //指定原始table容器的选择器或DOM
            url: common.basePath + 'tb/jltdjl/queryJltdjlByUserId.html', //数据接口
            //请求头
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            toolbar: '#toolbar_jyglxt_jltdqk_table', //指定头工具栏
            page: true, //开启分页
            method: 'post', //请求类型
            where: {userId: id, sqr_role: role}, //接口请求的其他参数
            limit: 10, //每页显示的数量
            limits: [10, 20, 30, 40, 50, 60, 70, 80, 90],
            height: 500, //设定容器的高度
            text: {none: '对不起，暂无相关数据！'},
            cols: [[
                {field: 'sqrxm', title: '申请人姓名', width: '9%'},
                {field: 'sqdw', title: '申请单位', width: '11%'},
                {field: 'sqzw', title: '申请职位', width: '11%'},
                {
                    field: 'spzt', title: '审批状态', width: '8%', templet: function (data) {
                        var spzt = data.spzt;
                        var spztName = '';
                        if (spzt === '0') {
                            spztName = '未审核';
                        }
                        if (spzt === '1') {
                            spztName = '邀请面试';
                        }
                        if (spzt === '-1') {
                            spztName = '拒绝面试';
                        }
                        return spztName;
                    }
                },
                {field: 'spsj', title: '审批时间', width: '11%'},
                {
                    field: 'lyzt', title: '录用状态', width: '8%', templet: function (data) {
                        var lyzt = data.lyzt;
                        var lyztName = '';
                        if (lyzt === '0') {
                            lyztName = '未反馈';
                        }
                        if (lyzt === '1') {
                            lyztName = '已录用';
                        }
                        if (lyzt === '-1') {
                            lyztName = '拒绝录用';
                        }
                        return lyztName;
                    }
                },
                {field: 'spyj', title: '审批意见', width: '11%'},
                {
                    field: 'right', title: '操作', templet: function (data) {
                        console.log(data)
                        var role = sessionStorage.getItem('role');
                        var html = '';
                        if (role === '1') {
                            html += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>';
                        }
                        if (role === '2') {
                            html += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="grjl">简历信息</a>';
                            html += '<a class="layui-btn layui-btn-xs" lay-event="sh">审核</a>';
                            html += '<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="isly">反馈</a>';
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

        table.on('toolbar(jyglxt_jltdqk_table)', function (obj) {
            switch (obj.event) {
                case 'ref':
                    //table重载
                    refreshJltdjlTable();
                    break;
            }
        });

        //行工具栏
        table.on('tool(jyglxt_jltdqk_table)', function (obj) {
            var data = obj.data;
            console.log(data);
            layui.grxx_index.grxxData = data;
            switch (obj.event) {
                case 'delete':
                    if (data.spzt !== '0') {
                        layer.alert('对不起，只能对未审核的数据进行删除！', {icon: 1});
                        return;
                    }
                    layer.alert('确认删除该投递记录吗？', {
                        skin: 'layui_layer_del_jltdjl',
                        closeBtn: 0,
                        btn: ['确认', '取消'],
                        icon: 6,
                        yes: function (index) {
                            layer.close(index);
                            layer.prompt({
                                formType: 2,
                                title: '请输入删除原因！！！！！'
                            }, function (text, index) {
                                layer.close(index);
                                delJltdjl(data.id, text);
                            })
                        }
                    });
                    break;
                case 'grjl':
                    layer.open({
                        type: 2,
                        title: '个人简历',
                        id: 'layer_open_user_grjl',
                        area: ['90%', '80%'],
                        btnAlign: 'c',
                        // btn: ['保存', '关闭'],
                        // offset: '30px',
                        // shade: 0.1,
                        content: '../grxx/user_grjl.html'
                    });
                    break;
                case 'sh':
                    if (data.spzt !== '0') {
                        layer.alert('对不起，只能对未审核的数据进行审核！', {icon: 1});
                        return;
                    }
                    openId = layer.open({
                        type: 1,
                        title: '求职者简历审核',
                        id: 'layer_open_grjl_sh',
                        area: ['65%', '60%'],
                        btnAlign: 'c',
                        // btn: ['保存', '关闭'],
                        // offset: '30px',
                        // shade: 0.1,
                        content: $('#sh_qzz_tdxx'),
                        success: function (layero, index) {
                            $('#jltdjl_id').val(data.id);
                        }
                    });
                    return;
                case 'isly':
                    if (data.spzt !== '1') {
                        layer.alert('对不起，只能对邀请面试的数据进行录用反馈！', {icon: 1});
                        return;
                    }
                    if (data.lyzt !== '0') {
                        layer.alert('对不起，只能对未反馈的数据进行录用审批反馈！', {icon: 1});
                        return;
                    }
                    openId = layer.open({
                        type: 1,
                        title: '反馈信息',
                        id: 'layer_open_grjl_sh',
                        area: ['40%', '35%'],
                        btnAlign: 'c',
                        content: $('#is_lyzt_qzz_tdxx'),
                        success: function (layero, index) {
                            $('#is_lyzt_jltdjl_id').val(data.id);
                        }
                    });
                    break;
            }
        });
    }

    function refreshJltdjlTable() {
        table.reload('jyglxt_jltdqk_table', {
            where: {}
        });
    }

    function delJltdjl(jlId, zxyy) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/jltdjl/delJltdjlById.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                csglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {id: jlId, zxyy: zxyy},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('删除成功！', {icon: 1}, function (index) {
                        layer.close(index);
                        refreshJltdjlTable();
                    });
                }
            },
            error: function () {
                layer.close(loadingIndex);
            }
        });
    }

    function qrShBtn() {
        var shFormData = common.getFormJson($('#sh_qzz_tdxx_form'));

        var msg = shFformVerify(shFormData);
        if (msg) {
            layer.alert(msg, {icon: 5});
            return;
        }
        saveJlShxx(shFormData);
    }

    function shFformVerify(formData) {
        if (!formData.spzt) {
            return '请选择反馈结果！';
        } else {
            if (formData.spzt === '1') {
                if (!formData.mssj) {
                    return '请选择面试时间！';
                }
                if (!formData.msdz) {
                    return '请输入面试地址！';
                }
            }
        }
        if (!formData.spyj) {
            return '请输入审批意见！';
        }
    }

    function saveJlShxx(formData) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/jltdjl/saveJlshXx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {shDataJson: JSON.stringify(formData)},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('审核成功，结果将以邮件的形式告知求职者！', {icon: 1}, function (index) {
                        layer.close(index);

                        layer.closeAll();

                        refreshJltdjlTable();

                    });
                }
            },
            error: function () {
                layer.close(loadingIndex);
            }
        });
    }

    function qrLyBtn() {
        var fkFormData = common.getFormJson($('#is_lyzt_qzz_tdxx_form'));

        var msg = fkFformVerify(fkFormData);
        if (msg) {
            layer.alert(msg, {icon: 5});
            return;
        }
        saveJlFkxx(fkFormData);
    }

    function fkFformVerify(formData) {
        if (!formData.lyzt) {
            return '请选择录用结果！';
        }
    }

    function saveJlFkxx(formData) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/jltdjl/saveJlfkXx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {fkDataJson: JSON.stringify(formData)},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('反馈成功，结果将以邮件的形式告知求职者！', {icon: 1}, function (index) {
                        layer.close(index);

                        layer.closeAll();

                        refreshJltdjlTable();

                    });
                }
            },
            error: function () {
                layer.close(loadingIndex);
            }
        });
    }

    //定义抛出的对象
    var obj = {
        load: loadBase,
        qrShBtn: qrShBtn,
        qrLyBtn: qrLyBtn
    };

    //抛出对象
    exports('jltdqk_index', obj);
});