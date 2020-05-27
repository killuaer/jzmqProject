var index;
var operateMode;
layui.use(['form'], function() {
    var form = layui.form;
    form.on('submit(env_infoFilter)',function(data){
        var data = data.field;
        layer.load(1);
       $.ajax({
               url:"../env/save", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('envTable')
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

function submitEnvForm(i,operM) {
    index = i;
    operateMode = operM;
    $("#env_infoBtn").click();
}

//日期控件,有效期
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
	elem: '#jdjzValidTerm',
	});
    $('.dateType').each(function(){
        laydate.render({
            elem: this,
            format: 'yyyy-MM-dd'
        });
    });
});

//最新检定时间
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
	elem: '#jdjzDate',
	done: function(value, date, endDate){
		layui.use('laydate', function(){
			var laydate = layui.laydate;
			//执行一个laydate实例
			laydate.render({
			elem: '#nextJdjzDate',
			value: (date.year+1)+"-"+date.month+"-"+date.date, 
			});
		});
	  }
	});
});

//下次检定时间
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
	elem: '#nextJdjzDate',
	});
});

