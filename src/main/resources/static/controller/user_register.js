layui.define(['table', 'form', 'common'], function (exports) {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var common = layui.common;

    var grxxData;
    var openId;

    //页面初始化加载
    function loadBase() {
        if (parent.layui.grxx_index.grxxData) {
            grxxData = parent.layui.grxx_index.grxxData;
            if (grxxData) {
                for (var i in grxxData) {
                    $('#user_' + i).val(grxxData[i]);
                }
                $('input[name="sex"][value="'+ grxxData.sex +'"]').prop('checked', true);

                $('#user_userName').attr('readonly', 'readonly');
                $('#user_password').attr('readonly', 'readonly');
                $('#user_role').attr('disabled', 'disabled');
            }
        }
        form.render();
    }

    //用户注册账号提交按钮
    function saveUserReg() {
        // var zcFormData = form.val('jyglxt_user_zc_form');
        var zcFormData = common.getFormJson($('#jyglxt_user_zc_form'));
        if (!zcFormData.sex) {
            var xb = $("input[name='sex']:checked").val();
            zcFormData.sex = xb;
        }
        var token = sessionStorage.getItem('token');
        if (token) {
            zcFormData.role = sessionStorage.getItem('role');
        }

        var msg = formVerify(zcFormData);
        if (msg) {
            layer.alert(msg, {icon: 5});
            return;
        }
        var title = '';
        if (zcFormData.id) {
            title = '是否确认修改该账号信息？';
        } else {
            title = '是否确认注册该账号？';
        }
        layer.alert(title, {
            skin: 'layui_layer_user_zc_save',
            closeBtn: 0,
            btn: ['确认', '取消'],
            icon: 6,
            yes: function (index) {
                layer.close(index);
                saveUser(zcFormData);
            }
        });
    }

    //保存用户注册信息
    function saveUser(zcFormData) {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'sys/user/saveYhxx.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {userDataJson: JSON.stringify(zcFormData)},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    var title = '';
                    if (zcFormData.id) {
                        title = '修改成功！';
                    } else {
                        title = '注册成功！';
                    }
                    layer.alert(title, {icon: 1}, function (index) {
                        layer.close(index);

                        //重置注册表单
                        resetUserRegForm();

                        //关闭当前层
                        //先得到当前iframe层的索引
                        openId = parent.layer.getFrameIndex(window.name);
                        //执行关闭
                        parent.layer.close(openId);
                        //刷新父页面
                        parent.location.reload();
                    });
                }
            },
            error: function () {
                layer.close(loadingIndex);
            }
        });
    }

    //用户注册表单重置按钮
    function resetUserRegForm() {
        $('#jyglxt_user_zc_form')[0].reset();
        form.render();
    }

    //注册表单验证
    function formVerify(formData) {
        if (!formData.name) {
            return '请输入用户姓名！';
        }
        if (!formData.sex) {
            return '请选择用户性别！';
        }
        if (!formData.sfz) {
            return '请输入用户身份证号！';
        } else {
            if (formData.sfz.length !== 18) {
                return '请输入18位正确的身份证号！';
            }
        }
        if (!formData.lxdh) {
            return '请输入用户联系电话！';
        }
        if (!formData.userName) {
            return '请输入登录用户名！';
        }
        if (!formData.password) {
            return '请输入登录用户密码！';
        }if (!formData.role) {
            return '请选择用户角色！';
        }
    }

    //定义抛出的对象
    var obj = {
        load: loadBase,
        saveUserReg: saveUserReg,
        resetUserRegForm: resetUserRegForm
    };

    //抛出对象
    exports('user_register', obj);
});