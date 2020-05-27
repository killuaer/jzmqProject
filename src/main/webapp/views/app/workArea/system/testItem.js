var table;
window.app.testItem = function() {
	function init() {
		var html = $.templates.testItem.render();
		window.work_area.empty();
		window.work_area.html(html);
        testItemTable();
        treeActive();
	}

    $.when($.lazyLoadTemplate("testItem")).done(function() {
		init();
	});

	this.backToList = function() {
		location.hash = "/testItem";
	}
	function testItemTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#testItemTable',
                height: $('#rightDiv').height()*0.9,
                url: '../testItem/listItem',
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
                        { unresize: true,width : '8%',templet: function (a) {return a.LAY_INDEX},align: 'center',title : '序号'},
                        { unresize: true,field: 'xmName',width:'32%',title: '项目名称'},
                        { unresize: true,field: 'pdStd', width: '35%', title: '检测标准',align: 'center' },
                        { unresize: true,field: 'cuozuo',  width: '25%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
    var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=testItem.move_item(\''+id+'\',\'up\')>上移</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=testItem.move_item(\''+id+'\',\'down\')>下移</a>';
//        row += '<a href="javascript:void(0);" class="oc" onclick=testItem.edit_item(\''+id+'\')>编辑</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=testItem.del_item(\''+id+'\')>删除</a>';
        return row;
    }
    
    function loadTree() {
        $.get("../testItem/treeCategory", function(data){
            $("#tree_category").empty();
            layui.use('tree', function(){
              layui.tree({
                  elem:"#tree_category",
                  nodes:data,
                  click: clickCategory,
              });
            });
        });   
    }
    loadTree();

    function clickCategory(item) {
        $("#selectCategoryId").val(item.id);
        table.reload('testItemTable', {where:{categoryId: item.id}});
    }
    //新增分类
    this.add_category = function() {
        var id = $("#selectCategoryId").val();
        if (id) {
            var queryParams = {
                type: 'add'
            };
            var btnOptions = {
                save : {
                    name : "submitTestItemCategoryForm",
                    arg : ["save"]
                }
            };
            generateModel('testItem_category','add', "50%", "","项目分类树",queryParams, btnOptions);
        } else {
            MSG("请先选择分类!");
        }
	}
    //编辑分类
    this.edit_category = function() {
        var id = $("#selectCategoryId").val();
        if (id) {
            var queryParams = {
                type: 'edit'
            };
            var btnOptions = {
                save : {
                    name : "submitTestItemCategoryForm",
                    arg : ["save"]
                }
            };
            generateModel('testItem_category','edit',"50%", "","项目分类树",queryParams, btnOptions, '../testItem/getCategory?id='+id);
        } else {
            MSG("请先选择分类!");
        }
	}
    //删除分类
	this.del_category = function() {
        var id = $("#selectCategoryId").val();
        if (id) {
            layui.use('layer',function(){
                layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
                    $.ajax({
                        url:"../testItem/delCategory?id="+id, 
                        type:"get",
                        success:function(data){
                           if(data.status=='success'){
                               layer.close(index); 
                               $("#selectCategoryId").val("");
                               loadTree();
                               MSG("删除成功");
                           }else{
                               MSG(data.msg);
                               loadTree();
                           }
                        },
                        error:function(){
                           MSG("异常");
                        }       
                    });
                });
            });
        } else {
            MSG("请先选择分类!");
        }
	}

    this.relate_category = function() {
        var id = $("#selectCategoryId").val();
        if (id) {
            var queryParams = {
//                type: 'edit'
            };
            var btnOptions = {
//                moveIn : {
//                    name : "submitRelateXmForm",
//                    arg : ["moveIn"]
//                },
//                moveOut : {
//                    name : "submitRelateXmForm",
//                    arg : ["moveOut"]
//                }
            };
            generateModel('testItem_relate','add', "80%", "85%", "项目管理",queryParams, btnOptions);
        } else {
            MSG("请先选择分类!");
        }
    }
    
    this.move_category = function(type) {
        var id = $("#selectCategoryId").val();
        if (id) {
            $.ajax({
               url:"../testItem/moveCategory", 
               type:"post",
               dataType:"json",
               data:{id:id, type:type},
               success:function(data){
                  if(data.status=='success'){
                      $("#selectCategoryId").val("");
                       loadTree();	
                  } else {
                      MSG(data.msg)
                  }
               },
               error:function(){
                   MSG("异常");
               }       
            });
        } else {
            MSG("请先选择分类!");
        }
    }
    
    //新增项目
    /*this.add_item = function() {
        var id = $("#selectCategoryId").val();
        if (id) {
            var queryParams = {
                type: 'add'
            };
            var btnOptions = {
                save : {
                    name : "submitTestItemForm",
                    arg : ["save"]
                }
            };
            generateModel('testItem_item','add', "70%", "85%",queryParams, btnOptions);
        } else {
            MSG("请先选择分类!");
        }
	}*/
    //编辑项目
    /*this.edit_item = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitTestItemForm",
                arg : ["save"]
            }
        };
        generateModel('testItem_item','edit', "70%", "85%",queryParams, btnOptions, '../testItem/getItem?id='+id);
	}*/
    //删除项目
	this.del_item = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../testItem/delItem?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('testItemTable');	
						   MSG("删除成功");
					   }else{
						   MSG("已有关联，不能删除")
					   }
					},
					error:function(){
					   MSG("异常");
					}       
				});
			});
		});
	}
    
    this.move_item = function(id,type) {
        $.ajax({
               url:"../testItem/moveItem", 
               type:"post",
               dataType:"json",
               data:{id:id, type:type},
               success:function(data){
                  if(data.status=='success'){
                      table.reload('testItemTable');	
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
var testItem = window.app.testItem();