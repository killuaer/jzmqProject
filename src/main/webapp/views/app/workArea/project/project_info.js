var index;
var operateMode;
var form;
layui.use(['form'], function() {
    form = layui.form;
    form.on('submit(project_infoFilter)',function(data){
        var data = data.field;
        layer.load(1);
       $.ajax({
               url:"../project/save", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('projectTable')
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

function submitProjectForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#project_infoBtn").click();
}