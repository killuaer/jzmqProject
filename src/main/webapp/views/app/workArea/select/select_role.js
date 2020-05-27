var table;
window.app.selectRole = function() {
    layui.use(['layer', 'jquery', 'table'], function() {
        table = layui.table;
        table.render({
            elem: '#selectTable',
            height: 400,
            url: '../role/listRole',
            even: true, //开启隔行背景
            size: 'sm', //小尺寸的表格
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                groups: 1, //只显示 1 个连续页码
                first: false, //不显示首页
                last: false, //不显示尾页
                theme: '#008aec'
            },
            cols: [
                [
                    { unresize: true,width : '4%', checkbox: true},
                    { unresize: true,width : '6%',templet: function (a) {return a.LAY_INDEX},align: 'center',title : '序号'},
                    { unresize: true,field: 'name',width:'88%',title: '角色名称',align: 'center'}
                ]
            ]
        });
    });

    this.queryList  = function() {
        var name = $('#roleName_select').val();
        table.reload('selectTable', {
          where: { //设定异步数据接口的额外参数，任意设
            name: name
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }

    return this;
}
var selectRole = window.app.selectRole();