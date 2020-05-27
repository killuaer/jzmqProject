var table;
window.app.project = function() {
	function init() {
		var html = $.templates.project.render();
		window.work_area.empty();
		window.work_area.html(html);
        projectTable();
	}
	this.backToList = function() {
		location.hash = "/project";
	}
	$.when($.lazyLoadTemplate("project")).done(function() {
		init();
	});
    
    function projectTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#projectTable',
                height: $('.data_list').height(),
                url: '../project/listProject',
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
                        { unresize: true,field: 'gcCode',width:'20%',title: '工程编号'},
                        { unresize: true,field: 'gcName',width:'20%',title: '工程名称'},
                        { unresize: true,field: 'gcJianduId',width:'20%',title: '监督登记号'},
                        { unresize: true,field: 'address',width:'20%',title: '工程地址'},
                        { unresize: true,field: 'cuozuo',  width: '15%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
    
    var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=project.check_entity(\''+id+'\')>查看</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=project.edit_entity(\''+id+'\')>编辑</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=project.del_entity(\''+id+'\')>删除</a>';
        return row;
    }
    this.queryList  = function() {
        var gcName = $('#gcName_project').val();
        var gcJianduId = $('#gcJianduId_project').val();
        table.reload('projectTable', {
          where: { //设定异步数据接口的额外参数，任意设
            gcName: gcName,
            gcJianduId: gcJianduId
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    this.toReset  = function() {
        $('#gcName_project').val('');
        $('#gcJianduId_project').val('');
    }
    //删除
	this.del_entity = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../project/del?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('projectTable');	
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
                name : "submitProjectForm",
                arg : ["save"]
            }
        };
        generateModel('project_info','edit', "70%", "",'工程信息',queryParams, btnOptions, '../project/getProject');
    }
    this.edit_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitProjectForm",
                arg : ["save"]
            }
        };
        generateModel('project_info','edit', "70%", "",'工程信息',queryParams, btnOptions, '../project/getProject?id='+id);
    }
    this.check_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        generateModel('project_info','edit', "70%", "",'工程信息',queryParams, null, '../project/getProject?id='+id);
    }
    
    return this;
};
var project = window.app.project();