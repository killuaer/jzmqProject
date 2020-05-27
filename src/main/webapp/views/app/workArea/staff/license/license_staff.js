var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(license_staffFilter)',function(data){
        var data = data.field;
        layer.load(1);

       $.ajax({
               url:"../license/save", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('licenseTable')
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

function submitLicenseForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#license_staffBtn").click();
}

$('#selectStaffBtn').click(function() {
    var queryParams = {};
    selectSystemObj('姓名', 'select_staff', '70%', '60%', true, queryParams, selectStaffBackCall);
});

function selectStaffBackCall(data) {
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
    $('#staffName').val(ids1);
    $('#ryId').val(ids2);
}


//日期控件
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
	elem: '#validTerm'
	});
});