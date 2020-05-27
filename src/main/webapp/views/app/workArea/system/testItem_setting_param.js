var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(testItem_setting_paramFilter)',function(data){
        var data = data.field;
        layer.load(1);
       $.ajax({
               url:"../testItem/saveParam", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                        table.reload('paramTable');
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

function submitParamForm(i) {
    index = i;
    $("#testItem_setting_paramBtn").click();
}

