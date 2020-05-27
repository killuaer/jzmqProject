var table;
window.app.selectProject = function() {
    layui.use(['layer', 'jquery', 'table'], function() {
        table = layui.table;
        table.render({
            elem: '#selectTable',
            height: 400,
            url: '../project/listProject',
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
                    { unresize: true,width : '6%',templet: function (a) {return a.LAY_INDEX},align: 'center',title : '序号'},
                    { unresize: true,field: 'gcCode',width:'20%',title: '工程项目编号'},
                    { unresize: true,field: 'gcName',width:'20%',title: '工程名称'},
                    { unresize: true,field: 'wtUnit',width:'20%',title: '委托单位'},
                    { unresize: true,field: 'gcAddress',width:'20%',title: '工程地址'},
                    { unresize: true,field: 'caozuo',  width: '14%', align: 'center', title: '操作',templet: function(d){
                        return operateColumn(d)
                    }}
                ]
            ]
        });
    });


    var operateColumn = function(d) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=selectFunc(\''+JSON.stringify(d)+'\')>选择</a>';
        return row;
    }
    
    this.queryList  = function() {
        var gcName = $('#gcName_select').val();
        table.reload('selectTable', {
          where: { //设定异步数据接口的额外参数，任意设
            gcName: gcName
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }

    return this;
}
var selectProject = window.app.selectProject();

var selectFunc = function(d) {
    var json = JSON.parse(d);
    $('#jcxmGrouForm #gcCode').val(json.gcCode);
    $('#jcxmGrouForm #gcName').val(json.gcName);
    $('#jcxmGrouForm #gcAddress').val(json.gcAddress);
    $('#jcxmGrouForm #jsUnit').val(json.jsUnit);
    $('#jcxmGrouForm #jzUnit').val(json.jzUnit);
    $('#jcxmGrouForm #sjUnit').val(json.sjUnit);
    $('#jcxmGrouForm #wtUnit').val(json.wtUnit);
    $('#jcxmGrouForm #wtMan').val(json.wtMan);
    $('#jcxmGrouForm #wtManTel').val(json.wtManTel);
    $('#jcxmGrouForm #jzMan').val(json.jzMan);   
    layer.close(layer.index);
}