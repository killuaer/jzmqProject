var index;
var operateMode;
var form;
var table;
layui.use(['form'], function() {
    form = layui.form;
    form.on('submit(unit_infoFilter)',function(data){
        var data = data.field;
        var xmJson = table.cache.personTable;
        var datas = [];    //如果table删除了数据，拿到的table数据长度不会变，被删除的数据为长度为0的Array
        if (xmJson && xmJson.length > 0) {
            for (var i=0; i<xmJson.length; i++) {
                if (!(xmJson[i] instanceof Array)) {
                    datas.push(xmJson[i]);  
                }
            }
        }
        var xmData = JSON.stringify(datas);
        data.linkmanData = xmData;
        layer.load(1);
       $.ajax({
               url:"../unit/save", 
               type:"post",
               dataType:"json",
               data:data,
               success:function(data){
                   if(data.status=="success"){
                       layer.close(index); 
                       MSG("保存成功");
                       table.reload('unitTable')
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

function submitUnitForm (i,operM) {
    index = i;
    operateMode = operM;
    $("#unit_infoBtn").click();
}

layui.use(['table'], function() {
    table = layui.table;
    $.ajax({
       url:"../unit/listPerson", 
       type:"get",
       dataType:"json",
       data:{unitId:$('#unitId').val()},
       success:function(data){
            var tableData = data.data;
            table.render({
                elem: '#personTable',
                height: 200,
                data: tableData,
                even: true, //开启隔行背景
                size: 'sm', //小尺寸的表格
                cols: [
                    [
                        { unresize: true,width : '10%',templet: function (a) {return a.LAY_INDEX},align: 'center',title : '序号'},
                        { unresize: true,field: 'name',width:'15%',title: '联系人',edit:'text'},
                        { unresize: true,field: 'phone',width:'20%',title: '联系人电话',edit:'text'},
                        { unresize: true,field: 'type',width:'20%',title: '联系人类型',edit:'text'},
                        { unresize: true,field: 'code',width:'20%',title: '联系人编号',edit:'text'},
                        { unresize: true,field: 'cuozuo',  width: '15%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
           
       },
       error:function(){
       }       
    });
    
    table.on('tool(personTableFilter)', function(obj){ //注：tool是工具条事件名
      var data = obj.data; //获得当前行数据
      var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
      var tr = obj.tr; //获得当前行 tr 的DOM对象
      if(layEvent === 'del'){ //删除
          obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
          updateCache('personTable');
          if (data.id && data.id != '') {
              $.ajax({
                type : "POST",
                url : "../unit/delPerson", 
                data :  {id:data.id}, 
                dataType : "json",
                success: function (data) {
                    if(data.status=='success'){
                       MSG("删除成功");
                   }else{
                       MSG("已有关联，不能删除")
                   }
                }
            });
          }
      }
    });
});
form.on('select(typeUnit)', function (data) { 
    if(data.value == "建设单位" || data.value == "施工单位" || data.value == "委托单位"){
//        console.log($("#wtList").show().find('input')[0].attr('readonly') );
        $("#wtList").show().find('input').removeAttr('readonly');
    } else {
//        console.log($("#wtList").show().find('input'));
        $("#wtList").hide().find('input').attr("readonly","readonly");
    }
});
var operateColumn = function(id) {
    var row = '';
    row += '<a href="javascript:void(0);" lay-event="del" class="oc" >删除</a>';
    return row;
}

function add_person() {
    var table1 = table.cache["personTable"];
    var newRowData = {name:''};
    table1.push(newRowData);
    table.reload('personTable',{  
      data : table1  
    });
}
