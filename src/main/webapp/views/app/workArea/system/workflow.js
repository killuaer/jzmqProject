var table;
window.app.workflow = function() {
	function init() {
		var html = $.templates.workflow.render();
		window.work_area.empty();
		window.work_area.html(html);
		workflowTable();
	}
	this.backToList = function() {
		location.hash = "/workflow";
	}
	$.when($.lazyLoadTemplate("workflow")).done(function() {
		init();
	});
	function workflowTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#workflowTable',
                height: $('.data_list').height(),
                url: '../work/listWorkFlow',
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
                        { unresize: true,field: 'menuName', width: '12%', title: '文件标题',align: 'center' },
                        { unresize: true,field: 'cameraName', width: '8%', title: '文件号' ,align: 'center'},
                        { unresize: true,field: 'menuType', width: '8%', title: '工作类别' ,align: 'center'},
                        { unresize: true,field: 'jcUnit', width: '8%', title: '事务名称' ,align: 'center'},
                        { unresize: true,field: 'menuName', width: '8%', title: '拟稿人',align: 'center' },
                        { unresize: true,field: 'cameraName', width: '8%', title: '提交人·' ,align: 'center'},
                        { unresize: true,field: 'menuType', width: '8%', title: '待处理人' ,align: 'center'},
                        { unresize: true,field: 'jcUnit', width: '10%', title: '提交日期' ,align: 'center'},
                        { unresize: true,field: 'jcUnit', width: '10%', title: '处理日期' ,align: 'center'},
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
        row += '<a href="javascript:void(0);" class="oc" onclick=workflow.edit_entity(\''+id+'\')>编辑</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=workflow.del_entity(\''+id+'\')>删除</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=workflow.check_entity(\''+id+'\')>查看</a>';
        return row;
    }
	this.queryList  = function() {
        var name = $('#name').val();
        table.reload('workflowTable', {
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
    this.add_entity = function() {
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitWorkFlowForm",
                arg : ["save"]
            }
        };
        generateModel('workflow_info','add', "70%", "85%","流程信息",queryParams, btnOptions);
    }
    this.edit_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitWorkFlowForm",
                arg : ["save"]
            }
        };
        generateModel('workflow_info','edit', "70%", "85%","流程信息",queryParams, btnOptions, '../work/getWorkFlow?id='+id);
    }
    this.check_entity = function(id) {
        var queryParams = {
            type: 'check'
        };
        var btnOptions = {
        };
        generateModel('workflow_info','edit', "70%", "85%","流程信息",queryParams, null, '../work/getWorkFlow?id='+id);
    }
  //删除
	this.del_entity = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../video/delVideoCamera?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('videoCameraTable');	
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
	return this;
};
var workflow = window.app.workflow();