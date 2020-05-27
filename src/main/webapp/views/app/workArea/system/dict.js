var table;
var form;
window.app.dict = function() {
	function init() {
		var html = $.templates.dict.render();
		window.work_area.empty();
		window.work_area.html(html);
        dictTable();
        treeActive();
	}
	this.backToList = function() {
		location.hash = "/dict";
	}
	$.when($.lazyLoadTemplate("dict")).done(function() {
		init();
	});
    
    function dictTable() {
        layui.use(['table'], function() {
            table = layui.table;
            form = layui.form;
            table.render({
                elem: '#dictTable',
                height: $('.data_list').height(),
                where: {parentId: '1'},
                url: '../dict/listDict',
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
                        { unresize: true,field: 'type',width:'15%',title: '类型',align: 'center'},
                        { unresize: true,field: 'name', width: '20%', title: '名称',align: 'center' },
                        { unresize: true,field: 'code', width: '10%', title: '编号',align: 'center' },
                        { unresize: true,field: 'descr', width: '15%', title: '备注',align: 'center' },
                        { unresize: true,field: 'cuozuo',  width: '34%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d)
                        } }
                    ]
                ]

            });
            
            form.on('submit(submitCodeDefFilter)',function(data){
				var data = data.field;
				layer.load(1);
				$.ajax({
					   url:"../dict/saveDict", 
					   type:"post",
					   dataType:"json",
					   data:data,
					   success:function(data){
						   if(data.status=='success'){
							   MSG("保存成功");
						   }else{
							   MSG("异常 "+data)
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
            form.render();
        });
    }
    var operateColumn = function(d) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=dict.move_dict(\''+d.id+'\',\'up\')>上移</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=dict.move_dict(\''+d.id+'\',\'down\')>下移</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=dict.edit_dict(\''+d.id+'\')>编辑</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=dict.del_dict(\''+d.id+'\')>删除</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=dict.next_dict(\''+d.id+'\',\''+d.value+'\')>下一级</a>';
        return row;
    }
    
    function loadDictTree() {
        $.get("../dict/getDictTree", function(data){
            $("#tree_dict").empty();
            layui.use('tree', function(){
                layui.tree({
                    elem:"#tree_dict",
                    nodes:data,
                    click: clickDict,
                });
            });
        });   
    }
    loadDictTree();
    
    function clickDict(item) {
        $("#selectDict").val(item.name);
        $("#selectDictId").val(item.id);
        if ('0' != item.id) {
            table.reload('dictTable', {
                where: { //设定异步数据接口的额外参数，任意设
                    parentId: item.id
                },
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
    }
    this.add_dict = function() {
        var selectDictId = $('#selectDictId').val();
        if (selectDictId) {
            var queryParams = {
                type: 'add'
            };
            var btnOptions = {
                save : {
                    name : "submitDictForm",
                    arg : ["save"]
                }
            };
            generateModel('dict_info','add', "40%", "","数据字典信息",queryParams, btnOptions);
        } else {
            MSG("请先选择数据字典树");
        }
    }
    this.edit_dict = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitDictForm",
                arg : ["save"]
            }
        };
        generateModel('dict_info','edit', "40%", "","数据字典信息",queryParams, btnOptions, '../dict/getDict?id='+id);
    }
    
    //删除字典
	this.del_dict = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../dict/delDict?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('dictTable');	
						   MSG("删除成功");
					   }else{
						   MSG(data.msg)
					   }
					},
					error:function(){
					   MSG("异常");
					}       
				});
			});
		});
	}
    
    this.next_dict = function(id,value) {
        $('#selectTableDict').val(value);
        $('#selectTableDictId').val(id);
        var queryParams = {
            type: 'add'
        };
        generateModel('dict_children','add', "70%", "85%","数据字典信息",queryParams, null);
    }
    
    this.move_dict = function(id,type) {
        $.ajax({
               url:"../dict/moveDict", 
               type:"post",
               dataType:"json",
               data:{id:id, type:type},
               success:function(data){
                  if(data.status=='success'){
                      table.reload('dictTable');	
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
var dict = window.app.dict();