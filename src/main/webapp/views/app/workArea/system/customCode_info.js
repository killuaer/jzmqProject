var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(customCode_infoFilter)',function(data){
        var data = data.field;
        data.type = $('#selectCustomCode').val();
        layer.load(1);
       $.ajax({
               url:"../customCode/save", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('customCodeTable')
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

function submitCustomCodeForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#customCode_infoBtn").click();
}
