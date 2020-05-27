var table;
window.app.log_error = function() {
	function init() {
		var html = $.templates.log_error.render();
		window.work_area.empty();
		window.work_area.html(html);
        log_errorTable();
	}
	this.backToList = function() {
		location.hash = "/log_error";
	}
	$.when($.lazyLoadTemplate("log_error")).done(function() {
		init();
	});
    
    function log_errorTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#log_errorTable',
                height: $('.data_list').height(),
                url: '../log/listJcLogRun',
                where: {type:0},
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
                        { unresize: true,field: 'systemUrl',width:'15%',title: '报错方法',align: 'center'},
                        { unresize: true,field: 'logInfo',width:'50%',title: '报错提示',align: 'center'},
                        { unresize: true,field: 'createTime', width: '15%', title: '操作时间',align: 'center' },
                        { unresize: true,field: 'creator', width: '15%', title: '操作用户' ,align: 'center'}
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
        table.reload('log_errorTable', {
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
var log_error = window.app.log_error();
