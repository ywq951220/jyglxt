layui.extend({
    // common: '{/}/jyglxt/src/main/resources/static/controller/common'
    common: '{/}/jyglxt/static/controller/common'
    // echarts: '{/}/jyglxt/static/controller/echarts.min'
}).define(['table', 'form', 'laydate', 'common'], function (exports) {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var laydate = layui.laydate;
    var common = layui.common;

    //页面初始化加载
    function loadBase() {
        //加载求职者就业情况数据
        queryQzzJyqkSjtj();

        //加载企业录用人数统计数据
        loadQylyrsSjtj();

        //加载系统用户统计数据
        loadXtyffxtjData();

    }

    /**
     * 加载求职者就业情况数据
     */
    function queryQzzJyqkSjtj() {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'sys/user/queryQzzJyqkSjtj.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    console.log(data);
                    var result =  data.data;
                    if (result.length > 0) {
                        var pieData = [];
                        for (var i = 0; i < result.length; i++) {
                            pieData.push({name: result[i].jyqk, value: result[i].jyqkzs});
                        }
                        queryBysjyqkByPie(pieData);
                    }
                } else {
                    layer.alert(data.message, {icon: 5});
                }
            },
            error: function () {
                layer.close(loadingIndex);
            }
        });
    }

    //求职者就业情况饼图加载
    function queryBysjyqkByPie(pieData) {
        var myChart = echarts.init(document.getElementById('sjtj_bysjyqk_tjfx_pie'));
        var option = {
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: '毕业生就业情况',
                    type: 'pie',
                    radius: '75%',
                    // data: [
                    //     {name: '已就业', value: 35},
                    //     {name: '未就业', value: 19}
                    // ],
                    data: pieData,
                    itemStyle: {
                        normal:{
                            color:function(params) {
                                //自定义颜色
                                var colorList = ['#FF8AD6', '#B18AFF'];
                                return colorList[params.dataIndex]
                            }
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
    }

    function loadQylyrsSjtj() {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'tb/zpdw/loadQylyrsSjtj.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    console.log(data);
                    var result =  data.data;
                    if (result.length > 0) {
                        var pieData = [];
                        for (var i = 0; i < result.length; i++) {
                            pieData.push({name: result[i].gsmc, value: result[i].lyzs});
                        }
                        queryQylyrsByPie(pieData);
                    }
                } else {
                    layer.alert(data.message, {icon: 5});
                }
            },
            error: function () {
                layer.close(loadingIndex);
            }
        });
    }

    function queryQylyrsByPie(pieData) {
        var myChart = echarts.init(document.getElementById('sjtj_qylyrs_pie'));
        var option = {
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: '企业录用人数',
                    type: 'pie',
                    radius: ['45%', '70%'],
                    center: ['50%', '55%'],
                    // data: [
                    //     {name: '已就业', value: 35},
                    //     {name: '未就业', value: 19}
                    // ],
                    data: pieData,
                    itemStyle: {
                        normal:{
                            color:function(params) {
                                //自定义颜色
                                var colorList = ['#FF8AD6', '#DF8AFF', '#B18AFF', '#9A8AFF'];
                                return colorList[params.dataIndex]
                            }
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
    }

    function loadXtyffxtjData() {
        var loadingIndex = layer.load(0, {
            shade: [0.5, '#000000']
        });
        $.ajax({
            url: common.basePath + 'sys/user/queryXtyhfxSjtj.html', //数据接口
            headers: {
                Accept: 'application/json;charset=utf-8',
                jyglxt: sessionStorage.getItem('token')
            },
            type: 'post',
            dataType: 'json',
            data: {},
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    console.log(data);
                    var result =  data.data;
                    if (result.length > 0) {
                        var zztXData = [];
                        var zztYData = [];
                        for (var i = 0; i < result.length; i++) {
                            zztXData.push(result[i].roleName);
                            zztYData.push(result[i].userzs);
                        }
                        queryXtyhfxtjByZzt(zztXData, zztYData);
                    }
                } else {
                    layer.alert(data.message, {icon: 5});
                }
            },
            error: function () {
                layer.close(loadingIndex);
            }
        });
    }

    //求职者就业情况饼图加载
    function queryXtyhfxtjByZzt(zztXData, zztYData) {
        var myChart = echarts.init(document.getElementById('sjtj_xtyhfxtj_zzt'));
        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    // data: ['毕业生', '企业HR', '学校负责人', '系统管理员'],
                    data: zztXData
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    minInterval: 1
                }
            ],
            series: [
                {
                    name: '用户总数',
                    type: 'bar',
                    barWidth: '30',
                    // data: [10, 5, 2, 1],
                    data: zztYData,
                    itemStyle: {
                        color: function(params) {
                            var colorList = [
                                ['#FF8AD6', '#BA61FF'],
                                ['#FF8AFB', '#9161FF'],
                                ['#DF8AFF', '#616CFF'],
                                ['#B18AFF', '#61C9FF'],
                                ['#9A8AFF', '#61F9FF']

                            ];
                            var index = params.dataIndex;
                            if (params.dataIndex >= colorList.length) {
                                index = params.dataIndex - colorList.length
                            }

                            return new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                { offset: 0, color: colorList[index][0] },
                                { offset: 1, color: colorList[index][1] }
                            ])
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
    }

    //定义抛出的对象
    var obj = {
        load: loadBase
    };

    //抛出对象
    exports('sjtj_index', obj);
});