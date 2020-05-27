window.app.entrustRegister = function() {
	function init() {
		var html = $.templates.entrustRegister.render();
		window.work_area.empty();
		window.work_area.html(html);
		entrustRegisterTable();
	}
	this.backToList = function() {
		location.hash = "/entrustRegister";
	}
    function entrustRegisterTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#entrustTable',
                height: $('.data_list').height(),
                url: '../entrust/listEntrust',
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
                        { unresize: true,field: 'wtNum',width:'15%',title: '委托编号'},
                        { unresize: true,field: 'wtUnit', width: '15%', title: '委托单位' ,align: 'center'},
                        { unresize: true,field: 'contractCode', width: '15%', title: '合同编号' ,align: 'center'},
                        { unresize: true,field: 'gcCode', width: '15%', title: '工程编号' ,align: 'center'},
                        { unresize: true,field: 'gcName', width: '20%', title: '工程名称' ,align: 'center'},
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
//        row += '<a href="javascript:void(0);" class="oc" onclick=role.grantUser_entity(\''+id+'\')>授权</a>';
        return row;
    }
    this.add_entrustRegister = function() {
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitTodoWorkForm",
                arg : ["save"]
            }
        };
        generateModel('entrust_info','add', "70%", "85%",'委托信息',queryParams, btnOptions);
    }
	$.when($.lazyLoadTemplate("entrustRegister")).done(function() {
		init();
	});
	return this;
};
var entrustRegister = window.app.entrustRegister();