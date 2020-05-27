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

//获取下拉值
function loadSeleceValue() {
    $.ajax({
       url:"../dict/getDictList?type=工程类型", 
       type:"GET",
       dataType:"json",
       success:function(data){
            if (data.list && data.list.length > 0) {
                for (var i = 0; i < data.list.length; i++) {
                    $("#type").append("<option value=\"" + data.list[i].name + "\">" + data.list[i].name + "</option>");
                }
                form.render();
                callAutofill();
            }
       },
       error:function(){
           MSG("异常");
       }      

    });
    $.ajax({
       url:"../dict/getDictList?type=建筑性质", 
       type:"GET",
       dataType:"json",
       success:function(data){
            if (data.list && data.list.length > 0) {
                for (var i = 0; i < data.list.length; i++) {
                    $("#buildingNature").append("<option value=\"" + data.list[i].name + "\">" + data.list[i].name + "</option>");
                }
                form.render();
                callAutofill();
            }
       },
       error:function(){
           MSG("异常");
       }      

    });
    $.ajax({
       url:"../dict/getDictList?type=建筑结构形式", 
       type:"GET",
       dataType:"json",
       success:function(data){
            if (data.list && data.list.length > 0) {
                for (var i = 0; i < data.list.length; i++) {
                    $("#buildingStyle").append("<option value=\"" + data.list[i].name + "\">" + data.list[i].name + "</option>");
                }
                form.render();
                callAutofill();
            }
       },
       error:function(){
           MSG("异常");
       }      

    });
    
}
loadSeleceValue();