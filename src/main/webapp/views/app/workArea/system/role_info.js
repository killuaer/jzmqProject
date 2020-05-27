var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(role_infoFilter)',function(data){
        var data = data.field;
        layer.load(1);
       $.ajax({
               url:"../role/save", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('roleTable')
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

function submitRoleForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#role_infoBtn").click();
}
stopRefresh();
$('#searchText').on('keydown', function (event) {
    console.log("!!!");
    if (event.keyCode == 13) {


        return false
    }
});