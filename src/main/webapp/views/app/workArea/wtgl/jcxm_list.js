var table;
window.app.jcxmList = function() {
	function init() {
		var html = $.templates.jcxmList.render();
		window.work_area.empty();
		window.work_area.html(html);
		jcxmListTable();
        loadDepartmentTree('tree_department','../wtgl/moveJcxm','selectDeptId','jcxmListTable');
        treeActive();
	}

    $.when($.lazyLoadTemplate("jcxmList")).done(function() {
		init();
	});

	this.backToList = function() {
		location.hash = "/jcxmList";
	}
	function jcxmListTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#jcxmListTable',
                height: $('.data_list').height(),
                url: '../wtgl/listJcxmcs',
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
                        { unresize: true,width : '7%',templet: function (a) {return a.LAY_INDEX},align: 'center',title : '序号'},
                        { unresize: true,field: 'xmName',width:'70%',title: '子单元分项',align: 'center'},
                        { unresize: true,field: 'cuozuo',  width: '20%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
    var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=jcxmList.edit_account(\''+id+'\')>编辑</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=jcxmList.del_account(\''+id+'\')>删除</a>';
        return row;
    }
    function clickDepartment(item) {
        $("#selectDeptId").val(item.id);
        table.reload('jcxmListTable', {where:{pid: item.id}});
    }
    //新增部门
    this.add_dept = function() {
        var id = $("#selectDeptId").val();
        if (id) {
            var queryParams = {
                type: 'add'
            };
            var btnOptions = {
                save : {
                    name : "submitStructureDeptForm",
                    arg : ["save"]
                }
            };
            generateModel('structure_dept','add', "50%", "40%", "部门信息",queryParams, btnOptions);
        } else {
            MSG("请先选择部门!");
        }
	}
    //编辑部门
    this.edit_dept = function() {
        var id = $("#selectDeptId").val();
        if (id) {
            var queryParams = {
                type: 'edit'
            };
            var btnOptions = {
                save : {
                    name : "submitStructureDeptForm",
                    arg : ["save"]
                }
            };
            generateModel('structure_dept','edit', "70%", "40%","部门信息",queryParams, btnOptions, '../dept/getDept?id='+id);
        } else {
            MSG("请先选择部门!");
        }
	}
    //删除部门
	this.del_dept = function() {
        var id = $("#selectDeptId").val();
        if (id) {
            layui.use('layer',function(){
                layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
                    $.ajax({
                        url:"../dept/del?id="+id, 
                        type:"get",
                        success:function(data){
                           if(data.status=='success'){
                               layer.close(index); 
                               $("#selectDeptId").val("");
                               loadDepartmentTree('tree_department','../dept/treeDept','selectDeptId','jcxmTable');
                               MSG("删除成功");
                           }else{
                               MSG("已有关联，不能删除");
                               loadDepartmentTree('tree_department','../dept/treeDept','selectDeptId','jcxmTable');
                           }
                        },
                        error:function(){
                           MSG("异常");
                        }       
                    });
                });
            });
        } else {
            MSG("请先选择部门!");
        }
	}
	//新增用户
    this.add_account = function() {
        var id = $("#selectDeptId").val();
        if (id) {
            var queryParams = {
                type: 'add'
            };
            var btnOptions = {
                save : {
                    name : "submitStructureAccountForm",
                    arg : ["save"]
                }
            };
            generateModel('structure_account','add', "40%", "", "用户信息",queryParams, btnOptions);
        } else {
            MSG("请先选择部门!");
        }
	}
    //编辑用户
    this.edit_account = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitStructureAccountForm",
                arg : ["save"]
            }
        };
        generateModel('structure_account','edit', "40%", "", "用户信息",queryParams, btnOptions, '../user/getUser?id='+id);
	}
    //删除用户
	this.del_account = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../user/del?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('structureTable');	
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
    
    this.move_dept = function(type) {
        var id = $('#selectDeptId').val();
        $.ajax({
               url:"../wtgl/moveJcxm", 
               type:"post",
               dataType:"json",
               data:{id:id, type:type},
               success:function(data){
                  if(data.status=='success'){
                      loadDepartmentTree('tree_department','../dept/treeDept','selectDeptId','jcxmTable');
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
var jcxmList = window.app.jcxmList();