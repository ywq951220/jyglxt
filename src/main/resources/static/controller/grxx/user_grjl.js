layui.extend({
    // common: '{/}/jyglxt/src/main/resources/static/controller/common'
    common: '{/}/jyglxt/static/controller/common'
}).define(['table', 'form', 'laydate',  'common'], function (exports) {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var laydate = layui.laydate;
    var common = layui.common;

    var userId = parent.layui.grxx_index.grxxData.userId;
    var openId;

    //页面初始化加载
    function loadBase() {
        loadDate();

        var role = sessionStorage.getItem('role');
        if (role !== '1') {
            $('.user_grjl').attr('readonly', 'readonly');
            $('#my_footer_btn_div').hide();
        }

        queryGrjlByUserId();

        form.render();
    }

    function loadDate() {
        laydate.render({
            elem: '#grjl_csrq',
            type: 'date',
            theme: 'molv'
        });
    }

    function queryGrjlByUserId() {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/grjl/queryGrjlByUserId.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {userId: userId},
            success: function (data) {
                layer.close(loadingIndex);
                console.log(data);
                if (data.code === 200) {
                    var result = data.data;
                    if (result) {
                        for (var i in result) {
                            $('#grjl_' + i).val(result[i]);
                        }
                        $('input[name="sex"][value="'+ result.sex +'"]').prop('checked', true);
                    }
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

    function saveUserGrjl() {
        var grjlFormData = common.getFormJson($('#jyglxt_user_grjl_form'));
        var msg = formVerify(grjlFormData);
        if (msg) {
            layer.alert(msg, {icon: 5});
            return;
        }
        layer.alert('是否确认保存个人简历信息？', {
            skin: 'layui_layer_user_grjl_save',
            closeBtn: 0,
            btn: ['确认', '取消'],
            icon: 6,
            yes: function (index) {
                layer.close(index);
                saveGrjl(grjlFormData);
            }
        });
    }

    function resetUserGrjlForm() {
        $('#jyglxt_user_grjl_form')[0].reset();
        form.render();
    }

    //注册表单验证
    function formVerify(formData) {
        if (!formData.csrq) {
            return '请选择出生日期！';
        }
        if (!formData.lxdh) {
            return '请输入联系电话！';
        }
        if (!formData.sg) {
            return '请输入身高！';
        }
        if (!formData.mz) {
            return '请输入民族！';
        }
        if (!formData.zzmm) {
            return '请输入政治面貌！';
        }
        if (!formData.jtzz) {
            return '请输入家庭住址！';
        }
        if (!formData.dzyx) {
            return '请输入电子邮箱！';
        }
        if (!formData.byyx) {
            return '请输入毕业院校！';
        }
        if (!formData.zgxl) {
            return '请输入最高学历！';
        }
        if (!formData.yxzw) {
            return '请输入意向职位！';
        }
        if (!formData.sxzy) {
            return '请输入所学专业！';
        }
        if (!formData.zxkc) {
            return '请输入主修课程！';
        }
    }

    function saveGrjl(formData) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/grjl/saveGrjlXx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {grjlXxJsonData: JSON.stringify(formData)},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('个人简历保存成功！', {icon: 1}, function (index) {
                        layer.close(index);

                        //重置注册表单
                        resetUserGrjlForm();

                        //关闭iframe
                        //先得到当前iframe层的索引
                        openId = parent.layer.getFrameIndex(window.name);
                        //再执行关闭
                        parent.layer.close(openId);
                        //刷新父页面
                        parent.location.reload();
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

    //定义抛出的对象
    var obj = {
        load: loadBase,
        saveUserGrjl: saveUserGrjl,
        resetUserGrjlForm: resetUserGrjlForm
    };

    //抛出对象
    exports('user_grjl', obj);
});