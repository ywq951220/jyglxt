var common;
var openId;

layui.extend({
    setter: 'config' //配置模块
    , admin: 'lib/admin' //核心模块
    , view: 'lib/view' //视图渲染模块
}).define(['setter', 'admin'], function (exports) {
    var setter = layui.setter
        , element = layui.element
        , admin = layui.admin
        , tabsPage = admin.tabsPage
        , view = layui.view

        //打开标签页
        , openTabsPage = function (url, text) {
            //遍历页签选项卡
            var matchTo
                , tabs = $('#LAY_app_tabsheader>li')
                , path = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, '');

            tabs.each(function (index) {
                var li = $(this)
                    , layid = li.attr('lay-id');

                if (layid === url) {
                    matchTo = true;
                    tabsPage.index = index;
                }
            });

            text = text || '新标签页';

            if (setter.pageTabs) {
                //如果未在选项卡中匹配到，则追加选项卡
                if (!matchTo) {
                    $(APP_BODY).append([
                        '<div class="layadmin-tabsbody-item layui-show">'
                        , '<iframe src="' + url + '" frameborder="0" class="layadmin-iframe"></iframe>'
                        , '</div>'
                    ].join(''));
                    tabsPage.index = tabs.length;
                    element.tabAdd(FILTER_TAB_TBAS, {
                        title: '<span>' + text + '</span>'
                        , id: url
                        , attr: path
                    });
                }
            } else {
                var iframe = admin.tabsBody(admin.tabsPage.index).find('.layadmin-iframe');
                iframe[0].contentWindow.location.href = url;
            }

            //定位当前tabs
            element.tabChange(FILTER_TAB_TBAS, url);
            admin.tabsBodyChange(tabsPage.index, {
                url: url
                , text: text
            });
        }

        , APP_BODY = '#LAY_app_body', FILTER_TAB_TBAS = 'layadmin-layout-tabs'
        , $ = layui.$, $win = $(window);

    //初始
    if (admin.screen() < 2) admin.sideFlexible();

    //将模块根路径设置为 controller 目录
    layui.config({
        base: setter.base
    });

    //扩展 lib 目录下的其它模块
    layui.each(setter.extend, function (index, item) {
        var mods = {};
        mods[item] = '{/}' + setter.base + 'lib/extend/' + item;
        layui.extend(mods);
    });

    view().autoRender();

    //加载公共模块
    layui.use('common', function () {
        common = layui.common;
        // sessionStorage.setItem('token', common.getToken());
    });

    function loadBase() {
        //加载首页时先判断是否登录
        var token = sessionStorage.getItem('token');
        if (!token) {
            window.location.href = '../templates/login.html';
            return;
        }

        $('#headerUserName').html(sessionStorage.getItem('name'));
        var role = sessionStorage.getItem('role');
        if (role === '1') {
            $('#headerUserRoleName').html('（求职者）');

            //如果角色为1，则显示招聘信息、职位推荐记录和简历投递情况
            $('#jyglxt_index_zpxx').show();
            $('#jyglxt_index_zwtjjl').show();
            $('#jyglxt_index_jltdqk').show();
        }
        if (role === '2') {
            $('#headerUserRoleName').html('（企业HR）');

            //如果角色为2，则显示招聘信息和简历投递记录
            $('#jyglxt_index_zpxx').show();
            $('#jyglxt_index_jltdqk').show();
        }
        if (role === '3') {
            $('#headerUserRoleName').html('（学校就业负责人）');

            //如果角色为3，则显示招聘信息、职位推荐记录、学生就业情况和学生简历
            $('#jyglxt_index_zpxx').show();
            $('#jyglxt_index_zwtjjl').show();
            $('#jyglxt_index_xsjyqk').show();
            $('#jyglxt_index_xsjl').show();
        }

        if (role === '4') {
            $('#headerUserRoleName').html('（系统管理员）');

            //如果角色为4，则显示招聘信息和用户管理
            $('#jyglxt_index_zpxx').show();
            $('#jyglxt_index_yhgl').show();
        }

        //拼接日期
        var myDateTime = new Date();
        var year = myDateTime.getFullYear();
        var month = myDateTime.getMonth() + 1;
        var date = myDateTime.getDate();
        var week = myDateTime.getDay();
        switch (week) {
            case 1:
                week = '星期一';
                break;
            case 2:
                week = '星期二';
                break;
            case 3:
                week = '星期三';
                break;
            case 4:
                week = '星期四';
                break;
            case 5:
                week = '星期五';
                break;
            case 6:
                week = '星期六';
                break;
            case 0:
                week = '星期日';
                break;
        }
        $('#time').html(year + '年' + month + '月'+ date + '日&nbsp;&nbsp;&nbsp;&nbsp;' + week)
    }

    function loginOut() {
        $.ajax({
            url: common.basePath + 'index/logout', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            async: true,
            data: {},
            success: function (data) {
                if (data.code === 200) {
                    layer.alert('是否确认退出【'+ sessionStorage.getItem('name') +'】的账号?', {
                        skin: 'layui-layer_yhgl_logout',
                        icon: 6,
                        btn: ['确认', '取消'],
                        closeBtn: 0,
                        yes: function (index) {
                            sessionStorage.setItem('token', '');
                            layer.close(index);
                            window.location.href = '../templates/login.html';
                        }
                    })
                }
            },
            error: function () {
                layer.alert('退出失败！');
            }
        });
    }

    //确认修改按按钮
    function qrBtn() {
        var formData = common.getFormJson($('#update_password_form'));
        var oldPassword = formData.password;
        if (!oldPassword) {
            layer.alert('请输入原始密码！', {icon: 5});
            return;
        }
        var sessionPassword = sessionStorage.getItem('password');
        if (oldPassword !== sessionPassword) {
            layer.alert('原始密码有误，请确认无误后重新修改！', {icon: 5});
            return;
        }
        var newPassword = formData.newPassword;
        var qrNewPassword = formData.qrNewPassword;
        if (!newPassword) {
            layer.alert('请输入新密码！', {icon: 5});
            return;
        }
        if (!qrNewPassword) {
            layer.alert('请确认新密码！', {icon: 5});
            return;
        }
        if (newPassword !== qrNewPassword) {
            layer.alert('两次密码输入不同，请重新输入！', {icon: 5});
            return;
        }
        if (oldPassword === newPassword) {
            layer.alert('原密码和新密码相同！', {icon: 5});
            return;
        }
        var id = sessionStorage.getItem('id');
        $.ajax({
            url: common.basePath + 'sys/user/updatePassword.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: common.token
            },
            type: 'post',
            dataType: 'json',
            async: true,
            data: {id: id, newPassword: newPassword},
            success: function (data) {
                if (data.code === 200) {
                    layer.close(openId);
                    layer.alert('密码修改成功，请重新登录！', function (index) {
                        sessionStorage.setItem('token', '');
                        layer.close(index);
                        window.location.href = '../templates/login.html';
                    })
                }
            }
        });
    }

    //对外输出
    exports('index', {
        openTabsPage: openTabsPage,
        load: loadBase,
        loginOut: loginOut,
        qrBtn: qrBtn
    });
});
