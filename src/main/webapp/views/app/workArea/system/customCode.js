var table;
window.app.customCode = function() {
	function init() {
		var html = $.templates.customCode.render();
		window.work_area.empty();
		window.work_area.html(html);
        customCodeTable();
        treeActive();
	}
	this.backToList = function() {
		location.hash = "/customCode";
	}
	$.when($.lazyLoadTemplate("customCode")).done(function() {
		init();
	});
    
    function customCodeTable() {
        layui.use(['table'], function() {
            table = layui.table;
            table.render({
                elem: '#customCodeTable',
                height: $('.data_list').height(),
                url: '../customCode/listJcXtszCodeDefine',
                where: {type: '1'},
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
                        { unresize: true,field: 'name', width: '40%', title: '编号名称',align: 'center' },
                        { unresize: true,field: 'codeFormat', width: '40%', title: '编号规则' ,align: 'center'},
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
        row += '<a href="javascript:void(0);" class="oc" onclick=customCode.edit_customCode(\''+id+'\')>编辑</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=customCode.del_customCode(\''+id+'\')>删除</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=customCode.check_customCode(\''+id+'\')>查看</a>';
        return row;
    }
    
    var treeData = [
        {"name":"所有编码格式","spread":true, "children":  [
            	{"name":"样品编号","type":"code","spread":true},
                {"name":"委托编号","type":"code","spread":true},
                {"name":"报告编号","type":"code","spread":true},
                {"name":"工程编号","type":"code","spread":true}
            ]
        },
    ];
    
    function loadCustomCodeTree() {
        layui.use('tree', function(){
            layui.tree({
                elem:"#tree_customCode",
                nodes:treeData,
                click: clickCustomCode,
            });
        });
    }
    loadCustomCodeTree();
    
    function clickCustomCode(item) {
        $("#selectCustomCode").val(item.name);
        table.reload('customCodeTable', {
            where: { //设定异步数据接口的额外参数，任意设
                type: item.name
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }
    this.add_customCode = function() {
        var selectCustomCode = $('#selectCustomCode').val();
        if (selectCustomCode) {
            var queryParams = {
                type: 'add'
            };
            var btnOptions = {
                save : {
                    name : "submitCustomCodeForm",
                    arg : ["save"]
                }
            };
            generateModel('customCode_info','add', "70%", "","自定编号",queryParams, btnOptions);
        } else {
            MSG("请先选择数据字典树");
        }
    }
    this.edit_customCode = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitCustomCodeForm",
                arg : ["save"]
            }
        };
        generateModel('customCode_info','edit', "70%", "","自定编号",queryParams, btnOptions, '../customCode/getJcXtszCodeDefine?id='+id);
    }
    
    this.check_customCode = function(id) {
        var queryParams = {
            type: 'edit'
        };
        generateModel('customCode_info','edit', "70%", "","自定编号",queryParams, null, '../customCode/getJcXtszCodeDefine?id='+id);
    }
    
    //删除字典
	this.del_customCode = function(id) {
		layui.use('layer',function(){
			layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"../customCode//del?id="+id, 
					type:"get",
					success:function(data){
					   if(data.status=='success'){
						   layer.close(index); 
						   table.reload('customCodeTable');	
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
var customCode = window.app.customCode();
