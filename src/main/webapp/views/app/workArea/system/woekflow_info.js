var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(workflow_infoFilter)',function(data){
        var data = data.field;
        layer.load(1);
       $.ajax({
               url:"../video/saveWorkFlow", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('workflowTable')
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

function submitWorkFlowForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#workflow_infoBtn").click();
}

/*$('#selectRoleBtn').click(function() {
    var queryParams = {};
    selectSystemObj('角色', 'select_role', '70%', '60%', true, queryParams, selectRoleBackCall);
});

function selectRoleBackCall(data) {
    var arr1 = new Array();
    var arr2 = new Array();
    if (data.length >= 1) {
        for (i = 0; i < data.length; i++) {
            arr1.push(data[i].name);
            arr2.push(data[i].id);
        }
    }
    var ids1 = arr1.join(",");
    var ids2 = arr2.join(",");
    $('#roleNames').val(ids1);
    $('#roleIds').val(ids2);
}*/