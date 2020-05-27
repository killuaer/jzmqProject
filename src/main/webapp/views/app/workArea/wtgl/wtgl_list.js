var table;
window.app.wtgl = function() {
	function init() {
		var html = $.templates.wtgl.render();
		window.work_area.empty();
		window.work_area.html(html);
		jcxmCoreTable();
	}
	this.backToList = function() {
		location.hash = "/wtgl";
	}
	$.when($.lazyLoadTemplate("wtgl")).done(function() {
		init();
	});
    
    function jcxmCoreTable() {
        layui.use(['layer', 'jquery', 'table'], function() {
            table = layui.table;
            table.render({
                elem: '#jcxmCoreTable',
                height: $('.data_list').height(),
                url: '../wtgl/listJcxmCore',
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
                        { unresize: true,field: 'wtNum',width:'8%',title: '委托编号',align: 'center'},
                        { unresize: true,field: 'gcName', width: '15%', title: '工程名称',align: 'center' },
                        { unresize: true,field: 'gcAddress', width: '15%', title: '工程地址' ,align: 'center'},
                        { unresize: true,field: 'wtUnit', width: '10%', title: '委托单位' ,align: 'center' },
                        { unresize: true,field: 'mqType', width: '8%', title: '幕墙类型' ,align: 'center' },
                        { unresize: true,field: 'mqmj', width: '7%', title: '幕墙面积(m<sup>2</sup>)' ,align: 'center' },
                        { unresize: true,field: 'syDate', width: '6%', title: '登记时间' ,align: 'center' },
                        { unresize: true,field: 'syMan', width: '5%', title: '登记人' ,align: 'center' }
                        ,{ unresize: true,field: 'cuozuo',  width: '15%', align: 'center', title: '操作',templet: function(d){
                            return operateColumn(d.id)
                        } }
                    ]
                ]

            });
        });
    }
    var operateColumn = function(id) {
        var row = '';
        row += '<a href="javascript:void(0);" class="oc" onclick=wtgl.check_entity(\''+id+'\')>查看</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=wtgl.edit_entity(\''+id+'\')>修改</a>';
        row += '<a href="javascript:void(0);" class="oc" onclick=wtgl.del_entity(\''+id+'\')>删除</a>';
        return row;
    }
    
    this.add_entity = function() {
        var queryParams = {
            type: 'add'
        };
        var btnOptions = {
            save : {
                name : "submitwtglForm",
                arg : ["save"]
            }
        };
        generateModel('wtgl_info','add', "70%", "85%","委托信息",queryParams, btnOptions);
    }

    this.queryList  = function() {
        var name = $('#name').val();
        var loginName = $('#loginName').val();
        var departId = $('#departId').val();
        table.reload('jcxmCoreTable', {
          where: { //设定异步数据接口的额外参数，任意设
            name: name,
            loginName: loginName,
            deptId: departId
          },
          page: {
            curr: 1 //重新从第 1 页开始
          }
        });
    }
    this.toReset  = function() {
        $('#name').val('');
        $('#loginName').val('');
        $('#departId').val('');
        $('#departName').val('');
    }

    this.selectDepart = function() {
        var queryParams = {};
        generateModel('select_depart','add', "70%", "85%", "部门信息", queryParams, null);
    }
    
    function selectDepartBackCall(data) {
        var arr1 = new Array();
        var arr2 = new Array();
        if (data.length >= 1) {
            for (i = 0; i < data.length; i++) {
                arr1.push(data[i].name);
                arr2.push(data[i].id);
            }
        }
        var ids1 = arr1.join(",");
        var ids2 = arr2.join(",");
        $('#roleNames').val(ids1);
        $('#roleIds').val(ids2);
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
    return this;
};
var wtgl = window.app.wtgl();
