var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(testItem_itemFilter)',function(data){
        var data = data.field;
        data.categoryId = $('#selectCategoryId').val();
        layer.load(1);
       $.ajax({
               url:"../testItem/saveItem", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('testItemTable')
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

function submitTestItemForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#testItem_itemBtn").click();
}
