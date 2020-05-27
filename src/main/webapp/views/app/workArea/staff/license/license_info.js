var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(license_infoFilter)',function(data){
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
    $("#license_infoBtn").click();
}

//日期控件
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
	elem: '#validTerm'
	});
});

