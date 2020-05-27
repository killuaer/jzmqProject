var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(structure_deptFilter)',function(data){
        var data = data.field;
        data.parentId = $('#selectDeptId').val();
       $.ajax({
               url:"../dept/save", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       loadDepartmentTree('tree_department','../dept/treeDept','selectDeptId','structureTable');
                       MSG("保存成功");
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

function submitStructureDeptForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#structure_deptBtn").click();
}

//function loadDepartmentTree() {
//    $.get("../dept/treeDept", function(data){
//        $("#tree_department").empty();
//        layui.use('tree', function(){
//          layui.tree({
//              elem:"#tree_department",
//              nodes:data,
//              click: clickDepartment
//          });
//        });
//    });   
//}

function clickDepartment(item) {
    $("#selectDeptId").val(item.id);
    table.reload('structureTable', {where:{deptId: item.id}});
}
    