var table;
window.app.log_operation = function() {
	function init() {
		var html = $.templates.log_operation.render();
		window.work_area.empty();
		window.work_area.html(html);
        log_operationTable();
	}
	this.backToList = function() {
		location.hash = "/log_operation";
	}
	$.when($.lazyLoadTemplate("log_operation")).done(function() {
		init();
	});
    
    function log_operationTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#log_operationTable',
                height: $('.data_list').height(),
                url: '../log/listJcLogOperation',
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
                        { unresize: true,field: 'content',width:'35%',title: '操作内容',align: 'center'},
                        { unresize: true,field: 'createTime', width: '20%', title: '操作时间',align: 'center' },
                        { unresize: true,field: 'creator', width: '20%', title: '操作用户',align: 'center' },
                        { unresize: true,field: 'opId', width: '20%', title: 'ip' ,align: 'center'}
                    ]
                ]

            });
        });
    }
    
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#starttime'
        });
        laydate.render({
            elem: '#endtime'
        });
    });
    
    this.queryList  = function() {
        var starttime = $('#starttime').val();
        var endtime = $('#endtime').val();
        table.reload('log_operationTable', {
          where: { //设定异步数据接口的额外参数，任意设
            starttime: starttime,
            endtime: endtime
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    this.toReset  = function() {
        $('#starttime').val('');
        $('#endtime').val('');
    }
    
    return this;
};
var log_operation = window.app.log_operation();
