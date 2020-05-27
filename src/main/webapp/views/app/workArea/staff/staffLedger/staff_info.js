var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(staff_infoFilter)',function(data){
        var data = data.field;
        layer.load(1);
       $.ajax({
               url:"../staff/save", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('staffTable')
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

function submitStaffForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#staff_infoBtn").click();
}

//日期控件
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
	elem: '#birthday'
	});
    $('.dateType').each(function(){
        laydate.render({
            elem: this,
            format: 'yyyy-MM-dd'
        });
    });
});
function selectDepart(){
    var queryParams = {
        type: 'add'
    };
    var btnOptions = {
        sure : {
            name : "selectDepartForm",
            arg : ["sure"]
        }
    };
    generateModel('select_depart','add', "400px", "550px",'部门信息',queryParams, btnOptions);
}
function selectDepartForm(winId){
    departName = $('#departName1').val();
    $("input[name='departId']").val(departName);
    layer.close(winId);
}
