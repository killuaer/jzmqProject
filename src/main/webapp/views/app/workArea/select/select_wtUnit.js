var table;
window.app.selectWtUnit = function() {
    layui.use(['layer', 'jquery', 'table'], function() {
        table = layui.table;
        table.render({
            elem: '#selectTable',
            height: 400,
            url: '../unit/listUnit',
            where: {type:'委托单位'},
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
                    { unresize: true,field: 'code',width:'20%',title: '单位编号'},
                    { unresize: true,field: 'name',width:'20%',title: '单位名称'},
                    { unresize: true,field: 'address',width:'20%',title: '单位地址'},
                    { unresize: true,field: 'contactPerson',width:'20%',title: '联系人'},
                    { unresize: true,field: 'caozuo',  width: '14%', align: 'center', title: '操作',templet: function(d){
                        return operateColumn(d)
                    }}
                ]
            ]
        });
    });


    var operateColumn = function(d) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=selectFunc(\''+d.id+'\',\''+d.name+'\',\''+d.contactPerson+'\',\''+d.contactPhone+'\')>选择</a>';
        return row;
    }
    
    this.queryList  = function() {
        var name = $('#name_select').val();
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
var selectWtUnit = window.app.selectWtUnit();

var selectFunc = function(id,name,man,phone) {
    $('#wtUnitId').val(id);
    $('#wtUnit').val(name);
    $('#wtMan').val(man);
    $('#wtManTel').val(phone);
    layer.close(layer.index);
}