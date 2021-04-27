
layui.define(function (exports) {
    var $ = layui.$
        , layer = layui.layer
        , laytpl = layui.laytpl
        , setter = layui.setter
        , view = layui.view
        , admin = layui.admin;

    var mode = 'local';
    //默认学习
    var basePath = '';
    switch (mode) {
        case 'local':
            basePath = 'http://localhost:8888/jyglxt/';
            break;
    }

    var token = sessionStorage.getItem('token');

    var getFormJson = function (form) {
        var o = {};
        var a = $(form).serializeArray();
        $.each(a, function () {
            if (o[this.name] != undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    var obj = {
        basePath: basePath,
        token: token,
        getFormJson: getFormJson,
    };
    //对外暴露的接口
    exports('common', obj);
});