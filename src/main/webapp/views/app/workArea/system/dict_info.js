var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(dict_infoFilter)',function(data){
        var data = data.field;
        if (operateMode == 'dict') {
            data.type = $('#selectDict').val();
            data.parentId = $('#selectDictId').val();
            
        } else if (operateMode == 'children') {
            data.type = $('#selectTableDict').val();
            data.parentId = $('#selectTableDictId').val();
        }
        layer.load(1);
       $.ajax({
               url:"../dict/saveDict", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       if (operateMode == 'dict') {
                           if ($('#selectDictId').val() == '0') {
                                loadDictTree();
                           } else {
                                table.reload('dictTable');
                           }
                           
                       } else if (operateMode == 'children') {
                           table.reload('dict_childrenTable');
                       }
                       MSG("保存成功");
                   }else{
                       MSG("异常 "+data.msg)
                   }
                   layer.closeAll('loading');
               },
               error:function(){
                   MSG("异常");
                   layer.closeAll('loading');
               }       
        });
        return false;
    });
});

function submitDictForm(i) {
    index = i;
    operateMode = 'dict';
    $("#dict_infoBtn").click();
}

function submitDictChildrenForm(i) {
    index = i;
    operateMode = 'children';
    $("#dict_infoBtn").click();
}

function loadDictTree() {
    $.get("../dict/getDictTree", function(data){
        $("#tree_dict").empty();
        layui.use('tree', function(){
            layui.tree({
                elem:"#tree_dict",
                nodes:data,
                click: clickDict,
            });
        });
    });   
}

function clickDict(item) {
    $("#selectDict").val(item.name);
    $("#selectDictId").val(item.id);
    if ('0' != item.id) {
        table.reload('dictTable', {
            where: { //设定异步数据接口的额外参数，任意设
                parentId: item.id
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }
}
