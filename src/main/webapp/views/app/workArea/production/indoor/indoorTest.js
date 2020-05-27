window.app.indoorTest = function() {
	function init() {
		var html = $.templates.indoorTest.render();
		window.work_area.empty();
		window.work_area.html(html);
		inspectionTable();
	}
	this.backToList = function() {
		location.hash = "/indoorTest";
	}
    $.when($.lazyLoadTemplate("indoorTest")).done(function() {
		init();
	});
    function inspectionTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#inspectionTable',
                height: $('.data_list').height(),
                url: '',
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
//        row += '<a href="javascript:void(0);" class="oc" onclick=role.grantUser_entity(\''+id+'\')>授权</a>';
        return row;
    }
    this.add_indoorTest = function() {
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitIndoorTestInfoForm",
                arg : ["save"]
            }
        };
        generateModel('indoorTest_info','add', "70%", "85%",'样品信息',queryParams, btnOptions);
    }
	
	return this;
};
var indoorTest = window.app.indoorTest();