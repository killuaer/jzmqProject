var index;
var operateMode;
var form;
var table;

layui.use(['form'], function() {
    form = layui.form;
    form.on('submit(wtgl_jcxmgrouFilter)',function(data){
        var data = data.field;
        layer.load(1);
       $.ajax({
               url:"../wtgl/jcXmCoreSave", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('jcxmCoreTable')
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

//日期控件
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
		elem: '#syDate'
	});
    $('.dateType').each(function(){
        laydate.render({
            elem: this,
            format: 'yyyy-MM-dd'
        });
    });
});

function submitjcxmGrouForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#wtgl_jcxmgrouBtn").click();
}