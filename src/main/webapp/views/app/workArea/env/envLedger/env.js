var table;
window.app.env = function() {
	function init() {
		var html = $.templates.env.render();
		window.work_area.empty();
		window.work_area.html(html);
		envTable();
	}
	this.backToList = function() {
		location.hash = "/env";
	}
	$.when($.lazyLoadTemplate("env")).done(function() {
		init();
	});
    
    function envTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#envTable',
                url: '../env/listEnv',
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
                        { unresize: true,field: 'sbCode',width:'7%',title: '设备编号',align: 'center'},
                        { unresize: true,field: 'sbName',width:'9%',title: '设备名称',align: 'center'},
                        { unresize: true,field: 'status',width:'7%',title: '设备状态',align: 'center'},
                        { unresize: true,field: 'sbModel',width:'10%',title: '规格型号',align: 'center'},
                        { unresize: true,field: 'jdjzCycle',width:'7%',title: '检定周期',align: 'center'},
                        { unresize: true,field: 'jdjzValidTerm',width:'7%',title: '有效期',align: 'center'},
                        { unresize: true,field: 'jdjzDate',width:'9%',title: '最新检定日期',align: 'center'},
                        { unresize: true,field: 'nextJdjzDate',width:'9%',title: '下次检定日期',align: 'center'},
                        { unresize: true,field: 'factory',width:'9%',title: '生产厂家',align: 'center'},
                        { unresize: true,field: 'facNum', width: '7%', title: '出厂编号',align: 'center' },
                        { unresize: true,field: 'manager', width: '7%', title: '设备管理员' ,align: 'center'},
                        { unresize: true,field: 'caozuo',  width: '7%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
    
    var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=env.edit_entity(\''+id+'\')>详情</a>';
        return row;
    }
    //查询
    this.queryList  = function() {
    	var content = $('#content').val();
        var overTime = $('#overTime').val();
        var departId = $('#departId').val();
        var stationId = $('#stationId').val();
        var sort = $('#sort').val();
        var starttime = $('#starttime').val();
        var endtime = $('#endtime').val();
        table.reload('envTable', {
          where: { //设定异步数据接口的额外参数，任意设
        	content: content,
        	overTime: overTime,
        	departId: departId,
        	stationId: stationId,
        	sort: sort,
        	starttime: starttime,
        	endtime:endtime
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    
    //根据日期查询
    layui.use('laydate', function(){
    	var laydate = layui.laydate;
    	//执行一个laydate实例
    	laydate.render({
            elem: '#starttime'
        });
        laydate.render({
            elem: '#endtime'
        });
	});
    
    //重置
    this.toReset  = function() {
    	$('#content').val('');
    	$('#overTime').val('');
        $('#departId').val('');
        $('#departName').val('');
        $('#stationId').val('');
        $('#sort').val('');
        $('#starttime').val('');
        $('#endtime').val('');
    }
    
    this.selectDepart = function() {
        var queryParams = {};
        generateModel('select_depart','add',"400px","550px", '按部门查询',queryParams, null);
    }
	
	//增加
    this.add_entity = function() {
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitEnvForm",
                arg : ["save"]
            }
        };
        generateModel('env_info','add', "80%","80%", '设备信息登记',queryParams, btnOptions);
    }
    
	//修改
    this.edit_entity = function(id) {
        var queryParams = {
            type: 'edit'
        };
        var btnOptions = {
            save : {
                name : "submitEnvForm",
                arg : ["save"]
            }
        };
        console.log(id);
        
        generateModel('env_info','edit', "80%","80%", '设备信息修改',queryParams, btnOptions, '../env/getEnv?id='+id);
    }
    
    return this;
};
var env = window.app.env();
