var table;
var form;
window.app.testItem_setting = function() {
	function init() {
		var html = $.templates.testItem_setting.render();
		window.work_area.empty();
		window.work_area.html(html);
        paramTable();
        treeActive();
	}
	this.backToList = function() {
		location.hash = "/testItem_setting";
	}
	$.when($.lazyLoadTemplate("testItem_setting")).done(function() {
		init();
	});
    
    function paramTable() {
        layui.use(['table','element','form'], function() {
            table = layui.table;
            form = layui.form;
            var element = layui.element;
            table.render({
                elem: '#paramTable',
                height: $('.data_list').height(),
                where: {xmId: '1'},
                url: '../testItem/listParam',
                even: true, //开启隔行背景
                size: 'sm', //小尺寸的表格
                page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                    layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                    //,curr: 5 //设定初始在第 5 页
                    groups: 1, //只显示 1 个连续页码
                    first: false, //不显示首页
                    last: false, //不显示尾页
                    theme: '#008aec'
                },
                cols: [
                    [
                        { unresize: true,width : '6%',templet: function (a) {return a.LAY_INDEX},align: 'center',title : '序号'},
                        { unresize: true,field: 'paramName',width:'20%',title: '检测参数名称',align: 'center'},
                        { unresize: true,field: 'customParamName', width: '20%', title: '缺省检测参数名称',align: 'center' },
                        { unresize: true,field: 'isDefault', width: '10%', title: '是否默认检测参数',align: 'center' },
                        { unresize: true,field: 'isOpen', width: '10%', title: '是否开放参数',align: 'center' },
                        { unresize: true,field: 'paramType', width: '10%', title: '参数类型',align: 'center' },
                        { unresize: true,field: 'cuozuo',  width: '24%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d)
                        } }
                    ]
                ]

            });
            form.on('submit(dict_infoFilter)',function(data){
                var data = data.field;
                if (data.id) {
                	layer.load(1);
                   $.ajax({
                           url:"../testItem/saveSetting", 
                           type:"post",
                           dataType:"json",
                           data:data,
                           success:function(data){
                               if(data.status=="success"){
                                   MSG("保存成功");
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
                }
                return false;
            });
            form.render();
        });
    }
    var operateColumn = function(d) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=testItem_setting.edit_param(\''+d.id+'\')>设置</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=testItem_setting.move_param(\''+d.id+'\',\'up\')>上移</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=testItem_setting.move_param(\''+d.id+'\',\'down\')>下移</a>';
        return row;
    }
    
    function loadSeleceValue() {
        //获取编码格式下拉
        $.ajax({
           url:"../customCode/getCodeList?name=委托编号", 
           type:"GET",
           dataType:"json",
           success:function(data){
                if (data.list && data.list.length > 0) {
                    for (var i = 0; i < data.list.length; i++) {
                        $("#phNumCodeFormat_setting").append("<option value=\"" + data.list[i].name + "\">" + data.list[i].name + "</option>");
                    }
                }
           },
           error:function(){
               MSG("异常");
           }      

        });
        $.ajax({
           url:"../customCode/getCodeList?name=报告编号", 
           type:"GET",
           dataType:"json",
           success:function(data){
                if (data.list && data.list.length > 0) {
                    for (var i = 0; i < data.list.length; i++) {
                        $("#prtnumCodeFormat_setting").append("<option value=\"" + data.list[i].name + "\">" + data.list[i].name + "</option>");
                    }
                }
           },
           error:function(){
               MSG("异常");
           }      

        });
        $.ajax({
           url:"../customCode/getCodeList?name=样品委托编号", 
           type:"GET",
           dataType:"json",
           success:function(data){
                if (data.list && data.list.length > 0) {
                    for (var i = 0; i < data.list.length; i++) {
                        $("#sampleWtnumCodeFormat_setting").append("<option value=\"" + data.list[i].name + "\">" + data.list[i].name + "</option>");
                    }
                }
           },
           error:function(){
               MSG("异常");
           }      

        });
    }
    loadSeleceValue();
    
    function loadItemTree() {
        $.get("../testItem/treeCategoryAndItem", function(data){
            $("#tree_item").empty();
            layui.use('tree', function(){
                layui.tree({
                    elem:"#tree_item",
                    nodes:data,
                    click: clickItem,
                });
            });
        });   
    }
    loadItemTree();
    
    function clickItem(item) {
        if (item.type == 'xm') {
            $("#selectItemId").val(item.id);
            $.ajax({
               url:"../testItem/getItem?id="+ item.id, 
               type:"GET",
               dataType:"json",
               success:function(data){
                   $('#defaultPdStd_setting').empty();
                   if (data.pdstdList && data.pdstdList.length > 0) {
                       for (i = 0; i < data.pdstdList.length; i++) {
                           $("#defaultPdStd_setting").append("<option value=\"" + data.pdstdList[i].name + "\">" + data.pdstdList[i].name + "</option>");
                       }
                   }
                   $('#testItem_settingForm').autofill(data);
                   if (data.isPrintWtxys && data.isPrintWtxys == 'y') {
                        $('input[name="is_print_wtxys"]').prop("checked",true);
                   } else {
                       $('input[name="is_print_wtxys"]').prop("checked",false);
                   }
                   if (data.isPrintTask && data.isPrintTask == 'y') {
                        $('input[name="is_print_task"]').prop("checked",true);
                   } else {
                       $('input[name="is_print_task"]').prop("checked",false);
                   }
                   if (data.isPrintYpbq && data.isPrintYpbq == 'y') {
                        $('input[name="is_print_ypbq"]').prop("checked",true);
                   } else {
                       $('input[name="is_print_ypbq"]').prop("checked",false);
                   }
                   if (data.isPrintBgpz && data.isPrintBgpz == 'y') {
                        $('input[name="is_print_bgpz"]').prop("checked",true);
                   } else {
                       $('input[name="is_print_bgpz"]').prop("checked",false);
                   }
                   if (data.isPrintOriginal && data.isPrintOriginal == 'y') {
                        $('input[name="is_print_original"]').prop("checked",true);
                   } else {
                       $('input[name="is_print_original"]').prop("checked",false);
                   }
                   form.render();
                   table.reload('paramTable',{
                       where: {xmId:item.xmId}
                   });
               },
               error:function(){
                   MSG("异常");
               }      
            
            });
            
            table.reload('paramTable', {
                where: { //设定异步数据接口的额外参数，任意设
                    xmId: item.id
                },
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
    }
    this.edit_param = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitParamForm",
                arg : ["save"]
            }
        };
        generateModel('testItem_setting_param','edit', "70%", "85%","参数设置",queryParams, btnOptions, '../testItem/getParam?id='+id);
    }
    
    this.move_param = function(id,type) {
        $.ajax({
               url:"../testItem/moveParam", 
               type:"post",
               dataType:"json",
               data:{id:id, type:type},
               success:function(data){
                  if(data.status=='success'){
                      table.reload('paramTable');	
                  } else {
                      MSG(data.msg)
                  }
               },
               error:function(){
                   MSG("异常");
               }       
        });
    }
	return this;
};
var testItem_setting = window.app.testItem_setting();