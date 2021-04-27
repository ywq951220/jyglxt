layui.extend({
    // common: '{/}/jyglxt/src/main/resources/static/controller/common'
    common: '{/}/jyglxt/static/controller/common'
}).define(['table', 'form', 'laydate', 'common'], function (exports) {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var laydate = layui.laydate;
    var common = layui.common;

    var zwxxData = parent.layui.zpxx_index.zwxxData;

    var openId;

    //页面初始化加载
    function loadBase() {
        if (sessionStorage.getItem('role') !== '2') {
            $('.user_zwxx').attr('readonly', 'readonly');
            $('#save_zwxx_btn_div').hide();
        }

        if (zwxxData) {
            for (var i in zwxxData) {
                $('#user_zwxx_' + i).val(zwxxData[i]);
            }
        }
    }

    function saveZwxx() {
        var zwxxFormData = common.getFormJson($('#add_user_zwxx_form'));
        var msg = formVerify(zwxxFormData);
        if (msg) {
            layer.alert(msg, {icon: 5});
            return;
        }
        layer.alert('是否确认保存？', {
            skin: 'layui_layer_user_zc_save',
            closeBtn: 0,
            btn: ['确认', '取消'],
            icon: 6,
            yes: function (index) {
                layer.close(index);
                saveUserZwxx(zwxxFormData);
            }
        });
    }

    //注册表单验证
    function formVerify(formData) {
        if (!formData.zwmc) {
            return '请输入职位名称！';
        }
        if (!formData.zwzz) {
            return '请职位职责！';
        }
        if (!formData.zwxlyq) {
            return '请输入职位学历要求！';
        }
        if (!formData.sbdz) {
            return '请输入上班地址！';
        }
        if (!formData.gzjyyq) {
            return '请输入工作经验要求！';
        }
    }

    function saveUserZwxx(formData) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/zpdw/zw/saveZwxx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {zwxxJsonData: JSON.stringify(formData)},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('在聘岗位信息保存成功！', {icon: 1}, function (index) {
                        layer.close(index);

                        //重置注册表单
                        resetZwxxForm();

                        //关闭当前层
                        //先得到当前iframe层的索引
                        openId = parent.layer.getFrameIndex(window.name);
                        //执行关闭
                        parent.layer.close(openId);
                        //刷新父页面
                        parent.layui.zpxx_index.refreshZwxxTable();
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

    function resetZwxxForm() {
        $('#add_user_zwxx_form')[0].reset();
        form.render();
    }

    //定义抛出的对象
    var obj = {
        load: loadBase,
        saveZwxx: saveZwxx,
        resetZwxxForm: resetZwxxForm
    };

    //抛出对象
    exports('add_zwxx', obj);
});