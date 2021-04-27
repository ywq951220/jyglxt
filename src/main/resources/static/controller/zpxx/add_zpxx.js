layui.extend({
    // common: '{/}/jyglxt/src/main/resources/static/controller/common'
    common: '{/}/jyglxt/static/controller/common'
}).define(['table', 'form', 'laydate', 'common'], function (exports) {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var laydate = layui.laydate;
    var common = layui.common;

    var zpxxData = parent.layui.zpxx_index.zpxxData;

    var openId;

    //页面初始化加载
    function loadBase() {
        loadDate();

        var role = sessionStorage.getItem('role');
        if (role !== '2') {
            $('.add_zpxx').attr('readonly', 'readonly');
            $('#save_zpxx_btn_div').css('display', 'none');
        }

        if (zpxxData) {
            for (var i in zpxxData) {
                $('#user_zpxx_' + i).val(zpxxData[i]);
            }
        }
        $('#user_id').val(sessionStorage.getItem('id'));
    }

    function loadDate() {
        laydate.render({
            elem: '#user_zpxx_clsj',
            type: 'date',
            theme: 'molv'
        });
    }

    function saveZpxx() {
        var zpxxFormData = common.getFormJson($('#add_user_zpxx_form'));
        var msg = formVerify(zpxxFormData);
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
                saveUserZpxx(zpxxFormData);
            }
        });
    }

    //注册表单验证
    function formVerify(formData) {
        if (!formData.gsmc) {
            return '请输入公司名称！';
        }
        if (!formData.clsj) {
            return '请选择成立时间！';
        }
        if (!formData.frdb) {
            return '请输入法人姓名！';
        }
        if (!formData.zjdh) {
            return '座机电话！';
        }
        if (!formData.gsdz) {
            return '公司地址！';
        }
    }

    function saveUserZpxx(formData) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/zpdw/savezpdwXx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {zpxxJsonData: JSON.stringify(formData)},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.alert('单位信息保存成功！', {icon: 1}, function (index) {
                        layer.close(index);

                        //重置注册表单
                        resetZpxxForm();

                        //关闭当前层
                        //先得到当前iframe层的索引
                        openId = parent.layer.getFrameIndex(window.name);
                        //执行关闭
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

    function resetZpxxForm() {
        $('#add_user_zpxx_form')[0].reset();
        form.render();
    }

    //定义抛出的对象
    var obj = {
        load: loadBase,
        saveZpxx: saveZpxx,
        resetZpxxForm: resetZpxxForm
    };

    //抛出对象
    exports('add_zpxx', obj);
});