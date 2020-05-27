var table;
window.app.unit = function() {
	function init() {
		var html = $.templates.unit.render();
		window.work_area.empty();
		window.work_area.html(html);
        unitTable();
	}
	this.backToList = function() {
		location.hash = "/unit";
	}
	$.when($.lazyLoadTemplate("unit")).done(function() {
		init();
	});
    
    function unitTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#unitTable',
                height: $('.data_list').height(),
                url: '../unit/listUnit',
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
                        { unresize: true,field: 'code',width:'10%',title: '单位编号'},
                        { unresize: true,field: 'name',width:'25%',title: '单位名称'},
                        { unresize: true,field: 'address',width:'25%',title: '单位地址'},
                        { unresize: true,field: 'type',width:'10%',title: '单位类型'},
                        { unresize: true,field: 'contactPerson',width:'10%',title: '联系人'},
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
        row += '<a href="javascript:void(0);" class="oc" onclick=unit.check_entity(\''+id+'\')>查看</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=unit.edit_entity(\''+id+'\')>编辑</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=unit.del_entity(\''+id+'\')>删除</a>';
        return row;
    }
    this.queryList  = function() {
        var name = $('#name_unit').val();
        var code = $('#code_unit').val();
        table.reload('unitTable', {
          where: { //设定异步数据接口的额外参数，任意设
            name: name,
            code: code
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    this.toReset  = function() {
        $('#name_unit').val('');
        $('#code_unit').val('');
    }
    //删除
	this.del_entity = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../unit/del?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('unitTable');	
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
                name : "submitUnitForm",
                arg : ["save"]
            }
        };
        generateModel('unit_info','edit', "70%", "85%",'单位信息',queryParams, btnOptions, '../unit/getUnit');
    }
    this.edit_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitUnitForm",
                arg : ["save"]
            }
        };
        generateModel('unit_info','edit', "70%", "85%",'单位信息',queryParams, btnOptions, '../unit/getUnit?id='+id);
    }
    this.check_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        generateModel('unit_info','edit', "70%", "85%",'单位信息',queryParams, null, '../unit/getUnit?id='+id);
    }
    
    return this;
};
var unit = window.app.unit();