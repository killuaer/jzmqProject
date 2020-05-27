var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(testItem_categoryFilter)',function(data){
        var data = data.field;
        data.parentId = $('#selectCategoryId').val();
        layer.load(1);
       $.ajax({
               url:"../testItem/saveCategory", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       loadTree();
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

function submitTestItemCategoryForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#testItem_categoryBtn").click();
}

function loadTree() {
    $.get("../testItem/treeCategory", function(data){
        $("#tree_category").empty();
        layui.use('tree', function(){
          layui.tree({
              elem:"#tree_category",
              nodes:data,
              click: clickCategory,
          });
        });
    });   
}
function clickCategory(item) {
    $("#selectCategoryId").val(item.id);
    table.reload('testItemTable', {where:{categoryId: item.id}});
}