var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(structure_accountFilter)',function(data){
        var data = data.field;
        data.deptId = $('#selectDeptId').val();
       $.ajax({
               url:"../user/save", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('structureTable')
                   }else{
                       MSG("异常 "+data.msg)
                   }
               },
               error:function(){
                   MSG("异常");
               }       
        });
        return false;
    });
});

function submitStructureAccountForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#structure_accountBtn").click();
}

$('#selectRoleBtn').click(function() {
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
}