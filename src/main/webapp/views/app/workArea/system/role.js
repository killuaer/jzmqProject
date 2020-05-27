var table;
window.app.role = function() {
	function init() {
		var html = $.templates.role.render();
		window.work_area.empty();
		window.work_area.html(html);
        roleTable();
	}
	this.backToList = function() {
		location.hash = "/role";
	}
	$.when($.lazyLoadTemplate("role")).done(function() {
		init();
	});
    
    function roleTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#roleTable',
                height: $('.data_list').height(),
                url: '../role/listRole',
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
                        { unresize: true,width : '5%',templet: function (a) {return a.LAY_INDEX},align: 'center',title : '序号'},
                        { unresize: true,field: 'name',width:'25%',title: '角色名'},
                        { unresize: true,field: 'descr', width: '40%', title: '描述' ,align: 'center'},
                        { unresize: true,field: 'cuozuo',  width: '30%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
    
    var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=role.grantMenu_entity(\''+id+'\')>操作权限</a>';
     /*   row += '<a href="javascript:void(0);" class="oc" onclick=role.dataPermission_entity(\''+id+'\')>数据权限</a>';*/
        row += '<a href="javascript:void(0);" class="oc" onclick=role.edit_entity(\''+id+'\')>编辑</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=role.del_entity(\''+id+'\')>删除</a>';
//        row += '<a href="javascript:void(0);" class="oc" onclick=role.grantUser_entity(\''+id+'\')>授权</a>';
        return row;
    }
    this.queryList  = function() {
        var name = $('#name').val();
        table.reload('roleTable', {
          where: { //设定异步数据接口的额外参数，任意设
            name: name
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    this.toReset  = function() {
        $('#name').val('');
    }
    //删除
	this.del_entity = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../role/del?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('roleTable');	
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
    this.add_entity = function() {
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitRoleForm",
                arg : ["save"]
            }
        };
        generateModel('role_info','add', "50%", "",'角色信息',queryParams, btnOptions);
    }
    this.edit_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitRoleForm",
                arg : ["save"]
            }
        };
        generateModel('role_info','edit', "70%", "",'角色信息',queryParams, btnOptions, '../role/getRole?id='+id);
    }
    this.check_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        generateModel('role_info','edit', "70%", "",'角色信息',queryParams, null, '../role/getRole?id='+id);
    }
    this.grantMenu_entity = function(id) {
        $('#selectRoleId').val(id);
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitGrantMenuForm",
                arg : ["save"]
            }
        };
        generateModel('role_grantMenu','add', "500px", "650px","授权操作权限",queryParams, btnOptions);
    }
    this.dataPermission_entity = function(id) {
        $('#selectRoleId').val(id);
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitDataPermissionForm",
                arg : ["save"]
            }
        };
        generateModel('role_dataPermission','add', '70%', '85%',"授权数据权限",queryParams, btnOptions);
    }
    //直接将角色授权给用户，有bug，不能取消角色，只能新增角色
    this.grantUser_entity = function(id) {
        $('#selectRoleId').val(id);
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitGrantUserForm",
                arg : ["save"]
            }
        };
        generateModel('role_grantUser','add', "70%", "85%","授权角色",queryParams, btnOptions);
    }
    
    return this;
};
var role = window.app.role();