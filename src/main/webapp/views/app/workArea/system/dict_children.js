var table;
layui.use(['table'], function() {
    table = layui.table;
    table.render({
        elem: '#dict_childrenTable',
        height: 530,
        url: '../dict/listDict',
        where: {parentId: $('#selectTableDictId').val()},
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
                { unresize: true,field: 'type',width:'15%',title: '类型',align: 'center'},
                { unresize: true,field: 'name', width: '20%', title: '名称',align: 'center' },
                { unresize: true,field: 'code', width: '10%', title: '编号',align: 'center' },
                { unresize: true,field: 'descr', width: '15%', title: '备注',align: 'center' },
                { unresize: true,field: 'cuozuo',  width: '34%', align: 'center', title: '操作',templet: function(d){
                    return operateColumn(d.id)
                } }
            ]
        ]

    });
});

var operateColumn = function(id) {
    var row = '';
    row += '<a href="javascript:void(0);" class="oc" onclick=move_dict_children(\''+id+'\',\'up\')>上移</a>';
    row += '<a href="javascript:void(0);" class="oc" onclick=move_dict_children(\''+id+'\',\'down\')>下移</a>';
    row += '<a href="javascript:void(0);" class="oc" onclick=edit_dict_children(\''+id+'\')>编辑</a>';
    row += '<a href="javascript:void(0);" class="oc" onclick=del_dict_children(\''+id+'\')>删除</a>';
    return row;
}
function add_dict_children() {
    var queryParams = {
        type: 'add'
    };
    var btnOptions = {
        save : {
            name : "submitDictChildrenForm",
            arg : ["save"]
        }
    };
    generateModel('dict_info','add', "70%", "85%","数据字典信息",queryParams, btnOptions);
}
function edit_dict_children(id) {
    var queryParams = {
        type: 'edit'
    };
    var btnOptions = {
        save : {
            name : "submitDictChildrenForm",
            arg : ["save"]
        }
    };
    generateModel('dict_info','edit', "70%", "85%","数据字典信息",queryParams, btnOptions, '../dict/getDict?id='+id);
}

//删除字典
function del_dict_children(id) {
    layui.use('layer',function(){
        layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                url:"../dict/delDict?id="+id, 
                type:"get",
                success:function(data){
                   if(data.status=='success'){
                       layer.close(index); 
                       table.reload('dict_childrenTable');	
                       MSG("删除成功");
                   }else{
                       MSG(data.msg)
                   }
                },
                error:function(){
                   MSG("异常");
                }       
            });
        });
    });
}

function move_dict_children(id, type) {
    $.ajax({
       url:"../dict/moveDict", 
       type:"post",
       dataType:"json",
       data:{id:id, type:type},
       success:function(data){
          if(data.status=='success'){
              table.reload('dict_childrenTable');	
          } else {
              MSG(data.msg)
          }
       },
       error:function(){
           MSG("异常");
       }       
});
}